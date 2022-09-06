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
    val bookRepository: BookRepository
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
        var book = findById(id)
        if (book.status == StatusEnum.INACTIVE){
            throw BadRequestException(Errors.B203.message.format(id), Errors.B203.code)
        }
        book.status = StatusEnum.INACTIVE
        update(book)
    }
}