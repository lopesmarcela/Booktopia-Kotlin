package com.booktopia.controllers

import com.booktopia.controllers.request.PostAddressRequest
import com.booktopia.controllers.request.PutAddressRequest
import com.booktopia.extensions.toAddressModel
import com.booktopia.models.AddressModel
import com.booktopia.services.AddressService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("address")
class AddressController(
    val addressService: AddressService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody address: PostAddressRequest) =
        addressService.create(address.toAddressModel())

    @GetMapping
    fun findAll():List<AddressModel> =
        addressService.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): AddressModel =
        addressService.findById(id)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody client: PutAddressRequest) {
        val addressSaved = addressService.findById(id)
        addressService.update(client.toAddressModel(addressSaved))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        addressService.delete(id)
    }
}