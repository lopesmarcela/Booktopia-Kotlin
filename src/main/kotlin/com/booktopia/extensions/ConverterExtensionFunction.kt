package com.booktopia.extensions

import com.booktopia.controllers.request.*
import com.booktopia.controllers.response.ClientResponse
import com.booktopia.controllers.response.RentResponse
import com.booktopia.enums.StatusEnum
import com.booktopia.models.*
import java.math.BigDecimal
import java.time.temporal.ChronoUnit

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

fun PostAdminRequest.toAdminModel(): AdminModel{
    return AdminModel(
        cpf = this.cpf,
        name = this.name,
        email = this.email,
        password = this.password,
        status = StatusEnum.ACTIVE
    )
}

fun PutAdminRequest.toAdminModel(previousValue: AdminModel): AdminModel{
    return AdminModel(
        id = previousValue.id,
        cpf = previousValue.cpf,
        name = this.name?:previousValue.name,
        email = this.email?:previousValue.email,
        password = previousValue.password,
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
        status = previousValue.status
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
        fine = previousValue.fine,
        totalValue = previousValue.totalValue,
        rentalDate = previousValue.rentalDate,
        returnDate = this.returnDate,
        client = previousValue.client,
        book = previousValue.book,
        status = StatusEnum.INACTIVE
    )
}

fun RentModel.toResponse(): RentResponse{
    return RentResponse(
        id = this.id,
        fine = this.fine,
        totalValue = this.totalValue,
        rentalDate = this.rentalDate,
        returnDate = this.returnDate,
        status = this.status,
        clientId = this.client!!.id,
        bookId = this.book!!.id
    )
}

fun ClientModel.toResponse(): ClientResponse{
    return ClientResponse(
        id = this.id,
        cpf = this.cpf,
        name = this.name,
        email = this.email,
        addressId = this.address!!.id,
        status = this.status
    )
}

fun RentModel.calculateFine(rent: RentModel): BigDecimal{
    //calculating difference of days
    var totalDays: Long = rent.rentalDate.until(rent.returnDate, ChronoUnit.DAYS)
    // calculating difference of 10 days
    var fineDays: Int =  totalDays.toInt() - 10

    return if(fineDays > 0 ) (fineDays * 1.00).toBigDecimal() else 0.0.toBigDecimal()
}
