package com.booktopia.models

data class ClientModel (
    var idClient: Int,
    var cpf: String,
    var name: String,
    var email: String,
    var address: String
){
}