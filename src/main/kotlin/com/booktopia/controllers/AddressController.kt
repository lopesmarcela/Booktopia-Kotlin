package com.booktopia.controllers

import com.booktopia.controllers.request.PostAddressRequest
import com.booktopia.controllers.request.PutAddressRequest
import com.booktopia.extensions.toAddressModel
import com.booktopia.models.AddressModel
import com.booktopia.services.AddressService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("address")
class AddressController(
    val addressService: AddressService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid address: PostAddressRequest) =
        addressService.create(address.toAddressModel())

    @GetMapping
    fun findAll(@PageableDefault(page = 0, size = 5) pageable: Pageable):Page<AddressModel> =
        addressService.findAll(pageable)

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): AddressModel =
        addressService.findById(id)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody address: PutAddressRequest) {
        val addressSaved = addressService.findById(id)
        addressService.update(address.toAddressModel(addressSaved))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        addressService.delete(id)
    }
}