package com.booktopia.services

import com.booktopia.controllers.request.PostClientRequest
import com.booktopia.controllers.request.PutClientRequest
import com.booktopia.models.ClientModel
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Service
class ClientService {

    val clients = mutableListOf<ClientModel>()

    fun create(client: ClientModel){
        var id = if (clients.isEmpty()){
            1
        }else{
            clients.last().idClient!!.plus(1)
        }
        client.idClient = id
        clients.add(client)
    }

    fun findAll(name: String?): List<ClientModel>{
        name?.let {
            return clients.filter { it.name.contains(name, true) }
        }
        return clients
    }

    fun findById(id: Int): ClientModel{
        return clients.filter { it.idClient == id }.first()
    }

    fun update(client: ClientModel){
        clients.filter { it.idClient == client.idClient }.first().let {
            it.cpf = it.cpf
            it.name = client.name
            it.email = client.email
            it.address = client.address
        }
    }

    fun delete(id: Int){
        clients.removeIf { it.idClient == id }
    }
}