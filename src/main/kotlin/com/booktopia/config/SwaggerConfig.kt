package com.booktopia.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMethod
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.builders.ResponseMessageBuilder
import springfox.documentation.service.ResponseMessage
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.booktopia.controllers"))
            .paths(PathSelectors.any())
            .build()
            .useDefaultResponseMessages(false)
            .globalResponseMessage(RequestMethod.GET, responseMessageForGET())

            .apiInfo(ApiInfoBuilder()
                .title("Booktopia")
                .description("Sistema gerenciador de aluguel de livros")
                .build())

    }

    private fun responseMessageForGET(): List<ResponseMessage?>? {
        return object : ArrayList<ResponseMessage?>() {
            init {
                add(
                    ResponseMessageBuilder()
                        .code(200)
                        .message("Success")
                        .build()
                )
                add(
                    ResponseMessageBuilder()
                        .code(400)
                        .message("Error: Invalid Parameter")
                        .build()
                )
                add(
                    ResponseMessageBuilder()
                        .code(404)
                        .message("Error: Entity Not Found")
                        .build()
                )
            }
        }
    }
}