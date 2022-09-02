package com.booktopia.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "adresses")
data class AddressModel (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @Column
    var street: String,
    @Column
    var number: Int,
    @Column
    var district: String,
    @Column
    var city: String,
    @Column
    var cep: String
){
}