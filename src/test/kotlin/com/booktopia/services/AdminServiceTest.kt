package com.booktopia.services

import com.booktopia.enums.Errors
import com.booktopia.enums.Role
import com.booktopia.enums.StatusEnum
import com.booktopia.exception.NotFoundException
import com.booktopia.models.AdminModel
import com.booktopia.repositories.AdminRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import net.bytebuddy.utility.RandomString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
class AdminServiceTest{
    @MockK
    private lateinit var adminRepository: AdminRepository
    @MockK
    private lateinit var bCrypt: BCryptPasswordEncoder

    @InjectMockKs
    @SpyK
    private lateinit var adminService: AdminService

    @Test
    fun `should return all admins`(){

        val pageable: Pageable = PageRequest.of(0,5)

        val fakeAdmins: Page<AdminModel> = PageImpl(listOf(buildAdmin(cpf = "547.616.951-29"), buildAdmin(cpf = "176.764.563-55")))

        every { adminRepository.findAll(pageable) } returns fakeAdmins

        val admins = adminService.findAll(pageable = pageable)

        assertEquals(fakeAdmins, admins)
        verify (exactly = 1){ adminRepository.findAll(pageable) }
    }

    @Test
    fun `should create admin and encrypt password`(){
        val initialPassword = Math.random().toString()
        val fakeAdmin = buildAdmin(cpf = "547.616.951-29", password = initialPassword)
        val fakePassword = UUID.randomUUID().toString()
        val fakeAdminEncrypted = fakeAdmin.copy(password = fakePassword)

        every { adminRepository.save(fakeAdminEncrypted) } returns fakeAdmin
        every { bCrypt.encode(initialPassword) } returns fakePassword

        adminService.create(fakeAdmin)

        verify (exactly = 1){ adminRepository.save(fakeAdminEncrypted) }
        verify (exactly = 1){ bCrypt.encode(initialPassword) }
    }

    @Test
    fun `should return admin by id`(){
        val id: Int = Random.nextInt()
        val fakeAdmin = buildAdmin(id, "064.687.863-80")

        every { adminRepository.findById(id) } returns Optional.of(fakeAdmin)

        val admin = adminService.findById(id)

        assertEquals(fakeAdmin,admin)
        verify (exactly = 1){ adminRepository.findById(id) }
    }

    @Test
    fun `should throw not found when find by id`(){
        val id: Int = Random.nextInt()

        every { adminRepository.findById(id) } returns Optional.empty()

        val error = assertThrows<NotFoundException> {
            adminService.findById(id)
        }

        assertEquals("Admin id [$id] not exists", error.message)
        assertEquals("B-501", error.errorCode)
        verify (exactly = 1){ adminRepository.findById(id) }
    }

    @Test
    fun`should update admin`(){
        val id: Int= Random.nextInt()
        val fakeAdmin = buildAdmin(id, "064,687,863-80")

        every { adminRepository.existsById(id) } returns true
        every { adminRepository.save(fakeAdmin) } returns fakeAdmin

        adminService.update(fakeAdmin)

        verify (exactly = 1){ adminRepository.existsById(id) }
        verify (exactly = 1){ adminRepository.save(fakeAdmin)  }
    }

    @Test
    fun`should throw not found when update admin`(){
        val id: Int= Random.nextInt()
        val fakeAdmin = buildAdmin(id, "064,687,863-80")

        every { adminRepository.existsById(id) } returns false
        every { adminRepository.save(fakeAdmin) } returns fakeAdmin

        val error = assertThrows<NotFoundException> {
            adminService.update(fakeAdmin)
        }

        assertEquals("Admin id [$id] not exists", error.message)
        assertEquals("B-501", error.errorCode)

        verify (exactly = 1){ adminRepository.existsById(id) }
        verify (exactly = 0){ adminRepository.save(any())  }
    }

    @Test
    fun `should delete admin`(){
        val id: Int = java.util.Random().nextInt()
        val fakeAdmin = buildAdmin(id,"064.687.863-80")

        every { adminService.findById(id) } returns fakeAdmin
        every {
            adminService.findAdminInactive(id)
            adminService.update(fakeAdmin)
        } just runs

        adminService.delete(id)
        verify (exactly = 1){ adminService.findById(id) }
        verify (exactly = 1){ adminService.findAdminInactive(id) }
        verify (exactly = 1){ adminService.update(fakeAdmin) }
    }

    @Test
    fun `should throw not found when delete admin`(){
        val id: Int = java.util.Random().nextInt()

        every { adminService.findById(id) } throws NotFoundException(Errors.B501.message.format(id), Errors.B501.code)


        val error = assertThrows<NotFoundException>{ adminService.delete(id) }

        assertEquals("Admin id [$id] not exists", error.message)
        assertEquals("B-501", error.errorCode)

        verify (exactly = 1){ adminService.findById(id) }
        verify (exactly = 0){ adminService.findAdminInactive(id) }
        verify (exactly = 0){ adminService.update(any()) }
    }

    fun buildAdmin(
        id: Int? = null,
        cpf: String,
        name: String =  RandomString(10).toString(),
        email:String = "${UUID.randomUUID()}@gmail.com",
        password:String = Random(6).toString()
    ) = AdminModel(
        id = id,
        cpf = cpf,
        name = name,
        email= email,
        password = password,
        status = StatusEnum.ACTIVE,
        roles = setOf(Role.ADMIN)
    )
}