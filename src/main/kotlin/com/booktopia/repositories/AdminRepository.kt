package com.booktopia.repositories


import com.booktopia.models.AdminModel
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository: JpaRepository<AdminModel, Int> {

    fun existsByEmail(email: String) : Boolean
    fun existsByCpf(cpf: String): Boolean
}