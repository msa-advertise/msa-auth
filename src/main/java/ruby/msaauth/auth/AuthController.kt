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
    fun login(@RequestBody request: LoginRequest) : TokenResponse {
        return authService.login(request)
    }

    /**
     * Refresh Token 으로 Access Token 재발급
     */
    @GetMapping("/refresh-token")
    fun refreshToken(
        @CookieValue("refreshToken") refreshToken: String?, // 쿠키에서 리프레시 토큰 가져옴
    ): AccessTokenResponse {
        return authService.refreshToken(refreshToken)
    }
}

data class LoginRequest(val email: String, val password: String)
