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
    @SpyK
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
    fun `should throw not found when find by id`(){
        val id: Int = Random().nextInt()
        every { clientRepository.findById(id) } returns Optional.empty()

        val error = assertThrows<NotFoundException> {
            clientService.findById(id)
        }

        assertEquals("Client id [$id] not exists", error.message)
        assertEquals("B-101", error.errorCode)
        verify (exactly = 1){ clientRepository.findById(id) }
    }

    @Test
    fun`should update client`(){
        val id: Int= kotlin.random.Random.nextInt()
        val fakeClient = buildClient(id, "064,687,863-80")

        every { clientRepository.existsById(id) } returns true
        every { clientRepository.save(fakeClient) } returns fakeClient

        clientService.update(fakeClient)

        verify (exactly = 1){ clientRepository.existsById(id) }
        verify (exactly = 1){ clientRepository.save(fakeClient)  }
    }

    @Test
    fun`should throw not found when update client`(){
        val id: Int= kotlin.random.Random.nextInt()
        val fakeClient = buildClient(id, "064,687,863-80")

        every { clientRepository.existsById(id) } returns false
        every { clientRepository.save(fakeClient) } returns fakeClient

        val error = assertThrows<NotFoundException> {
            clientService.update(fakeClient)
        }

        assertEquals("Client id [$id] not exists", error.message)
        assertEquals("B-101", error.errorCode)

        verify (exactly = 1){ clientRepository.existsById(id) }
        verify (exactly = 0){ clientRepository.save(any())  }
    }

    @Test
    fun `should delete admin`(){
        val id: Int = Random().nextInt()
        val fakeClient = buildClient(id,"064.687.863-80")

        every { clientService.findById(id) } returns fakeClient
        every { addressService.findById(fakeClient.address!!.id!!) } returns fakeClient.address!!
        every {
            clientService.findClientInactive(id)
            clientService.update(fakeClient)
            addressService.delete(fakeClient.address!!.id!!)
        } just runs

        clientService.delete(id)
        verify (exactly = 1){ clientService.findById(id) }
        verify (exactly = 1){ clientService.findClientInactive(id) }
        verify (exactly = 1){ clientService.update(fakeClient) }
    }

    @Test
    fun `should throw not found when delete admin`(){
        val id: Int = Random().nextInt()

        every { clientService.findById(id) } throws NotFoundException(Errors.B101.message.format(id), Errors.B101.code)


        val error = assertThrows<NotFoundException>{ clientService.delete(id) }

        assertEquals("Client id [$id] not exists", error.message)
        assertEquals("B-101", error.errorCode)

        verify (exactly = 1){ clientService.findById(id) }
        verify (exactly = 0){ clientService.update(any()) }
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