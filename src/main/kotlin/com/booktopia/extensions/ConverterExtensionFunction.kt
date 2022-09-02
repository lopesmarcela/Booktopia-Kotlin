package com.booktopia.extensions

import com.booktopia.controllers.request.PostClientRequest
import com.booktopia.controllers.request.PutClientRequest
import com.booktopia.models.ClientModel

fun PostClientRequest.toClientModel(): ClientModel{
    return ClientModel(cpf = this.cpf, name = this.name, email = this.email ,address = this.address)
}

fun PutClientRequest.toClientModel(id: Int): ClientModel{
    return ClientModel(idClient = id,cpf = this.cpf, name = this.name, email = this.email ,address = this.address)
}