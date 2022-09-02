package com.gvendas.gestaovendas.controlador.util;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    public Docket configuracao(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.vendas"))
                .build()
                .apiInfo(informacaoAPI());
    }

    private ApiInfo informacaoAPI() {
        return new ApiInfoBuilder()
                .title("Gestão de vendas")
                .description("Sistema de gestão de vendas")
                .version("1.0.0")
                .build();
    }
}