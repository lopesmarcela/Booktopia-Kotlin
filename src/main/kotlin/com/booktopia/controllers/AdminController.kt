package com.booktopia.controllers

import com.booktopia.controllers.request.PostAdminRequest
import com.booktopia.controllers.request.PutAdminRequest
import com.booktopia.controllers.response.AdminResponse
import com.booktopia.extensions.toAdminModel
import com.booktopia.extensions.toResponse
import com.booktopia.services.AdminService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api(value = "", tags = ["Admin"])
@RequestMapping("admin")
class AdminController(
    val adminService: AdminService
) {

    @PostMapping
    @ApiOperation(value = "Registers a admin")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid admin: PostAdminRequest) {
        adminService.create(admin.toAdminModel())
    }

    @GetMapping
    @ApiOperation(value = "Returns a list of admins")
    fun findAll( @PageableDefault(page = 0, size = 5) pageable: Pageable): Page<AdminResponse> =
        adminService.findAll(pageable).map { it.toResponse() }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns a admin by id")
    fun findById(@PathVariable id: Int): AdminResponse =
        adminService.findById(id).toResponse()

    @PutMapping("/{id}")
    @ApiOperation(value = "Changes a admin")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody @Valid admin: PutAdminRequest) {
        val adminSaved = adminService.findById(id)
        adminService.update(admin.toAdminModel(adminSaved))
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Disables a admin")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        adminService.delete(id)
    }
}