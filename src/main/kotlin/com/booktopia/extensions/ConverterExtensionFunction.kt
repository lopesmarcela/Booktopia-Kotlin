package com.booktopia.extensions

import com.booktopia.controllers.request.*
import com.booktopia.enums.StatusEnum
import com.booktopia.models.AddressModel
import com.booktopia.models.BookModel
import com.booktopia.models.ClientModel
import com.booktopia.models.RentModel

fun PostClientRequest.toClientModel(address: AddressModel): ClientModel{
    return ClientModel(
        cpf = this.cpf,
        name = this.name,
        email = this.email,
        address = address,
        status = StatusEnum.ACTIVE
    )
}

fun PutClientRequest.toClientModel(previousValue: ClientModel): ClientModel{
    return ClientModel(
        id = previousValue.id,
        cpf = previousValue.cpf,
        name = this.name?:previousValue.name,
        email = this.email?:previousValue.email,
        address = previousValue.address,
        status = previousValue.status
    )
}

fun PostAddressRequest.toAddressModel(): AddressModel{
    return AddressModel(
        street = this.street,
        number = this.number,
        district = this.district,
        city = this.city,
        cep = this.cep,
        status = StatusEnum.ACTIVE
    )
}

fun PutAddressRequest.toAddressModel(previousValue: AddressModel): AddressModel{
    return AddressModel(
        id = previousValue.id,
        street = this.street?:previousValue.street,
        number = this.number?:previousValue.number,
        district = this.district?:previousValue.district,
        city = this.city?:previousValue.city,
        cep = this.cep?:previousValue.cep,
        status = previousValue.status
    )
}

fun PostBookRequest.toBookModel(): BookModel{
    return BookModel(
        title = this.title,
        description = this.description,
        releaseDate = this.releaseDate,
        category = this.category,
        author = this.author,
        price = this.price,
        inventory = this.inventory,
        status = StatusEnum.ACTIVE
    )
}

fun PutBookRequest.toBookModel(previousValue: BookModel): BookModel{
    return BookModel(
        id = previousValue.id,
        title = previousValue.title,
        description = previousValue.description,
        releaseDate = previousValue.releaseDate,
        category = previousValue.category,
        author = previousValue.author,
        price = this.price?:previousValue.price,
        inventory = this.inventory?:previousValue.inventory,
        status = this.status?:previousValue.status
    )
}

fun PostRentRequest.toRentModel(client: ClientModel, book: BookModel):RentModel{
    return RentModel(
        rentalDate = this.rentalDate,
        client = client,
        book = book,
        status = StatusEnum.ACTIVE
    )
}

fun PutRentRequest.toRentModel(previousValue: RentModel):RentModel{
    return RentModel(
        id = previousValue.id,
        fine = this.fine?: previousValue.fine,
        totalValue = this.totalValue?: previousValue.totalValue,
        rentalDate = previousValue.rentalDate,
        returnDate = this.returnDate,
        client = previousValue.client,
        book = previousValue.book,
        status = StatusEnum.INACTIVE
    )
}