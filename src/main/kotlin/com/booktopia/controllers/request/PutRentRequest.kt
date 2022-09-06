package com.booktopia.controllers.request

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PutRentRequest (
    var fine: BigDecimal?,
    @JsonAlias("total_value")
    var totalValue: BigDecimal?,
    @JsonAlias("return_date")
    @field:NotEmpty(message = "Return date must be informed")
    var returnDate: String
)