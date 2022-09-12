package com.booktopia.services

import com.booktopia.enums.Errors
import com.booktopia.enums.StatusEnum
import com.booktopia.exception.BadRequestException
import com.booktopia.exception.NotFoundException
import com.booktopia.extensions.calculateFine
import com.booktopia.models.BookModel
import com.booktopia.models.RentModel
import com.booktopia.repositories.RentRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.Vector
import kotlin.Exception

@Service
class RentService(
    private val rentRepository: RentRepository,
    private val bookService: BookService,
    private val clientService: ClientService
) {

    fun create(rent: RentModel){
        var book = bookService.findById(rent.book!!.id!!)
        var client = clientService.findById(rent.client!!.id!!)
        if(book.status == StatusEnum.INACTIVE){
            throw BadRequestException(Errors.B302.message.format(book.id), Errors.B302.code)
        }
        if(client.status == StatusEnum.INACTIVE){
            throw BadRequestException(Errors.B303.message.format(book.id), Errors.B303.code)
        }
        //decreasing 1 book in stock
        book.inventory-= 1
        //deactivating book if there is no stock
        if(book.inventory == 0){
            bookService.delete(book.id!!)
        }
        bookService.update(book)
        rentRepository.save(rent)
    }

    fun findAll(pageable: Pageable): Page<RentModel> {
        return rentRepository.findAll(pageable)
    }

    fun findById(id: Int): RentModel{
        return rentRepository.findById(id).orElseThrow{ NotFoundException(Errors.B301.message.format(id), Errors.B301.code) }
    }

    fun returnBook(rent: RentModel){
        rent.fine = rent.calculateFine(rent)

        var book: BookModel = bookService.findById(rent.book!!.id!!)
        rent.totalValue = rent.fine!! + book.price

        book.inventory += 1
        book.status = if (book.inventory > 0) StatusEnum.ACTIVE else StatusEnum.INACTIVE

        bookService.update(book)
        rentRepository.save(rent)
    }
}