package ruby.msaauth.entity.userInfo

import jakarta.persistence.*
import ruby.msaauth.entity.vendor.VendorRole

@Entity
@Table(name = "USER_ROLE")
class UserRole(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: UserInfo,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_role_id")
    val vendorRole: VendorRole
)
