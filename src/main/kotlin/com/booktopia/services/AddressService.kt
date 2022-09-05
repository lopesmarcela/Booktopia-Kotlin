package com.booktopia.services

import com.booktopia.enums.StatusEnum
import com.booktopia.models.AddressModel
import com.booktopia.repositories.AddressRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class AddressService(
    val addressRepository: AddressRepository
) {

    fun create(address: AddressModel){
        addressRepository.save(address)
    }

    fun findAll(pageable: Pageable): Page<AddressModel> {
        return addressRepository.findAll(pageable)
    }

    fun findById(id: Int): AddressModel{
        return addressRepository.findById(id).orElseThrow()
    }

    fun update(address: AddressModel){
        addressRepository.save(address)
    }

    fun delete(id: Int){
        val address = findById(id)
        address.status = StatusEnum.INACTIVE
        update(address)
    }
}