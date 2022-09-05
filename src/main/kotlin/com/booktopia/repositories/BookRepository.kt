package com.booktopia.repositories

import com.booktopia.models.BookModel
import org.springframework.data.repository.CrudRepository

interface BookRepository: CrudRepository<BookModel, Int> {
    fun findByTitleContaining(title: String):List<BookModel>
}