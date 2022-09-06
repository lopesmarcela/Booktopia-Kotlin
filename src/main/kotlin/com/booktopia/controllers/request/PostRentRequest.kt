package com.booktopia.controllers.request

import com.fasterxml.jackson.annotation.JsonAlias

data class PostRentRequest (
    @JsonAlias("rental_date")
    var rentalDate: String,
    @JsonAlias("client_id")
    var clientId: Int,
    @JsonAlias("book_id")
    var bookId: Int
)