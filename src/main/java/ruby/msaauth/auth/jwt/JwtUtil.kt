package ruby.msaauth.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.util.*
import javax.security.auth.Subject

@Component
class JwtUtil(private val jwtProperties: JwtProperties) {

    /**
     * JWT 생성
     */
    fun generateAccessToken(subject: String): String {
        return jwtProperties.run {
            val now = Date()
            Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(Date(now.time + accessTokenExpirationMs))
                .signWith(Keys.hmacShaKeyFor(secretKey.toByteArray()), SignatureAlgorithm.HS256)
                .compact()
        }
    }

    /**
     * Refresh Token 생성
     */
    fun generateRefreshToken(subject: String): String {
        return jwtProperties.run {
            val now = Date()
            val expiration = Date(now.time + refreshTokenExpirationMs)
            Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(Keys.hmacShaKeyFor(secretKey.toByteArray()), SignatureAlgorithm.HS256)
                .compact()
        }
    }


    /**
     * JWT 검증
     */
    fun validateToken(token: String?): Boolean {
        if (token.isNullOrBlank()) return false

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
    fun extractSubject(token: String): String {
        val claims = Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray()))
            .build()
            .parseClaimsJws(token)
            .body
        return claims.subject
    }
}

@Component
@ConfigurationProperties(prefix = "jwt")
class JwtProperties {
    var secretKey: String = "secretkey"
    var accessTokenExpirationMs: Int = 15 * 60 * 1000 // 15분
    var refreshTokenExpirationMs: Int = 7 * 24 * 60 * 60 * 1000 // 7일
}
