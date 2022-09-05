package com.booktopia.repositories

import com.booktopia.enums.StatusEnum
import com.booktopia.models.ClientModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ClientRepository: JpaRepository<ClientModel, Int> {

    fun findByStatus(status: StatusEnum): List<ClientModel>
    fun findByNameContaining(name: String, pageable: Pageable):Page<ClientModel>
}