package com.booktopia.controllers.request

import com.booktopia.enums.StatusEnum
import java.math.BigDecimal

data class PutBookRequest (
    var price: BigDecimal?,
    var inventory: Int?
)