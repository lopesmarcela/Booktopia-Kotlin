package com.booktopia.services

import com.booktopia.enums.StatusEnum
import com.booktopia.models.ClientModel
import com.booktopia.repositories.ClientRepository
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class ClientService(
    val clientRepository: ClientRepository
) {

    fun create(client: ClientModel){
        clientRepository.save(client)
    }

    fun findAll(name: String?): List<ClientModel> {
        name?.let {
            return clientRepository.findByNameContaining(it)
        }
        return clientRepository.findAll().toList()
    }

    fun findById(id: Int): ClientModel{
        return clientRepository.findById(id).orElseThrow()
    }

    fun update(client: ClientModel){
        clientRepository.save(client)
    }

    fun delete(id: Int){
        var client = findById(id)
        client.status = StatusEnum.INACTIVE
        update(client)
    }
}