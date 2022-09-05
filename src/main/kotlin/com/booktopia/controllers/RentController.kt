package com.booktopia.controllers

import com.booktopia.controllers.request.PostRentRequest
import com.booktopia.controllers.request.PutRentRequest
import com.booktopia.enums.StatusEnum
import com.booktopia.extensions.toRentModel
import com.booktopia.models.RentModel
import com.booktopia.services.BookService
import com.booktopia.services.ClientService
import com.booktopia.services.RentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("rent")
class RentController(
    val rentService: RentService,
    val clientService: ClientService,
    val bookService: BookService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody rent: PostRentRequest) {
        var client = clientService.findById(rent.clientId)
        var book = bookService.findById(rent.bookId)
        rentService.create(rent.toRentModel(client, book))
    }

    @GetMapping
    fun findAll():List<RentModel> =
        rentService.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): RentModel =
        rentService.findById(id)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun returnBook(@PathVariable id: Int, @RequestBody rent: PutRentRequest) {
        val rentSaved = rentService.findById(id)
        rentService.returnBook(rent.toRentModel(rentSaved))
    }
}