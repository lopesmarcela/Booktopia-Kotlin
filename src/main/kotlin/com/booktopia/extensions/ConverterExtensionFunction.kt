package com.booktopia.extensions

import com.booktopia.controllers.request.PostAddressRequest
import com.booktopia.controllers.request.PostClientRequest
import com.booktopia.controllers.request.PutAddressRequest
import com.booktopia.controllers.request.PutClientRequest
import com.booktopia.models.AddressModel
import com.booktopia.models.ClientModel

fun PostClientRequest.toClientModel(address: AddressModel): ClientModel{
    return ClientModel(
        cpf = this.cpf,
        name = this.name,
        email = this.email,
        address = address
    )
}

fun PutClientRequest.toClientModel(previousValue: ClientModel): ClientModel{
    return ClientModel(
        id = previousValue.id,
        cpf = previousValue.cpf,
        name = this.name?:previousValue.name,
        email = this.email?:previousValue.email,
        address = previousValue.address
    )
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

fun PutAddressRequest.toAddressModel(previousValue: AddressModel): AddressModel{
    return AddressModel(
        id = previousValue.id,
        street = this.street?:previousValue.street,
        number = this.number?:previousValue.number,
        district = this.district?:previousValue.district,
        city = this.city?:previousValue.city,
        cep = this.cep?:previousValue.cep
    )
}