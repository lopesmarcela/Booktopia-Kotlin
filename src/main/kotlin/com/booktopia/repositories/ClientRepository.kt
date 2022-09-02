package com.booktopia.repositories

import com.booktopia.models.ClientModel
import org.springframework.data.repository.CrudRepository

interface ClientRepository: CrudRepository<ClientModel, Int> {
    fun findByNameContaining(name: String):List<ClientModel>
}