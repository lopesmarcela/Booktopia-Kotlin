package com.booktopia.services

import com.booktopia.models.AddressModel
import com.booktopia.repositories.AddressRepository
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class AddressService(
    val addressRepository: AddressRepository
) {

    fun create(address: AddressModel){
        addressRepository.save(address)
    }

    fun findAll(): List<AddressModel> {
        return addressRepository.findAll().toList()
    }

    fun findById(id: Int): AddressModel{
        return addressRepository.findById(id).orElseThrow()
    }

    fun update(address: AddressModel){
        if (!addressRepository.existsById(address.id!!)){
            throw Exception()
        }

        addressRepository.save(address)
    }

    fun delete(id: Int){
        if (!addressRepository.existsById(id)){
            throw Exception()
        }

        addressRepository.deleteById(id)
    }
}