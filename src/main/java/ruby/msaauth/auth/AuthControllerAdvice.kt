package ruby.msaauth.auth

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AuthControllerAdvice {
    @ExceptionHandler(AuthException::class)
    fun handleAuthException(ex: AuthException): ResponseEntity<Map<String, String>> {
        val errorResponse = mapOf("message" to ex.message.orEmpty())    // 예외 메시지를 반환
        return ResponseEntity(errorResponse, ex.status)
    }
}

open class AuthException(
    val status: HttpStatus,
    message: String
) : RuntimeException(message)
