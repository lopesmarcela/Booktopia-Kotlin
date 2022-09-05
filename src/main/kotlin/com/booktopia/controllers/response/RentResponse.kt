package com.booktopia.controllers.response

import com.booktopia.enums.StatusEnum
import java.math.BigDecimal

data class RentResponse(
    var id: Int? = null,
    var fine: BigDecimal? = null,
    var totalValue: BigDecimal? = null,
    var rentalDate: String,
    var returnDate: String? = "",
    var status: StatusEnum? = null,
    var clientId: Int?,
    var bookId: Int?
) {
}