package ruby.msaauth.entity.userInfo

import jakarta.persistence.*
import ruby.msaauth.entity.vendor.Vendor

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

    @ManyToOne(fetch = FetchType.LAZY)
    val vendor: Vendor? = null,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val roles: List<UserRole> = mutableListOf(),
)
