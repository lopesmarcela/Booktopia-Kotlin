package com.booktopia.services

import com.booktopia.enums.Errors
import com.booktopia.enums.StatusEnum
import com.booktopia.exception.BadRequestException
import com.booktopia.exception.NotFoundException
import com.booktopia.models.BookModel
import com.booktopia.repositories.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository
) {

    fun create(book: BookModel){
        bookRepository.save(book)
    }

    fun findAll(title: String?, pageable: Pageable): Page<BookModel> {
        title?.let {
            return bookRepository.findByTitleContaining(it, pageable)
        }
        return bookRepository.findAll(pageable)
    }

    fun findById(id: Int): BookModel{
        return bookRepository.findById(id).orElseThrow{ NotFoundException(Errors.B201.message.format(id), Errors.B201.code) }
    }

    fun update(book: BookModel){
        bookRepository.save(book)
    }

    fun delete(id: Int){
        val book = findById(id)
        findBookInactive(id)
        book.status = StatusEnum.INACTIVE
        update(book)
    }

    fun findBookInactive(id:Int){
        val book = findById(id)
        if(book.status == StatusEnum.INACTIVE)
            throw BadRequestException(Errors.B203.message.format(book.id), Errors.B203.code)
    }

    fun decreasesStock(book: BookModel) {
        book.inventory -= 1
        if (book.inventory == 0) {
            delete(book.id!!)
        }
        update(book)
    }

    fun increasesStock(book: BookModel) {
        book.inventory += 1
        book.status = if (book.inventory > 0) StatusEnum.ACTIVE else StatusEnum.INACTIVE
        update(book)
    }

}