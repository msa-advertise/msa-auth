import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.Date

object JwtUtil {
    private const val SECRET_KEY = "mysecretkeymysecretkeymysecretkey"  // 실제로는 .env 등에서 관리하세요.
    private const val EXPIRATION_TIME: Long = 3600000                   // 1시간 (밀리초)

    /**
     * JWT 생성
     */
    fun generateToken(username: String): String {
        val claims = mapOf("username" to username)
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(Keys.hmacShaKeyFor(SECRET_KEY.toByteArray()), SignatureAlgorithm.HS256)
            .compact()
    }

    /**
     * JWT 검증
     */
    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.toByteArray()))
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
            .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.toByteArray()))
            .build()
            .parseClaimsJws(token)
            .body
        return claims["username"].toString()
    }
}
