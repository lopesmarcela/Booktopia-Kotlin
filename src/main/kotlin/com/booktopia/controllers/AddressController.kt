package com.booktopia.controllers

import com.booktopia.controllers.request.PostAddressRequest
import com.booktopia.controllers.request.PutAddressRequest
import com.booktopia.extensions.toAddressModel
import com.booktopia.models.AddressModel
import com.booktopia.services.AddressService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api(value = "", tags = ["Address"])
@RequestMapping("address")
class AddressController(
    private val addressService: AddressService
) {

    @PostMapping(consumes = arrayOf("application/json"), produces= arrayOf("application/json"))
    @ApiOperation(value = "Creates an address")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid address: PostAddressRequest) =
        addressService.create(address.toAddressModel())

    @GetMapping(produces= arrayOf("application/json"))
    @ApiOperation(value = "Returns a list of addresses")
    fun findAll(@PageableDefault(page = 0, size = 5) pageable: Pageable):Page<AddressModel> =
        addressService.findAll(pageable)

    @GetMapping("/{id}", produces = arrayOf("application/json"))
    @ApiOperation(value = "Returns an address by id")
    fun findById(@PathVariable id: Int): AddressModel =
        addressService.findById(id)

    @PutMapping("/{id}", consumes = arrayOf("application/json"), produces= arrayOf("application/json"))
    @ApiOperation(value = "Changes an address")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody address: PutAddressRequest) {
        val addressSaved = addressService.findById(id)
        addressService.update(address.toAddressModel(addressSaved))
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Disables an address")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        addressService.delete(id)
    }
}