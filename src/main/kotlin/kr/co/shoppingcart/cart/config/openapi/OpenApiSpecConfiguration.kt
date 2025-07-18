package kr.co.shoppingcart.cart.config.openapi

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import kr.co.shoppingcart.cart.common.error.translators.ExceptionCodeTranslator
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiSpecConfiguration {
    @Bean
    fun openApi(): OpenAPI =
        OpenAPI()
            .apply { addSecurityItem(SecurityRequirement().addList("bearerAuth")) }
            .apply { components = authSetting() }
            .apply { info = swaggerInfo() }
            .apply { addServersItem(Server().url("https://kka-dam.shop/").description("개발 서버")) }
            .apply { addServersItem(Server().url("http://localhost:8080/").description("로컬 서버")) }

    @Bean
    fun operationCustomizer(translator: ExceptionCodeTranslator): OperationCustomizer =
        CustomOpenApiCustomizer(translator)

    private fun authSetting(): Components =
        Components()
            .addSecuritySchemes(
                "bearerAuth",
                SecurityScheme()
                    .name("Authorization")
                    .type(SecurityScheme.Type.HTTP)
                    .`in`(SecurityScheme.In.HEADER)
                    .scheme("bearer")
                    .bearerFormat("JWT"),
            )

    private fun swaggerInfo(): Info {
        val license = License().apply { name = "basket" }
        return Info()
            .version("v0.0.1")
            .title("스웨거 문서")
            .description("임시 토큰 발급받아 진행하면 됩니다.")
            .license(license)
    }
}
