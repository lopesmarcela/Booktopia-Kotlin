package com.booktopia.controllers.request

import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

data class PostAddressRequest (
    var street: String,
    var number: Int,
    var district: String,
    var city: String,
    var cep: String
)