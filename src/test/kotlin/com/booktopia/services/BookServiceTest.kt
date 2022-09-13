package com.booktopia.services

import com.booktopia.enums.CategoryEnum
import com.booktopia.enums.StatusEnum
import com.booktopia.models.BookModel
import com.booktopia.repositories.BookRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
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
    private lateinit var bookService: BookService

    @Test
    fun `should return all books`(){
        val pageable: Pageable = PageRequest.of(0,5)
        val fakeAddresses = PageImpl(listOf(buildBook(), buildBook()))

        every { bookRepository.findAll(pageable) } returns fakeAddresses

        val addresses = bookService.findAll(null,pageable)

        assertEquals(fakeAddresses, addresses)
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