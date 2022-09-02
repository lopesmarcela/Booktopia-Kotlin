package com.booktopia.controllers.request

data class PutClientRequest(
    var name: String,
    var email: String,
    var address: String
) {
}