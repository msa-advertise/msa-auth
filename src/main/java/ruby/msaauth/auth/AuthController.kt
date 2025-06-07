package ruby.msaauth.auth

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
) {

    /**
     * 사용자 로그인
     * - 로그인 성공시 JWT 발급
     */
    @PostMapping("/login")
    @Throws(RuntimeException::class)
    fun login(@RequestBody request: LoginRequest) : String {
        return authService.login(request)
    }
}

data class LoginRequest(val email: String, val password: String)
