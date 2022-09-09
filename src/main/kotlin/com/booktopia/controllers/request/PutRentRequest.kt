package com.booktopia.controllers.request

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import java.time.LocalDate
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PutRentRequest (
    @JsonAlias("return_date")
    var returnDate: LocalDate
)