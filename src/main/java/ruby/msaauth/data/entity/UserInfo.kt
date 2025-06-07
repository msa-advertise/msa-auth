package ruby.msaauth.data.entity

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 인증 서버에서는 사용자 인증을 할 정보만 있으면 된다.
 * 인증 서버에서는 검증을 위해 사용자 정보를 조회만 한다.
 */
@Entity
@Table(name = "USER_INFO")
class UserInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val name: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false, unique = true)
    val email: String,
)

interface UserInfoRepository : JpaRepository<UserInfo, Long> {
    fun findByEmail(email: String): UserInfo?
}

