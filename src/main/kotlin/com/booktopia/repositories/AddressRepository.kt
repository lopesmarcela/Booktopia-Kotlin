package com.booktopia.repositories

import com.booktopia.models.AddressModel
import org.springframework.data.repository.CrudRepository

interface AddressRepository: CrudRepository<AddressModel, Int> {
}