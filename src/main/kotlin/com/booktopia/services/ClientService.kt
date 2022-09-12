package com.booktopia.services

import com.booktopia.enums.Errors
import com.booktopia.enums.StatusEnum
import com.booktopia.exception.BadRequestException
import com.booktopia.exception.NotFoundException
import com.booktopia.models.ClientModel
import com.booktopia.repositories.ClientRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class ClientService(
    private val clientRepository: ClientRepository,
    private val addressService: AddressService
) {

    fun create(client: ClientModel){
        var address = addressService.findById(client.address!!.id!!)
        if( address.status == StatusEnum.INACTIVE)
            throw BadRequestException(Errors.B403.message.format(address.id), Errors.B403.code)
        clientRepository.save(client)
    }

    fun findAll(name: String?, pageable: Pageable): Page<ClientModel> {
        name?.let {
            return clientRepository.findByNameContaining(it, pageable)
        }
        return clientRepository.findAll(pageable!!)
    }

    fun findById(id: Int): ClientModel{
        return clientRepository.findById(id).orElseThrow{NotFoundException(Errors.B101.message.format(id), Errors.B101.code)}
    }

    fun findByActive():List<ClientModel>{
        return clientRepository.findByStatus(StatusEnum.ACTIVE)
    }

    fun update(client: ClientModel){
        clientRepository.save(client)
    }

    fun delete(id: Int){
        var client = findById(id)
        if (client.status == StatusEnum.INACTIVE){
            throw BadRequestException(Errors.B103.message.format(id), Errors.B103.code)
        }
        client.status = StatusEnum.INACTIVE

        //disabling customer address
        var address = addressService.findById(client.address!!.id!!)
        addressService.delete(address.id!!)

        update(client)
    }

    fun emailAvailable(email: String): Boolean {
        return !clientRepository.existsByEmail(email)
    }

    fun cpfAvailable(cpf: String): Boolean {
        return !clientRepository.existsByCpf(cpf)
    }
}