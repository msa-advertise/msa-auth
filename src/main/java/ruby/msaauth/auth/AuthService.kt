package ruby.msaauth.auth

import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ruby.msaauth.data.entity.UserInfoRepository
import ruby.msaauth.jwt.JwtUtil

@Service
@Transactional(readOnly = true)
class AuthService(
    private val userInfoRepository: UserInfoRepository,
    private val jwtUtil: JwtUtil,
    private val passwordEncoder: PasswordEncoder,
) {

    fun login(request: LoginRequest) : TokenResponse {
        val userInfo = userInfoRepository.findByEmail(request.email) ?: throw LoginFailException()
        val matches = passwordEncoder.matches(request.password, userInfo.password)
        if (!matches) throw LoginFailException()

        return userInfo.run {
            TokenResponse(
                accessToken = jwtUtil.generateAccessToken(email, roles.map { it.role }),
                refreshToken = jwtUtil.generateRefreshToken(email)
            )
        }
    }

    fun refreshToken(refreshToken: String) : AccessTokenResponse {
        // Refresh Token 검증
        if (!jwtUtil.validateToken(refreshToken)) {
            throw RefreshTokenFailException()
        }

        val email = jwtUtil.extractSubject(refreshToken)
        return userInfoRepository.findByEmail(email)
            ?.run {
                AccessTokenResponse(
                    jwtUtil.generateAccessToken(email, roles.map { it.role })
                )
            }
            ?: throw LoginFailException()
    }
}


data class TokenResponse(val accessToken: String, val refreshToken: String)
data class AccessTokenResponse(val accessToken: String)


class LoginFailException(
    status: HttpStatus = HttpStatus.UNAUTHORIZED,
    message: String = "로그인에 실패하였습니다."
) : AuthException(status, message)

class RefreshTokenFailException(
    status: HttpStatus = HttpStatus.UNAUTHORIZED,
    message: String = "Refresh Token 이 더 이상 유효하지 않습니다."
) : AuthException(status, message)
