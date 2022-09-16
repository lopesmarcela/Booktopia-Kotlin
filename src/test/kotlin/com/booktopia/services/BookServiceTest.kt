package com.booktopia.services

import com.booktopia.enums.CategoryEnum
import com.booktopia.enums.Errors
import com.booktopia.enums.StatusEnum
import com.booktopia.exception.NotFoundException
import com.booktopia.models.BookModel
import com.booktopia.repositories.BookRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class BookServiceTest{
    @MockK
    private lateinit var bookRepository: BookRepository

    @InjectMockKs
    @SpyK
    private lateinit var bookService: BookService

    @Test
    fun `should return all books`(){
        val pageable: Pageable = PageRequest.of(0,5)
        val fakeAddresses = PageImpl(listOf(buildBook(), buildBook()))

        every { bookRepository.findAll(pageable) } returns fakeAddresses

        val book = bookService.findAll(null,pageable)

        assertEquals(fakeAddresses, book)
        verify (exactly = 1){ bookRepository.findAll(pageable) }
        verify (exactly = 0){ bookRepository.findByTitleContaining(any(), pageable) }
    }

    @Test
    fun `should return clients when name is informed`(){
        val title = UUID.randomUUID().toString()
        val pageable: Pageable = PageRequest.of(0,5)
        val fakeAddresses = PageImpl(listOf(buildBook(), buildBook()))

        every { bookRepository.findByTitleContaining(title,pageable) } returns fakeAddresses

        val addresses = bookService.findAll(title,pageable)

        assertEquals(fakeAddresses, addresses)
        verify (exactly = 0){ bookRepository.findAll(pageable) }
        verify (exactly = 1){ bookRepository.findByTitleContaining(title, pageable) }
    }

    @Test
    fun `should create book`(){
        val book = buildBook()

        every { bookRepository.save(book) } returns book

        bookService.create(book)

        verify (exactly = 1){ bookRepository.save(book) }
    }

    @Test
    fun `should return book by id`(){
        val id: Int = Random.nextInt()
        val fakeBook = buildBook(id)

        every { bookRepository.findById(id) } returns Optional.of(fakeBook)

        val book = bookService.findById(id)

        assertEquals(fakeBook,book)
        verify (exactly = 1){ bookRepository.findById(id) }
    }

    @Test
    fun `should throw not found when find by id`(){
        val id: Int = Random.nextInt()

        every { bookRepository.findById(id) } returns Optional.empty()

        val error = assertThrows<NotFoundException> {
            bookService.findById(id)
        }

        assertEquals("Book id [$id] not exists", error.message)
        assertEquals("B-201", error.errorCode)
        verify (exactly = 1){ bookRepository.findById(id) }
    }

    @Test
    fun`should update book`(){
        val id: Int= Random.nextInt()
        val fakeBook = buildBook(id)

        every { bookRepository.existsById(id) } returns true
        every { bookRepository.save(fakeBook) } returns fakeBook

        bookService.update(fakeBook)

        verify (exactly = 1){ bookRepository.existsById(id) }
        verify (exactly = 1){ bookRepository.save(fakeBook)  }
    }

    @Test
    fun`should throw not found when update book`(){
        val id: Int= Random.nextInt()
        val fakeBook = buildBook(id)

        every { bookRepository.existsById(id) } returns false
        every { bookRepository.save(fakeBook) } returns fakeBook

        val error = assertThrows<NotFoundException> {
            bookService.update(fakeBook)
        }

        assertEquals("Book id [$id] not exists", error.message)
        assertEquals("B-201", error.errorCode)

        verify (exactly = 1){ bookRepository.existsById(id) }
        verify (exactly = 0){ bookRepository.save(any())  }
    }

    @Test
    fun `should delete book`(){
        val id: Int= Random.nextInt()
        val fakeAdmin = buildBook(id)

        every { bookService.findById(id) } returns fakeAdmin
        every {
            bookService.findBookInactive(id)
            bookService.update(fakeAdmin)
        } just runs

        bookService.delete(id)
        verify (exactly = 1){ bookService.findById(id) }
        verify (exactly = 1){ bookService.findBookInactive(id) }
        verify (exactly = 1){ bookService.update(fakeAdmin) }
    }

    @Test
    fun `should throw not found when delete book`(){
        val id: Int = java.util.Random().nextInt()

        every { bookService.findById(id) } throws NotFoundException(Errors.B201.message.format(id), Errors.B201.code)


        val error = assertThrows<NotFoundException>{ bookService.delete(id) }

        assertEquals("Book id [$id] not exists", error.message)
        assertEquals("B-201", error.errorCode)

        verify (exactly = 1){ bookService.findById(id) }
        verify (exactly = 0){ bookService.findBookInactive(id) }
        verify (exactly = 0){ bookService.update(any()) }
    }

    fun buildBook(
        id:Int?=null,
        title:String = RandomString(30).toString(),
        description:String = RandomString(100).toString(),
        author:String = RandomString(10).toString(),
        price: BigDecimal = Random(6).nextDouble().toBigDecimal(),
        inventory: Int = Random(1).nextInt()
    )=BookModel(
        id = id,
        title = title,
        description = description,
        releaseDate = LocalDate.now(),
        category = CategoryEnum.ROMANCE,
        author = author,
        price = price,
        inventory = inventory,
        status = StatusEnum.ACTIVE
    )
}