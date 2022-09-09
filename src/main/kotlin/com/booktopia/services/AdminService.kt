package com.booktopia.services

import com.booktopia.enums.Errors
import com.booktopia.enums.Profile
import com.booktopia.enums.StatusEnum
import com.booktopia.exception.BadRequestException
import com.booktopia.exception.NotFoundException
import com.booktopia.models.AdminModel
import com.booktopia.repositories.AdminRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


@Service
class AdminService(
    val adminRepository: AdminRepository
) {

    fun create(admin: AdminModel){
        val adminCopy = admin.copy(
            roles = setOf(Profile.ADMIN)
        )
        adminRepository.save(adminCopy)
    }

    fun findAll( pageable: Pageable): Page<AdminModel> {
        return adminRepository.findAll(pageable!!)
    }

    fun findById(id: Int): AdminModel{
        return adminRepository.findById(id).orElseThrow{NotFoundException(Errors.B501.message.format(id), Errors.B501.code)}
    }

    fun update(admin: AdminModel){
        adminRepository.save(admin)
    }

    fun delete(id: Int){
        var admin = findById(id)
        if (admin.status == StatusEnum.INACTIVE){
            throw BadRequestException(Errors.B503.message.format(id), Errors.B503.code)
        }
        admin.status = StatusEnum.INACTIVE
        update(admin)
    }

    fun emailAvailable(email: String): Boolean {
        return !adminRepository.existsByEmail(email)
    }

    fun cpfAvailable(cpf: String): Boolean {
        return !adminRepository.existsByCpf(cpf)
    }
}