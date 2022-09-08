package com.booktopia.models

import com.booktopia.enums.StatusEnum
import io.swagger.annotations.ApiModelProperty
import javax.persistence.*

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
    var cep: String,
    @Column
    @Enumerated(EnumType.STRING)
    var status: StatusEnum? = null

){
}