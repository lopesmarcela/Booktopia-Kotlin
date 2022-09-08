package com.booktopia.controllers.response

import com.booktopia.enums.StatusEnum
import java.math.BigDecimal
import java.time.LocalDate

data class RentResponse(
    var id: Int? = null,
    var fine: BigDecimal? = null,
    var totalValue: BigDecimal? = null,
    var rentalDate: LocalDate,
    var returnDate: LocalDate?,
    var status: StatusEnum? = null,
    var clientId: Int?,
    var bookId: Int?
) {
}