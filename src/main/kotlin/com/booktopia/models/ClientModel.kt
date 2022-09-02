package com.booktopia.models

import com.booktopia.enums.StatusEnum
import javax.persistence.*

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
    @ManyToOne
    @JoinColumn(name = "address_id")
    var address: AddressModel? = null,
    @Column
    @Enumerated(EnumType.STRING)
    var status: StatusEnum? = null
){
}