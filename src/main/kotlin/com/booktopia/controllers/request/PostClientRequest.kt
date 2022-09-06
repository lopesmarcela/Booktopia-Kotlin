package com.booktopia.controllers.request

import com.booktopia.validations.CPFValidation
import com.booktopia.validations.EmailValidation
import com.fasterxml.jackson.annotation.JsonAlias
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PostClientRequest (
    @field:CPF(message = "Enter a valid CPF")
    @CPFValidation
    var cpf: String,
    @field:NotEmpty(message = "Name must be informed")
    var name: String,
    @field:Email(message = "Invalid email format")
    @EmailValidation
    var email: String,
    @JsonAlias("address_id")
    var addressId: Int
)