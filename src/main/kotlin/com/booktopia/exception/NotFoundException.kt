package com.booktopia.exception

class NotFoundException(override val message: String, val errorCode: String): Exception() {
}