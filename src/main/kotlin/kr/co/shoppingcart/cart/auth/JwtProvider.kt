package kr.co.shoppingcart.cart.auth

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import kr.co.shoppingcart.cart.utils.DateUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey


@Component
class JwtProvider (
    @Value("\${jwt.secret}")
    private val secret: String,
    @Value("\${jwt.issuer}")
    private val issuer: String
) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))

    fun createJwt(jwtPayload: JwtPayload): String {
        val expiredTime = jwtPayload.now.plusSeconds(jwtPayload.expiredTimestamp)

        val jwtBuilder = Jwts.builder()

        if (jwtPayload.claims.isNullOrEmpty()) {
            return jwtBuilder
                .id(UUID.randomUUID().toString())
                .subject(jwtPayload.identificationValue)
                .issuer(issuer)
                .issuedAt(DateUtil.convertZoneDateTimeToDate(jwtPayload.now))
                .expiration(DateUtil.convertZoneDateTimeToDate(expiredTime))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact()
        }

        return jwtBuilder.claims(jwtPayload.claims)
            .id(UUID.randomUUID().toString())
            .subject(jwtPayload.identificationValue)
            .issuer(issuer)
            .issuedAt(DateUtil.convertZoneDateTimeToDate(jwtPayload.now))
            .expiration(DateUtil.convertZoneDateTimeToDate(expiredTime))
            .signWith(secretKey, Jwts.SIG.HS512)
            .compact()
    }

    fun verifyToken(jwt: String?): JwtPayload {
        val claimsJwt = Jwts.parser().verifyWith(secretKey).build()
            .parseSignedClaims(jwt)

        return JwtPayload(
            claimsJwt.payload,
            claimsJwt.payload.expiration.time,
            claimsJwt.payload.subject,
        )
    }
}