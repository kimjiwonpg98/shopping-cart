package kr.co.shoppingcart.cart.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
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

    fun createJwt(jwtPayloadDto: JwtPayloadDto): String {
        val expiredTime = jwtPayloadDto.now.plusSeconds(jwtPayloadDto.expiredTimestamp)

        val jwtBuilder = Jwts.builder()

        if (jwtPayloadDto.claims.isNullOrEmpty()) {
            return jwtBuilder
                .id(UUID.randomUUID().toString())
                .subject(jwtPayloadDto.identificationValue)
                .issuer(issuer)
                .issuedAt(DateUtil.convertZoneDateTimeToDate(jwtPayloadDto.now))
                .expiration(DateUtil.convertZoneDateTimeToDate(expiredTime))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact()
        }

        return jwtBuilder.claims(jwtPayloadDto.claims)
            .id(UUID.randomUUID().toString())
            .subject(jwtPayloadDto.identificationValue)
            .issuer(issuer)
            .issuedAt(DateUtil.convertZoneDateTimeToDate(jwtPayloadDto.now))
            .expiration(DateUtil.convertZoneDateTimeToDate(expiredTime))
            .signWith(secretKey, Jwts.SIG.HS512)
            .compact()
    }

    fun verifyToken(jwt: String?): JwtPayload {
        val claimsJwt = Jwts.parser().verifyWith(secretKey).build()
            .parseSignedClaims(jwt)

        if (claimsJwt.payload["email"].toString().isEmpty()) {
            return JwtPayload(
                identificationValue = claimsJwt.payload.subject
            )
        }

        return JwtPayload(
            identificationValue = claimsJwt.payload.subject,
            email = claimsJwt.payload["email"] as String
        )
    }
}