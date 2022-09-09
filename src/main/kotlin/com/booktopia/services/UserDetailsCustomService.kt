package com.booktopia.services

import com.booktopia.exception.AuthenticationException
import com.booktopia.repositories.AdminRepository
import com.booktopia.security.UserCustomDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsCustomService(
    private val adminRepository: AdminRepository
): UserDetailsService {
    override fun loadUserByUsername(id: String): UserDetails {
        val admin = adminRepository.findById(id.toInt())
            .orElseThrow{AuthenticationException("fail","999")}
        return UserCustomDetails(admin)
    }
}