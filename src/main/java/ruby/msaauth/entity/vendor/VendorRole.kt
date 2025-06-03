package ruby.msaauth.entity.vendor

import jakarta.persistence.*
import ruby.msaauth.enums.Role

@Entity
@Table(name = "VENDOR_ROLE")
class VendorRole(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Enumerated(EnumType.STRING)
    val role: Role,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    val vendor: Vendor,
)
