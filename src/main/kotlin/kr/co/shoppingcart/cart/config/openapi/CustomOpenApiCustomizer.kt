package kr.co.shoppingcart.cart.config.openapi

import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.examples.Example
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.responses.ApiResponse
import kr.co.shoppingcart.cart.common.error.annotations.OpenApiSpecApiException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.common.error.translators.ExceptionCodeTranslator
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.method.HandlerMethod

class CustomOpenApiCustomizer (
    private val translator: ExceptionCodeTranslator
): OperationCustomizer {
    override fun customize(operation: Operation, handlerMethod: HandlerMethod): Operation {
        val hasMethodAnnotation = handlerMethod.hasMethodAnnotation(OpenApiSpecApiException::class.java)

        if (!hasMethodAnnotation) return operation

        val openApiSpecApiException = handlerMethod.getMethodAnnotation(OpenApiSpecApiException::class.java)!!
        val exampleGroups = openApiSpecApiException
            .values
            .toSet()
            .map(::exampleHolder)
            .groupBy(ExampleHolder::statusCode)

        val responses = operation.responses
        exampleGroups.forEach { (code, exampleHolders) ->
            val content = Content()
            val mediaType = MediaType()
            val apiResponse = ApiResponse()
            exampleHolders.forEach { exampleHolder ->
                mediaType.addExamples(exampleHolder.name, exampleHolder.example)
            }
            content.addMediaType(APPLICATION_JSON_VALUE, mediaType)
            apiResponse.content = content
            responses.addApiResponse(code.toString(), apiResponse)
        }
        return operation
    }


    private fun exampleHolder(exceptionCode: ExceptionCode): ExampleHolder {
        val apiExceptionResponseBody = translator.translate(exceptionCode)
        return ExampleHolder(
            example(apiExceptionResponseBody.message, apiExceptionResponseBody),
            exceptionCode.name,
            exceptionCode.code,
            exceptionCode.httpStatus.value()
        )

    }

    private fun <T> example(description: String, value: T): Example {
        val example = Example()
        example.description = description
        example.value = value
        return example
    }

}