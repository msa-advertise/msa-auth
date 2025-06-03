package ruby.msaauth

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController {

    /**
     * 사용자 로그인
     * - 로그인 성공시 JWT 발급
     */
    @PostMapping("/login")
    fun login() {}

}
