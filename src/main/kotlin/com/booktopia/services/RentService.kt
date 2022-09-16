package com.booktopia.services

import com.booktopia.enums.Errors
import com.booktopia.exception.BadRequestException
import com.booktopia.exception.NotFoundException
import com.booktopia.extensions.calculateFine
import com.booktopia.models.BookModel
import com.booktopia.models.RentModel
import com.booktopia.repositories.RentRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class RentService(
    private val rentRepository: RentRepository,
    private val bookService: BookService,
    private val clientService: ClientService
) {

    fun create(rent: RentModel){
        val book = bookService.findById(rent.book!!.id!!)
        val client = clientService.findById(rent.client!!.id!!)

        bookService.findBookInactive(book.id!!)
        clientService.findClientInactive(client.id!!)

        bookService.decreasesStock(book)

        rentRepository.save(rent)
    }

    fun findAll(pageable: Pageable): Page<RentModel> {
        return rentRepository.findAll(pageable)
    }

    fun findById(id: Int): RentModel{
        return rentRepository.findById(id).orElseThrow{ NotFoundException(Errors.B301.message.format(id), Errors.B301.code) }
    }

    fun returnBook(rent: RentModel){
        if (!rentRepository.existsById(rent.id!!)){
            throw NotFoundException(Errors.B301.message.format(rent.id!!), Errors.B301.code)
        }
        rent.fine = rent.calculateFine(rent)

        val book: BookModel = bookService.findById(rent.book!!.id!!)
        rent.totalValue = rent.fine!! + book.price

        bookService.increasesStock(book)

        rentRepository.save(rent)
    }


}