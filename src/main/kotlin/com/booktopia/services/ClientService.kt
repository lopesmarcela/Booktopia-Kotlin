package com.booktopia.services

import com.booktopia.enums.Errors
import com.booktopia.enums.StatusEnum
import com.booktopia.exception.BadRequestException
import com.booktopia.exception.NotFoundException
import com.booktopia.models.ClientModel
import com.booktopia.repositories.ClientRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ClientService(
    private val clientRepository: ClientRepository,
    private val addressService: AddressService
) {

    fun create(client: ClientModel){
        addressService.findAddressInactive(client.address!!.id!!)
        clientRepository.save(client)
    }

    fun findAll(name: String?, pageable: Pageable): Page<ClientModel> {
        name?.let {
            return clientRepository.findByNameContaining(it, pageable)
        }
        return clientRepository.findAll(pageable)
    }

    fun findById(id: Int): ClientModel{
        return clientRepository.findById(id).orElseThrow{NotFoundException(Errors.B101.message.format(id), Errors.B101.code)}
    }

    fun findByActive():List<ClientModel>{
        return clientRepository.findByStatus(StatusEnum.ACTIVE)
    }

    fun update(client: ClientModel){
        clientRepository.save(client)
    }

    fun delete(id: Int){
        val client = findById(id)
        findClientInactive(id)
        client.status = StatusEnum.INACTIVE

        //disabling customer address
        val address = addressService.findById(client.address!!.id!!)
        addressService.delete(address.id!!)

        update(client)
    }

    fun emailAvailable(email: String): Boolean {
        return !clientRepository.existsByEmail(email)
    }

    fun cpfAvailable(cpf: String): Boolean {
        return !clientRepository.existsByCpf(cpf)
    }

    fun findClientInactive(int: Int){
        val client = findById(int)
        if(client.status == StatusEnum.INACTIVE){
            throw BadRequestException(Errors.B303.message.format(client.id), Errors.B303.code)
        }
    }
}