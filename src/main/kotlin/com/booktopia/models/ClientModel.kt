package com.booktopia.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "clients")
data class ClientModel (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @Column
    var cpf: String,
    @Column
    var name: String,
    @Column
    var email: String,
    @Column
    var address: String
){
}