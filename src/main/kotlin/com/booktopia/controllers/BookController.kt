package com.booktopia.controllers

import com.booktopia.controllers.request.PostBookRequest
import com.booktopia.controllers.request.PutBookRequest
import com.booktopia.extensions.toBookModel
import com.booktopia.models.BookModel
import com.booktopia.services.BookService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("book")
class BookController(
    val bookService: BookService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid book: PostBookRequest) {
        //bookService.create(book.toBookModel())
    }

    @GetMapping
    fun findAll(@RequestParam title: String?, @PageableDefault(page = 0, size = 5) pageable: Pageable): Page<BookModel> =
        bookService.findAll(title, pageable)

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