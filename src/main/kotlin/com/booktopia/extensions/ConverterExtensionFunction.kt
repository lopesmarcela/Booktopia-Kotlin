package com.booktopia.extensions

import com.booktopia.controllers.request.PostAddressRequest
import com.booktopia.controllers.request.PostClientRequest
import com.booktopia.controllers.request.PutAddressRequest
import com.booktopia.controllers.request.PutClientRequest
import com.booktopia.models.AddressModel
import com.booktopia.models.ClientModel

fun PostClientRequest.toClientModel(): ClientModel{
    return ClientModel(cpf = this.cpf, name = this.name, email = this.email )
}

fun PutClientRequest.toClientModel(id: Int): ClientModel{
    return ClientModel(id = id,cpf = this.cpf, name = this.name, email = this.email)
}

fun PostAddressRequest.toAddressModel(): AddressModel{
    return AddressModel(
        street = this.street,
        number = this.number,
        district = this.district,
        city = this.city,
        cep = this.cep
    )
}

fun PutAddressRequest.toAddressModel(id: Int): AddressModel{
    return AddressModel(
        street = this.street,
        number = this.number,
        district = this.district,
        city = this.city,
        cep = this.cep
    )
}