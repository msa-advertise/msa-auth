package ruby.msaauth.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil(private val jwtProperties: JwtProperties) {

    /**
     * JWT 생성
     */
    fun generateToken(email: String): String {
        return jwtProperties.run {
            Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date())
                .setExpiration(Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .signWith(Keys.hmacShaKeyFor(secretKey.toByteArray()), SignatureAlgorithm.HS256)
                .compact()
        }
    }

    /**
     * JWT 검증
     */
    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray()))
                .build()
                .parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * JWT 로부터 사용자 이름 확인
     */
    fun extractUsername(token: String): String {
        val claims = Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray()))
            .build()
            .parseClaimsJws(token)
            .body
        return claims["name"].toString()
    }
}

@Component
@ConfigurationProperties(prefix = "jwt")
class JwtProperties {
    var secretKey: String = "secretkey"
    var accessTokenExpirationMs: Int = 15 * 60 * 1000 // 15분
    var refreshTokenExpirationMs: Int = 7 * 24 * 60 * 60 * 1000 // 7일
}
