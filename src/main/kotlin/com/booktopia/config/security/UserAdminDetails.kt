package com.booktopia.config.security

import com.booktopia.enums.StatusEnum
import com.booktopia.models.AdminModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserAdminDetails(private val adminModel: AdminModel): UserDetails {
    val id:Int = adminModel.id!!

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>
        = adminModel.roles.map { SimpleGrantedAuthority(it.description) }.toMutableList()

    override fun getPassword(): String = adminModel.password

    override fun getUsername(): String = adminModel.email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = adminModel.status == StatusEnum.ACTIVE

}