package com.booktopia.repositories

import com.booktopia.enums.StatusEnum
import com.booktopia.models.ClientModel
import org.springframework.data.repository.CrudRepository

interface ClientRepository: CrudRepository<ClientModel, Int> {

    fun findByStatus(status: StatusEnum): List<ClientModel>
    fun findByNameContaining(name: String):List<ClientModel>
}