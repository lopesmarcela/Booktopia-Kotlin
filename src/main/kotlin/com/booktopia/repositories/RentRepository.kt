package com.booktopia.repositories

import com.booktopia.models.RentModel
import org.springframework.data.repository.CrudRepository

interface RentRepository: CrudRepository<RentModel, Int> {
}