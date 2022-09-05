package com.booktopia.services

import com.booktopia.enums.StatusEnum
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
    val rentRepository: RentRepository,
    val bookService: BookService
) {

    fun create(rent: RentModel){
        var book = bookService.findById(rent.book!!.id!!)
        if(book.status == StatusEnum.INACTIVE){
            throw Exception("Book Inactive!")
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
        return rentRepository.findById(id).orElseThrow()
    }

    fun returnBook(rent: RentModel){
        if (rent.status == StatusEnum.INACTIVE){
            throw Exception("This book has already been returned")
        }
        // converting rental date to LocalDateTime
        var splitRetalDate = Vector<String>(rent.rentalDate.split("/"))
        var year = splitRetalDate[0].toInt()
        var mounth = splitRetalDate[1].toInt()
        var day = splitRetalDate[2].toInt()
        var rentalDate2: LocalDateTime = LocalDateTime.of(year, mounth, day,0,0,0)

        // converting rental date to LocalDateTime
        var splitReturnDate = Vector<String>(rent.returnDate!!.split("/"))
        var yearReturn = splitReturnDate[0].toInt()
        var mounthReturn = splitReturnDate[1].toInt()
        var dayReturn = splitReturnDate[2].toInt()
        var returnDate2: LocalDateTime = LocalDateTime.of(yearReturn, mounthReturn, dayReturn,0,0,0)

        //calculating difference of days
        var totalDays: Long = rentalDate2.until(returnDate2, ChronoUnit.DAYS)
        // calculating difference of 10 days
        var fineDays: Int =  totalDays.toInt() - 10

        rent.fine = if(fineDays > 0 ) (fineDays * 1.00).toBigDecimal() else 0.0.toBigDecimal()

        var book: BookModel = bookService.findById(rent.book!!.id!!)
        rent.totalValue = rent.fine!! + book.price

        book.inventory += 1
        book.status = StatusEnum.ACTIVE

        bookService.update(book)
        rentRepository.save(rent)
    }
}