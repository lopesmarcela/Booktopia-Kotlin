package com.booktopia.repositories

import com.booktopia.models.BookModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository: JpaRepository<BookModel, Int> {
    fun findByTitleContaining(title: String, pageable: Pageable):Page<BookModel>
}