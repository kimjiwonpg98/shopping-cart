package kr.co.shoppingcart.cart.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import kr.co.shoppingcart.cart.utils.DateUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime
import java.util.UUID
import javax.crypto.SecretKey

@DisplayName("jwt 생성 및 검증 테스트")
class JwtProviderTest {
    private lateinit var jwtProvider: JwtProvider
    private val secret: String =
        @Suppress("ktlint:standard:max-line-length")
        "secre123sdf23fdvberhrfwefewfwefwfwfewfdcsdwf2ttesttesttestsetsetstsetsdfsdfsdfdsfsdfdsfsdfsfsetstetsetet"
    private val issuer: String = "test"

    @BeforeEach
    fun setUp() {
        jwtProvider =
            JwtProvider(
                secret,
                issuer,
            )
    }

    @Test
    @DisplayName("jwt 생성 테스트")
    fun `jwt 생성에 성공`() {
        // given
        val secretKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
        val claims: Map<String, Any> = mapOf("email" to email)
        val jwtPayload =
            createJwtPayload(
                claims = claims,
                expiredTimestamp = 86400,
                issuedAt = ZonedDateTime.now(),
            )
        val expiration = jwtPayload.now.plusSeconds(jwtPayload.expiredTimestamp)

        // when
        val jwt: String = jwtProvider.createJwt(jwtPayload)

        // then
        val claimsJwt =
            Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)

        assertEquals(claimsJwt.payload.issuer, issuer)
        assertEquals(claimsJwt.payload["email"], email)
        assertEquals(
            claimsJwt.payload.expiration.toString(),
            DateUtil.convertZoneDateTimeToDate(expiration).toString(),
        )
        assertNotNull(claimsJwt.payload.id)
    }

    private fun createJwtPayload(
        claims: Map<String, Any>,
        expiredTimestamp: Long,
        issuedAt: ZonedDateTime,
    ): JwtPayloadDto =
        JwtPayloadDto(
            claims = claims,
            expiredTimestamp = expiredTimestamp,
            identificationValue = UUID.randomUUID().toString(),
            now = issuedAt,
        )

    companion object {
        private val email = "test@test.com"
    }
}
