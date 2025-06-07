package ruby.msaauth

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import ruby.msaauth.data.entity.UserInfo
import ruby.msaauth.data.entity.UserInfoRepository

@Profile("local")
@Component
class DataInitializer(
    private val userInfoRepository: UserInfoRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    @PostConstruct
    fun initData() {
        userInfoRepository.deleteAll()

        // UserInfo 임시 데이터 생성
        val ruby = UserInfo(
            name = "ruby",
            email = "ruby@gmail.com",
            password = passwordEncoder.encode("1234")
        )
        userInfoRepository.save(ruby)

        println("==== @PostConstruct 데이터 초기화 완료 ====")
    }
}
