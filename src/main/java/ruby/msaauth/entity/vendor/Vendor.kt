package ruby.msaauth.entity.vendor

import jakarta.persistence.*

@Entity
@Table(name = "VENDOR")
class Vendor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val businessNumber: String,

    @OneToMany(mappedBy = "vendor", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val roles: List<VendorRole> = mutableListOf(),
)
