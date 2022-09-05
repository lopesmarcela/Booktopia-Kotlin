package com.booktopia.controllers.request

import java.math.BigDecimal

data class PutBookRequest (
    var price: BigDecimal?,
    var inventory: Int?
)