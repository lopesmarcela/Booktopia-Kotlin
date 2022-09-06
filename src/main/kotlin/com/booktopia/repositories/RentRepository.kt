package com.booktopia.repositories

import com.booktopia.models.RentModel
import org.springframework.data.jpa.repository.JpaRepository


interface RentRepository: JpaRepository<RentModel, Int> {
}