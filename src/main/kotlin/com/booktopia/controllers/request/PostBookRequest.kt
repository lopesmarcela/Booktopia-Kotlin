package com.booktopia.controllers.request

import com.booktopia.enums.CategoryEnum
import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PostBookRequest (
    @field:NotEmpty(message = "Title must be informed")
    var title: String,
    @field:NotEmpty(message = "Description must be informed")
    var description: String,
    @JsonAlias("release_date")
    @field:NotEmpty(message = "Release date must be informed")
    var releaseDate: String,
    @field:NotEmpty(message = "Category must be informed")
    var category: CategoryEnum,
    @field:NotEmpty(message = "Author must be informed")
    var author: String,
    @field:NotNull(message = "Price must be informed")
    var price: BigDecimal,
    @field:NotNull(message = "Inventory must be informed")
    var inventory: Int
)