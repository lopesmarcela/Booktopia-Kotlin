package com.booktopia.controllers.request

import com.booktopia.enums.CategoryEnum
import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal

data class PostBookRequest (
    var title: String,
    var description: String,
    @JsonAlias("release_date")
    var releaseDate: String,
    var category: CategoryEnum,
    var author: String,
    var price: BigDecimal,
    var inventory: Int
)