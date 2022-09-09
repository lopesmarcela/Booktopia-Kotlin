package com.booktopia.controllers.request

data class LoginRequest(
    val email: String,
    val password: String
) {
}