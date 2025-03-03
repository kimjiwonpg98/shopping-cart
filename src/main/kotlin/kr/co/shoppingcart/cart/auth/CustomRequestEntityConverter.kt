package kr.co.shoppingcart.cart.auth

// import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
// import org.bouncycastle.openssl.PEMParser
// import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import io.jsonwebtoken.Jwts
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.core.convert.converter.Converter
import org.springframework.core.io.ClassPathResource
import org.springframework.http.RequestEntity
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter
import org.springframework.util.MultiValueMap
import java.io.File
import java.io.IOException
import java.util.Date

class CustomRequestEntityConverter : Converter<OAuth2AuthorizationCodeGrantRequest?, RequestEntity<*>?> {
    private val defaultConverter =
        OAuth2AuthorizationCodeGrantRequestEntityConverter()

    override fun convert(req: OAuth2AuthorizationCodeGrantRequest): RequestEntity<*> {
        val entity = defaultConverter.convert(req)
        val registrationId = req.clientRegistration.registrationId
        val params = entity!!.body as MultiValueMap<String, String>?
        // Apple일 경우 secret key를 동적으로 세팅
        if (registrationId.contains("apple")) {
            params!!["client_secret"] =
                createAppleClientSecret(
                    params["client_id"]!![0],
                    params["client_secret"]!![0],
                )
        }
        return RequestEntity(
            params,
            entity.headers,
            entity.method,
            entity.url,
        )
    }

    // Apple Secret Key를 만드는 메서드
    fun createAppleClientSecret(
        clientId: String,
        secretKeyResource: String,
    ): String {
        var clientSecret = ""
        // application-oauth.yml에 설정해놓은 apple secret Key를 /를 기준으로 split
        val secretKeyResourceArr = secretKeyResource.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        try {
//            val tempFile = File.createTempFile("appleKeyFile", ".p8")
            val inputStream = ClassPathResource("{.p8 파일 경로}" + secretKeyResourceArr[0]).inputStream
            val file: File = File.createTempFile("appleKeyFile", ".p8")
            try {
                inputStream.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            } finally {
                IOUtils.closeQuietly(inputStream)
            }

            val appleKeyId = secretKeyResourceArr[1]
            val appleTeamId = secretKeyResourceArr[2]
            val appleClientId = clientId

//            val pemParser = PEMParser(FileReader(file))
//            val converter = JcaPEMKeyConverter()
//            val privateKeyInfo: PrivateKeyInfo = pemParser.readObject() as PrivateKeyInfo

//            val privateKey: PrivateKey = converter.getPrivateKey(privateKeyInfo)

            clientSecret =
                Jwts
                    .builder()
                    .subject(appleClientId)
                    .issuer(appleTeamId)
                    .issuedAt(Date(System.currentTimeMillis()))
                    .claims(mapOf("kid" to appleKeyId))
                    .expiration(Date(System.currentTimeMillis() + (1000 * 60 * 5)))
//                    .signWith(privateKey, Jwts.SIG.ES256)
                    .compact()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return clientSecret
    }
}
