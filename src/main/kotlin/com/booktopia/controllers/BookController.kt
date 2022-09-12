package com.booktopia.controllers

import com.booktopia.controllers.request.PostBookRequest
import com.booktopia.controllers.request.PutBookRequest
import com.booktopia.extensions.toBookModel
import com.booktopia.extensions.toPageResponse
import com.booktopia.models.BookModel
import com.booktopia.services.BookService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import com.booktopia.controllers.response.PageResponse

@RestController
@Api(value = "", tags = ["Book"])
@RequestMapping("books")
class BookController(
    private val bookService: BookService
) {

    @PostMapping
    @ApiOperation(value = "Creates a book")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid book: PostBookRequest) {
        bookService.create(book.toBookModel())
    }

    @GetMapping
    @ApiOperation(value = "Returns a list of books")
    fun findAll(@RequestParam title: String?, @PageableDefault(page = 0, size = 5) pageable: Pageable): PageResponse<BookModel> =
        bookService.findAll(title, pageable).toPageResponse()

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns a book by id")
    fun findById(@PathVariable id: Int): BookModel =
        bookService.findById(id)

    @PutMapping("/{id}")
    @ApiOperation(value = "Changes a book")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody book: PutBookRequest) {
        val bookSaved = bookService.findById(id)
        bookService.update(book.toBookModel(bookSaved))
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Disables a book")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        bookService.delete(id)
    }
}