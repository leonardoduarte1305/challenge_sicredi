package br.dev.leoduarte.sicredi.configuration.springfox;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@EnableOpenApi
@Configuration
public class SpringFoxConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2) //
				.apiInfo(getApiInfo()) //
				.enable(true) //
				.select() //
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)) //
				.paths(PathSelectors.any()) //
				.build();
	}

	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder() //
				.title("DevBack-Sicredi - Leo Duarte") //
				.description("Insere e consulta Associados, Pautas e Votos.") //
				.contact(new Contact("Leonardo Duarte", //
						"", //
						"leonardoduarte1305@gmail.com")) //
				.version("Spring 2.6.4") //
				.build();
	}

}
