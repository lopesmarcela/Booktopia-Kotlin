package com.booktopia.security

import com.booktopia.controllers.response.ErrorResponse
import com.booktopia.enums.Errors
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationEntryPoint: AuthenticationEntryPoint{
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val errorResponse = ErrorResponse(HttpStatus.UNAUTHORIZED.value(), Errors.B000.message,Errors.B000.code, null)
        response.outputStream.print(jacksonObjectMapper().writeValueAsString(errorResponse))
    }
}