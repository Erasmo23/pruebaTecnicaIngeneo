package co.com.ingeneo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({ "co.com.ingeneo.api.*" })
@EnableJpaRepositories(basePackages = { "co.com.ingeneo.api.repository" })
@EnableJpaAuditing
public class IngeneoLogisticaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngeneoLogisticaApiApplication.class, args);
	}

}
