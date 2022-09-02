package com.booktopia.services

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
        if (!clientRepository.existsById(client.id!!)){
            throw Exception()
        }

        clientRepository.save(client)
    }

    fun delete(id: Int){
        if (!clientRepository.existsById(id)){
            throw Exception()
        }

        clientRepository.deleteById(id)
    }
}