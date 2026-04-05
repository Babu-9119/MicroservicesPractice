package com.letsbuild.accounts;

import com.letsbuild.accounts.dto.AccountsContactInfoDto;
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
/*@ComponentScans({ @ComponentScan("com.letsbuild.accounts.controller") })
@EnableJpaRepositories("com.letsbuild.accounts.repository")
@EntityScan("com.letsbuild.accounts.model")*/
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts Microsrvice Rest API Documentation",
				description = "Learning Microservices by usindig the bacnking app",
				version = "v1",
				contact = @Contact(
						name = "Babu Reddy",
						email = "babu@letslearn.com",
						url = "www.test.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.letsbuild.com"
				)

		),
		externalDocs = @ExternalDocumentation(
				description =  "Build Accounts microservice REST API Documentation",
				url = "https://www.letsbuild.com/swagger-ui.html"
		)
)
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
