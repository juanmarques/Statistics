package com.n26.main.configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {

		final List<ResponseMessage> responseMessageList = new ArrayList<ResponseMessage>();
		responseMessageList.add(new ResponseMessageBuilder().code(201).message("Success").build());
		responseMessageList
				.add(new ResponseMessageBuilder().code(204).message("Transaction is older than 60 seconds").build());

		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.n26.main.controller")).paths(PathSelectors.any()).build()
				.apiInfo(apiInfo()).tags(new Tag("N26", "Statistics")).useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.POST, responseMessageList);
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("N26 Stastistics RESTful API",
				"Restful API for statistics. The main use case for our API is to\r\n"
						+ "calculate realtime statistic from the last 60 seconds.",
				"1.0", "Terms of service",
				new Contact("Juan Marques", "https://github.com/juanmarques/Statistics", "1juanmarques@gmail.com"), "",
				"", Collections.emptyList());
	}

}