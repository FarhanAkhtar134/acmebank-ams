package com.acmebank.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ResourceNotFoundException(message: String) : ResponseStatusException(HttpStatus.NOT_FOUND, message) {
}