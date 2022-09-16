package com.booktopia.services

import com.booktopia.enums.StatusEnum
import com.booktopia.exception.NotFoundException
import com.booktopia.repositories.AddressRepository
import com.booktopia.models.AddressModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.*
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class AddressServiceTest{
    @MockK
    private lateinit var addressRepository: AddressRepository

    @InjectMockKs
    private lateinit var addressService: AddressService

    @Test
    fun `should return all addresses`(){
        val pageable = PageRequest.of(0,5)
        val fakeAddresses: Page<AddressModel> = PageImpl(listOf(buildAddress(), buildAddress()))

        every{addressRepository.findAll(pageable)} returns fakeAddresses

        val addresses = addressService.findAll(pageable)

        assertEquals(fakeAddresses, addresses)
        verify (exactly = 1){ addressRepository.findAll(pageable) }
    }

    @Test
    fun `should create address`(){
        val fakeAddress = buildAddress()

        every { addressRepository.save(fakeAddress) } returns fakeAddress

        addressService.create(fakeAddress)

        verify (exactly = 1){ addressRepository.save(fakeAddress) }
    }

    @Test
    fun `should return address by id`(){
        val id: Int= Random.nextInt()
        val fakeAddress = buildAddress(id = id)

        every { addressRepository.findById(id) } returns Optional.of(fakeAddress)

        val address = addressService.findById(id)

        assertEquals(fakeAddress, address)
        verify (exactly = 1){ addressRepository.findById(id) }
    }

    @Test
    fun `should throw not found when find by id`(){
        val id: Int= Random.nextInt()

        every { addressRepository.findById(id) } returns Optional.empty()

        val error = assertThrows<NotFoundException> {
            addressService.findById(id)
        }

        assertEquals("Address id [$id] not exists", error.message)
        assertEquals("B-401", error.errorCode)
        verify (exactly = 1){ addressRepository.findById(id) }
    }

    @Test
    fun`should update address`(){
        val id: Int= Random.nextInt()
        val fakeAddress = buildAddress(id)

        every { addressRepository.existsById(id) } returns true
        every { addressRepository.save(fakeAddress) } returns fakeAddress

        addressService.update(fakeAddress)

        verify (exactly = 1){ addressRepository.existsById(id) }
        verify (exactly = 1){ addressRepository.save(fakeAddress)  }
    }

    @Test
    fun`should throw not found when update address`(){
        val id: Int= Random.nextInt()
        val fakeAddress = buildAddress(id)

        every { addressRepository.existsById(id) } returns false
        every { addressRepository.save(fakeAddress) } returns fakeAddress

        val error = assertThrows<NotFoundException> {
            addressService.update(fakeAddress)
        }

        assertEquals("Address id [$id] not exists", error.message)
        assertEquals("B-401", error.errorCode)

        verify (exactly = 1){ addressRepository.existsById(id) }
        verify (exactly = 0){ addressRepository.save(any())  }
    }

    fun buildAddress(
        id: Int? = Random.nextInt(),
        street: String = RandomString(50).toString(),
        number: Int = Random.nextInt(),
        district: String = RandomString(10).toString(),
        city: String = RandomString(10).toString(),
        cep: String = Random(8).toString()
    ) = AddressModel (
        id = id,
        street = street,
        number = number,
        district = district,
        city = city,
        cep = cep,
        status = StatusEnum.ACTIVE
    )

}