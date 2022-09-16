package com.booktopia.services

import com.booktopia.enums.Errors
import com.booktopia.enums.Role
import com.booktopia.enums.StatusEnum
import com.booktopia.exception.BadRequestException
import com.booktopia.exception.NotFoundException
import com.booktopia.models.AdminModel
import com.booktopia.repositories.AdminRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class AdminService(
    private val adminRepository: AdminRepository,
    private val bCrypt: BCryptPasswordEncoder
) {

    fun create(admin: AdminModel){
        val adminCopy = admin.copy(
            roles = setOf(Role.ADMIN),
            password = bCrypt.encode(admin.password)
        )
        adminRepository.save(adminCopy)
    }

    fun findAll(pageable: Pageable): Page<AdminModel> {
        return adminRepository.findAll(pageable)
    }

    fun findById(id: Int): AdminModel{
        return adminRepository.findById(id).orElseThrow{NotFoundException(Errors.B501.message.format(id), Errors.B501.code)}
    }

    fun update(admin: AdminModel){
        if (!adminRepository.existsById(admin.id!!)){
            throw NotFoundException(Errors.B501.message.format(admin.id!!), Errors.B501.code)
        }
        adminRepository.save(admin)
    }

    fun delete(id: Int){
        val admin = findById(id)
        findAdminInactive(id)
        admin.status = StatusEnum.INACTIVE
        update(admin)
    }

    fun findAdminInactive(id: Int){
        val admin = findById(id)
        if (admin.status == StatusEnum.INACTIVE){
            throw BadRequestException(Errors.B503.message.format(id), Errors.B503.code)
        }
    }
}