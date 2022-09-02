package com.booktopia.controllers

import com.booktopia.controllers.request.PostClientRequest
import com.booktopia.controllers.request.PutClientRequest
import com.booktopia.models.ClientModel
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("client")
class ClientController {

    val clients = mutableListOf<ClientModel>()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody client: PostClientRequest){
        var id = if (clients.isEmpty()){
            1
        }else{
            clients.last().idClient+1
        }
        clients.add(ClientModel(id, client.cpf, client.name, client.email ,client.address))
    }

    @GetMapping
    fun findAll(@RequestParam name: String?): List<ClientModel>{
        name?.let {
            return clients.filter { it.name.contains(name, true) }
        }
        return clients
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ClientModel{
        return clients.filter { it.idClient == id }.first()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody client: PutClientRequest){
       clients.filter { it.idClient == id }.first().let {
           it.name = client.name
           it.email = client.email
           it.address = client.address
       }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int){
        clients.removeIf { it.idClient == id }
    }
}