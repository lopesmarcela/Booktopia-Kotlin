package com.booktopia.controllers

import com.booktopia.controllers.request.PostBookRequest
import com.booktopia.controllers.request.PutBookRequest
import com.booktopia.enums.CategoryEnum
import com.booktopia.enums.StatusBook
import com.booktopia.extensions.toBookModel
import com.booktopia.models.BookModel
import com.booktopia.services.BookService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("book")
class BookController(
    val bookService: BookService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody book: PostBookRequest) {
        bookService.create(book.toBookModel())
    }

    @GetMapping
    fun findAll(@RequestParam title: String?): List<BookModel> =
        bookService.findAll(title)

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): BookModel =
        bookService.findById(id)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody book: PutBookRequest) {
        val bookSaved = bookService.findById(id)
        bookService.update(book.toBookModel(bookSaved))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        bookService.delete(id)
    }
}