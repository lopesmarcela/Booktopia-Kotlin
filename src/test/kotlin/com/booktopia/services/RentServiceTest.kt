package com.booktopia.services

import com.booktopia.enums.StatusEnum
import com.booktopia.models.RentModel
import com.booktopia.repositories.RentRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
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
    fun buildRent(
        id: Int? = null,
        fine: BigDecimal = Random(6).nextDouble().toBigDecimal(),
        totalValue: BigDecimal = Random(6).nextDouble().toBigDecimal(),
    )=RentModel(
        id = id,
        fine = fine,
        totalValue = totalValue,
        rentalDate = LocalDate.now(),
        returnDate = LocalDate.now(),
        status = StatusEnum.ACTIVE
    )
}