package com.booktopia.controllers.request

data class PostClientRequest (
    var cpf: String,
    var name: String,
    var email: String,
    var address: String
)