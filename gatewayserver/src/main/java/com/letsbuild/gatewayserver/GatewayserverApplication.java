package com.letsbuild.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator letsBuildBankCustomRouteConfig(RouteLocatorBuilder routeLocatorBuilder){

		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/letsbuild/accounts/**")
						.filters(f -> f.rewritePath("/letsbuild/accounts/(?<segment>.*)","/${segment}")
								.circuitBreaker(config -> config.setName("accountsCircuitBreaker")
										.setFallbackUri("forward:/contactSupport"))
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/letsbuild/loans/**")
						.filters(f -> f.rewritePath("/letsbuild/loans/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://LOANS"))
				.route(p -> p
						.path("/letsbuild/cards/**")
						.filters(f -> f.rewritePath("/letsbuild/cards/(?<segment>.*)","/${segment}").addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://CARDS"))
				.build();
	}

}
