package com.booktopia.config.security

import com.booktopia.enums.Errors
import com.booktopia.exception.AuthenticationException
import com.booktopia.repositories.AdminRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserDetailsAdminService(
    private val adminRepository: AdminRepository
): UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val admin = adminRepository.findByEmail(email)
            .orElseThrow{AuthenticationException(Errors.B002.message,Errors.B002.code)}
        return UserAdminDetails(admin)
    }
}