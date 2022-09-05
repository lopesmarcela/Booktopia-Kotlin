package com.booktopia.repositories

import com.booktopia.models.AddressModel
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository: JpaRepository<AddressModel, Int> {
}