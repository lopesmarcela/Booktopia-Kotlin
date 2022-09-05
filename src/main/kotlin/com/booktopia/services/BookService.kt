package com.booktopia.services

import com.booktopia.enums.StatusEnum
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
        return bookRepository.findById(id).orElseThrow()
    }

    fun update(book: BookModel){
        bookRepository.save(book)
    }

    fun delete(id: Int){
        var book = findById(id)
        book.status = StatusEnum.INACTIVE
        update(book)
    }
}