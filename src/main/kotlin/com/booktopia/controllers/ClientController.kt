package com.booktopia.controllers

import com.booktopia.controllers.request.PostClientRequest
import com.booktopia.controllers.request.PutClientRequest
import com.booktopia.controllers.response.ClientResponse
import com.booktopia.extensions.toClientModel
import com.booktopia.extensions.toResponse
import com.booktopia.models.ClientModel
import com.booktopia.services.AddressService
import com.booktopia.services.ClientService
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("client")
class ClientController(
    val clientService: ClientService,
    val addressService: AddressService
) {

    @PostMapping
    @ApiOperation(value = "Registers a client")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid client: PostClientRequest) {
        var address = addressService.findById(client.addressId)
        clientService.create(client.toClientModel(address))
    }

    @GetMapping
    @ApiOperation(value = "Returns a list of clients")
    fun findAll(@RequestParam name: String?, @PageableDefault(page = 0, size = 5) pageable: Pageable): Page<ClientResponse> =
        clientService.findAll(name, pageable).map { it.toResponse() }

    @GetMapping("/{id}")
    @ApiOperation(value = "Details a client")
    fun details(@PathVariable id: Int): ClientModel =
        clientService.findById(id)

    @GetMapping("/active")
    @ApiOperation(value = "Returns a list of clients actives")
    fun findByActive(): List<ClientResponse> =
        clientService.findByActive().map { it.toResponse() }

    @PutMapping("/{id}")
    @ApiOperation(value = "Changes a client")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody @Valid client: PutClientRequest) {
        val clientSaved = clientService.findById(id)
        clientService.update(client.toClientModel(clientSaved))
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Disables a client")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        clientService.delete(id)
    }
}