package ruby.msaauth.auth

import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ruby.msaauth.data.entity.UserInfoRepository
import ruby.msaauth.jwt.JwtUtil

@Service
class AuthService(
    private val userInfoRepository: UserInfoRepository,
    private val jwtUtil: JwtUtil,
    private val passwordEncoder: PasswordEncoder,
) {

    fun login(request: LoginRequest) : String {
        val userInfo = userInfoRepository.findByEmail(request.email) ?: throw LoginFailException()
        val matches = passwordEncoder.matches(request.password, userInfo.password)
        if (!matches) throw LoginFailException()

        return jwtUtil.generateToken(request.email)
    }
}

class LoginFailException(
    status: HttpStatus = HttpStatus.UNAUTHORIZED,
    message: String = "로그인에 실패하였습니다."
) : AuthException(status, message)

