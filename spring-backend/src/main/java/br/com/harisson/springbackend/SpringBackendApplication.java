package br.com.harisson.springbackend;

import br.com.harisson.core.property.JwtConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({"br.com.harisson.core.model"})
@EnableJpaRepositories({"br.com.harisson.core.repository"})
@EnableConfigurationProperties(value = JwtConfiguration.class)
@ComponentScan("br.com.harisson")
public class SpringBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBackendApplication.class, args);
	}

}
