package com.booktopia.services

import com.booktopia.enums.StatusEnum
import com.booktopia.models.ClientModel
import com.booktopia.repositories.ClientRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ClientService(
    val clientRepository: ClientRepository,
    val addressService: AddressService
) {

    fun create(client: ClientModel){
        clientRepository.save(client)
    }

    fun findAll(name: String?, pageable: Pageable): Page<ClientModel> {
        name?.let {
            return clientRepository.findByNameContaining(it, pageable)
        }
        return clientRepository.findAll(pageable)
    }

    fun findById(id: Int): ClientModel{
        return clientRepository.findById(id).orElseThrow()
    }

    fun findByActive():List<ClientModel>{
        return clientRepository.findByStatus(StatusEnum.ACTIVE)
    }

    fun update(client: ClientModel){
        clientRepository.save(client)
    }

    fun delete(id: Int){
        var client = findById(id)
        client.status = StatusEnum.INACTIVE

        //disabling customer address
        var address = addressService.findById(client.address!!.id!!)
        addressService.delete(address.id!!)

        update(client)
    }
}