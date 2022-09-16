package com.booktopia.services

import com.booktopia.enums.CategoryEnum
import com.booktopia.enums.Errors
import com.booktopia.enums.StatusEnum
import com.booktopia.exception.BadRequestException
import com.booktopia.exception.NotFoundException
import com.booktopia.models.BookModel
import com.booktopia.models.ClientModel
import com.booktopia.models.RentModel
import com.booktopia.repositories.RentRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.awt.print.Book
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@ExtendWith(MockKExtension::class)
class RentServiceTest{
    @MockK
    private lateinit var rentRepository: RentRepository
    @MockK
    private lateinit var bookService: BookService
    @MockK
    private lateinit var clientService: ClientService

    @InjectMockKs
    private lateinit var rentService: RentService

    @Test
    fun `should return all rents` (){
        val pageable = PageRequest.of(0,5)
        val fakeRents = PageImpl(listOf(buildRent(), buildRent()))

        every { rentRepository.findAll(pageable) } returns fakeRents

        val rents = rentService.findAll(pageable)

        assertEquals(fakeRents, rents)
        verify (exactly = 1){ rentRepository.findAll(pageable) }
    }

    @Test
    fun `should create client`(){
        val rent = buildRent()

        every { bookService.findById(rent.book!!.id!!) } returns rent.book!!
        every { clientService.findById(rent.client!!.id!!) } returns rent.client!!
        every {
            bookService.findBookInactive(rent.book!!.id!!)
            clientService.findClientInactive(rent.client!!.id!!)
            bookService.decreasesStock(rent.book!!)
        } just runs
        every { rentRepository.save(rent) } returns rent

        rentService.create(rent)

        verify (exactly = 1){ bookService.findById(rent.book!!.id!!) }
        verify (exactly = 1){ clientService.findById(rent.client!!.id!!) }
        verify (exactly = 1){ bookService.findBookInactive(rent.book!!.id!!) }
        verify (exactly = 1){ clientService.findClientInactive(rent.client!!.id!!) }
        verify (exactly = 1){  bookService.decreasesStock(rent.book!!) }
        verify (exactly = 1){ rentRepository.save(rent) }
    }

    @Test
    fun `should return rent by id`(){
        val id: Int = Random().nextInt()
        val fakeRent = buildRent(id)

        every { rentRepository.findById(id) } returns Optional.of(fakeRent)

        val rent = rentService.findById(id)

        assertEquals(fakeRent,rent)
        verify (exactly = 1){ rentRepository.findById(id) }
    }

    @Test
    fun `should throw not found when find by id`(){
        val id: Int = Random().nextInt()
        every { rentRepository.findById(id) } returns Optional.empty()

        val error = assertThrows<NotFoundException> {
            rentService.findById(id)
        }

        assertEquals("Rent id [$id] not exists", error.message)
        assertEquals("B-301", error.errorCode)
        verify (exactly = 1){ rentRepository.findById(id) }
    }

    @Test
    fun`should return rent`(){
        val id: Int= Random().nextInt()
        val fakeRent = buildRent(id)

        every { rentRepository.existsById(id) } returns true
        every { bookService.increasesStock(fakeRent.book!!)} just runs
        every { bookService.findById(fakeRent.book!!.id!!) } returns fakeRent.book!!
        every { rentRepository.save(fakeRent) } returns fakeRent

        rentService.returnBook(fakeRent)

        verify (exactly = 1){ rentRepository.existsById(id) }
        verify (exactly = 1){ bookService.increasesStock(fakeRent.book!!) }
        verify (exactly = 1){ bookService.findById(fakeRent.book!!.id!!) }
        verify (exactly = 1){ rentRepository.save(fakeRent)  }
    }

    @Test
    fun`should throw not found when return rent`(){
        val id: Int= Random().nextInt()
        val fakeRent = buildRent(id)

        every { rentRepository.existsById(id) } returns false
        every { bookService.increasesStock(fakeRent.book!!)} just runs
        every { bookService.findById(fakeRent.book!!.id!!) } returns fakeRent.book!!
        every { rentRepository.save(fakeRent) } returns fakeRent

        val error = assertThrows<NotFoundException> {
            rentService.returnBook(fakeRent)
        }

        assertEquals("Rent id [$id] not exists", error.message)
        assertEquals("B-301", error.errorCode)

        verify (exactly = 1){ rentRepository.existsById(id) }
        verify (exactly = 0){ bookService.increasesStock(fakeRent.book!!) }
        verify (exactly = 0){ bookService.findById(fakeRent.book!!.id!!) }
        verify (exactly = 0){ rentRepository.save(fakeRent)  }
    }

    fun buildRent(
        id: Int? = Random().nextInt(),
        fine: BigDecimal = Random(6).nextDouble().toBigDecimal(),
        totalValue: BigDecimal = Random(6).nextDouble().toBigDecimal(),
    )=RentModel(
        id = id,
        fine = fine,
        totalValue = totalValue,
        rentalDate = LocalDate.now(),
        returnDate = LocalDate.now().plusDays(10),
        status = StatusEnum.ACTIVE,
        client = ClientModel(
            id = Random().nextInt(),
            cpf = "064.687.863-80",
            name = "Marcela",
            email = "marcela@gmail.com"
        ),
        book = BookModel(
            id = Random().nextInt(),
            title = "",
            description = "",
            releaseDate = LocalDate.now(),
            category = CategoryEnum.ROMANCE,
            author = "",
            price = 0.0.toBigDecimal(),
            inventory = 2
        )
    )
}