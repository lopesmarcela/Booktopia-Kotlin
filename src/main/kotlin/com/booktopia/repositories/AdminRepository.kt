package com.booktopia.repositories


import com.booktopia.models.AdminModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AdminRepository: JpaRepository<AdminModel, Int> {
    fun findByEmail(email: String): Optional<AdminModel>
}