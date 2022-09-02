package com.booktopia.controllers.request
data class PostAddressRequest (
    var street: String,
    var number: Int,
    var district: String,
    var city: String,
    var cep: String
)