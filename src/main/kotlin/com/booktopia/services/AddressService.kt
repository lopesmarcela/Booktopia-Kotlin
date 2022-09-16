package com.booktopia.services

import com.booktopia.enums.Errors
import com.booktopia.enums.StatusEnum
import com.booktopia.exception.BadRequestException
import com.booktopia.exception.NotFoundException
import com.booktopia.models.AddressModel
import com.booktopia.repositories.AddressRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class AddressService(
    private val addressRepository: AddressRepository
) {

    fun create(address: AddressModel){
        addressRepository.save(address)
    }

    fun findAll(pageable: Pageable): Page<AddressModel> {
        return addressRepository.findAll(pageable)
    }

    fun findById(id: Int): AddressModel{
        return addressRepository.findById(id).orElseThrow {NotFoundException(Errors.B401.message.format(id), Errors.B401.code)}
    }

    fun update(address: AddressModel){
        if (!addressRepository.existsById(address.id!!)){
            throw NotFoundException(Errors.B401.message.format(address.id!!), Errors.B401.code)
        }
        addressRepository.save(address)
    }

    fun delete(id: Int){
        val address = findById(id)
        findAddressInactive(id)
        address.status = StatusEnum.INACTIVE
        update(address)
    }

    fun findAddressInactive(id: Int){
        val address = findById(id)
        if( address.status == StatusEnum.INACTIVE)
            throw BadRequestException(Errors.B403.message.format(address.id), Errors.B403.code)
    }
}