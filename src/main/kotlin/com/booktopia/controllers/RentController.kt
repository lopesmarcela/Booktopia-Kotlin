package com.booktopia.controllers

import com.booktopia.controllers.request.PostRentRequest
import com.booktopia.controllers.request.PutRentRequest
import com.booktopia.controllers.response.RentResponse
import com.booktopia.extensions.toRentModel
import com.booktopia.extensions.toResponse
import com.booktopia.models.RentModel
import com.booktopia.services.BookService
import com.booktopia.services.ClientService
import com.booktopia.services.RentService
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("rent")
class RentController(
    val rentService: RentService,
    val clientService: ClientService,
    val bookService: BookService
) {

    @PostMapping
    @ApiOperation(value = "Registers a rent")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid rent: PostRentRequest) {
        var client = clientService.findById(rent.clientId)
        var book = bookService.findById(rent.bookId)
        rentService.create(rent.toRentModel(client, book))
    }

    @GetMapping
    @ApiOperation(value = "Returns a list of rents")
    fun findAll(@PageableDefault(page = 0, size = 5) pageable: Pageable): Page<RentResponse> =
        rentService.findAll(pageable).map { it.toResponse() }

    @GetMapping("/{id}")
    @ApiOperation(value = "Details a rent")
    fun details (@PathVariable id: Int): RentModel =
        rentService.findById(id)

    @PutMapping("/{id}")
    @ApiOperation(value = "Restore a rent")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun returnBook(@PathVariable id: Int, @RequestBody @Valid rent: PutRentRequest) {
        val rentSaved = rentService.findById(id)
        rentService.returnBook(rent.toRentModel(rentSaved))
    }
}