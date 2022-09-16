package com.booktopia.services

import com.booktopia.enums.Errors
import com.booktopia.enums.StatusEnum
import com.booktopia.exception.BadRequestException
import com.booktopia.exception.NotFoundException
import com.booktopia.models.AddressModel
import com.booktopia.models.ClientModel
import com.booktopia.repositories.ClientRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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

        val clients = clientService.findAll(pageable = pageable, name = null)

        assertEquals(fakeClients, clients)
        verify (exactly = 1){ clientRepository.findAll(pageable) }
        verify (exactly = 0){ clientRepository.findByNameContaining(any(), pageable) }
    }

    @Test
    fun `should return clients when name is informed`(){
        val name = UUID.randomUUID().toString()
        val pageable: Pageable = PageRequest.of(0,5)
        val fakeClients: Page<ClientModel> = PageImpl(listOf(buildClient(cpf = "547.616.951-29"), buildClient(cpf = "176.764.563-55")))

        every { clientRepository.findByNameContaining(name, pageable) } returns fakeClients

        val clients = clientService.findAll(pageable = pageable, name = name)

        assertEquals(fakeClients, clients)
        verify (exactly = 0){ clientRepository.findAll(pageable) }
        verify (exactly = 1){ clientRepository.findByNameContaining(name, pageable) }
    }

    @Test
    fun `should create client`(){
        val client = buildClient(cpf = "064.687.863-80")

        every { addressService.findAddressInactive(client.address!!.id!!) } just runs
        every { clientRepository.save(client) } returns client


        clientService.create(client)

        verify (exactly = 1){ clientRepository.save(client) }
        verify (exactly = 1){ addressService.findAddressInactive(client.address!!.id!!) }
    }

    @Test
    fun `should return client by id`(){
        val id: Int = kotlin.random.Random.nextInt()
        val fakeClient = buildClient(id, "064.687.863-80")

        every { clientRepository.findById(id) } returns Optional.of(fakeClient)

        val client = clientService.findById(id)

        assertEquals(fakeClient,client)
        verify (exactly = 1){ clientRepository.findById(id) }
    }

    @Test
    fun `should throw error when client not found`(){
        val id: Int = Random().nextInt()
        every { clientRepository.findById(id) } returns Optional.empty()

        val error = assertThrows<NotFoundException> {
            clientService.findById(id)
        }

        assertEquals("Client id [$id] not exists", error.message)
        assertEquals("B-101", error.errorCode)
        verify (exactly = 1){ clientRepository.findById(id) }
    }

    fun buildClient(
        id: Int = Random().nextInt(),
        cpf: String,
        name: String = RandomString(10).toString(),
        email:String = "${UUID.randomUUID()}@gmail.com",
    ) = ClientModel(
        id = id,
        cpf = cpf,
        name = name,
        email= email,
        status = StatusEnum.ACTIVE,
        address = AddressModel(
            id = 1,
            street = "",
            number = 1,
            district = "",
            city = "",
            cep = ""
        )
    )
}