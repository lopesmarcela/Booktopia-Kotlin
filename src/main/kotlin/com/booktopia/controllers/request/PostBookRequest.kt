package com.booktopia.controllers.request

import com.booktopia.enums.CategoryEnum
import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import java.time.LocalDate
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PostBookRequest (
    @field:NotEmpty(message = "Title must be informed")
    var title: String,
    @field:NotEmpty(message = "Description must be informed")
    var description: String,
    @JsonAlias("release_date")
    var releaseDate: LocalDate,
    var category: CategoryEnum,
    @field:NotEmpty(message = "Author must be informed")
    var author: String,
    @field:NotNull(message = "Price must be informed")
    var price: BigDecimal,
    @field:NotNull(message = "Inventory must be informed")
    var inventory: Int
)