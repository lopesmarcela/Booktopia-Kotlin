package com.booktopia.controllers.request

import com.fasterxml.jackson.annotation.JsonAlias

data class PostClientRequest (
    var cpf: String,
    var name: String,
    var email: String,
    @JsonAlias("address_id")
    var addressId: Int
)