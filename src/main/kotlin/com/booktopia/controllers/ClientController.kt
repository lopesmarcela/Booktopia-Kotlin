package com.booktopia.controllers

import com.booktopia.controllers.request.PostClientRequest
import com.booktopia.controllers.request.PutClientRequest
import com.booktopia.extensions.toClientModel
import com.booktopia.models.ClientModel
import com.booktopia.services.ClientService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("client")
class ClientController(
    val clientService: ClientService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody client: PostClientRequest) =
        clientService.create(client.toClientModel())

    @GetMapping
    fun findAll(@RequestParam name: String?): List<ClientModel> =
        clientService.findAll(name)

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ClientModel =
        clientService.findById(id)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody client: PutClientRequest) =
       clientService.update(client.toClientModel(id))

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) =
        clientService.delete(id)
}