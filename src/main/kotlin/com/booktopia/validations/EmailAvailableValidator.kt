package com.booktopia.validations

import com.booktopia.services.ClientService
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class EmailAvailableValidator(private val clientService: ClientService): ConstraintValidator<EmailValidation, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value.isNullOrEmpty()){
            return false
        }
        return clientService.emailAvailable(value)
    }

}
