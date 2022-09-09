package com.booktopia.controllers.request

import com.booktopia.validations.CPFValidation
import com.booktopia.validations.EmailValidation
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PostAdminRequest (
    @field:CPF(message = "Enter a valid CPF")
    @CPFValidation
    var cpf: String,
    @field:NotEmpty(message = "Name must be informed")
    var name: String,
    @field:Email(message = "Invalid email format")
    @EmailValidation
    var email: String,
    @field:NotEmpty(message = "Password must be informed")
    var password: String
)