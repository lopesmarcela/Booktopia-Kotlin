package com.booktopia.models

import javax.persistence.Entity

@Entity(name="clients")
data class ClientModel (
    var id: Int,
    var CPF: String,
    var name: String,
    var email: String,
    var address: String
){
}