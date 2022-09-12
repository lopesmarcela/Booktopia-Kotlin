package com.booktopia.controllers

import com.booktopia.controllers.request.PostRentRequest
import com.booktopia.controllers.request.PutRentRequest
import com.booktopia.controllers.response.PageResponse
import com.booktopia.controllers.response.RentResponse
import com.booktopia.extensions.toPageResponse
import com.booktopia.extensions.toRentModel
import com.booktopia.extensions.toResponse
import com.booktopia.models.RentModel
import com.booktopia.services.BookService
import com.booktopia.services.ClientService
import com.booktopia.services.RentService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api(value = "", tags = ["Rent"])
@RequestMapping("rents")
class RentController(
    private val rentService: RentService,
    private val clientService: ClientService,
    private val bookService: BookService
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
    fun findAll(@PageableDefault(page = 0, size = 5) pageable: Pageable): PageResponse<RentResponse> =
        rentService.findAll(pageable).map { it.toResponse() }.toPageResponse()

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