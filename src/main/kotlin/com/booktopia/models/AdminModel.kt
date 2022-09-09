package com.booktopia.models

import com.booktopia.enums.Role
import com.booktopia.enums.StatusEnum
import javax.persistence.*

@Entity(name = "admins")
data class AdminModel (
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
    var password: String,
    @Column
    @Enumerated(EnumType.STRING)
    var status: StatusEnum? = null,

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name="admin_roles", joinColumns = [JoinColumn(name = "admin_id")])
    var roles: Set<Role> = setOf()
){
}