package com.booktopia.services

import com.booktopia.enums.StatusEnum
import com.booktopia.models.ClientModel
import com.booktopia.repositories.ClientRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.util.*

@ExtendWith(MockKExtension::class)
class ClientServiceTest{

    @MockK
    private lateinit var clientRepository: ClientRepository

    @MockK
    private lateinit var addressService: AddressService

    @InjectMockKs
    private lateinit var clientService: ClientService

    @Test
    fun `should return all clients`(){
        val pageable: Pageable = PageRequest.of(0,5)

        val fakeClients: Page<ClientModel> = PageImpl(listOf(buildClient(cpf = "547.616.951-29"), buildClient(cpf = "176.764.563-55")))

        every { clientRepository.findAll(pageable) } returns fakeClients

        val admins = clientService.findAll(pageable = pageable, name = null)

        assertEquals(fakeClients, admins)
        verify (exactly = 1){ clientRepository.findAll(pageable) }
        verify (exactly = 0){ clientRepository.findByNameContaining(any(), pageable) }
    }

    fun buildClient(
        id: Int? = null,
        cpf: String,
        name: String = RandomString(10).toString(),
        email:String = "${UUID.randomUUID()}@gmail.com",
    ) = ClientModel(
        id = id,
        cpf = cpf,
        name = name,
        email= email,
        status = StatusEnum.ACTIVE
    )
}