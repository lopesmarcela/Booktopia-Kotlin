package com.booktopia.controllers.request

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal

data class PutRentRequest (
    var fine: BigDecimal?,
    @JsonAlias("total_value")
    var totalValue: BigDecimal?,
    @JsonAlias("return_date")
    var returnDate: String
)