package com.rossacheson.loans;

import com.rossacheson.loans.dto.LoansContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@EnableConfigurationProperties(LoansContactInfoDto.class)
@OpenAPIDefinition(
		info=@Info(
				title="Loans microservice REST API Documentation",
				description = "EazyBank Loans microservice REST API Documentation",
				version="v1",
				contact = @Contact(name = "Ross Acheson", url = "https://rossacheson.com"),
				license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")
		),
		externalDocs = @ExternalDocumentation(
				description = "EazyBank Documentation",
				url = "https://eazybytes.com/swagger-ui.html"
		)
)
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}
