package ruby.msaauth.data.entity

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import ruby.msaauth.data.enums.Role

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

    @OneToMany(mappedBy = "userInfo", cascade = [CascadeType.ALL], orphanRemoval = true)
    val roles: MutableList<UserRole> = mutableListOf()
)

@Entity
@Table(name = "USER_ROLE")
class UserRole(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val role: Role,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_info_id")
    val userInfo: UserInfo
)


interface UserInfoRepository : JpaRepository<UserInfo, Long> {
    fun findByEmail(email: String): UserInfo?
}

interface UserRoleRepository : JpaRepository<UserRole, Long>
