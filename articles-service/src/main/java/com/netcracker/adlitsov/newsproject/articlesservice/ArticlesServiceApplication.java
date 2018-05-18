package com.netcracker.adlitsov.newsproject.articlesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@EnableDiscoveryClient
@SpringBootApplication
@EnableJpaAuditing
public class ArticlesServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ArticlesServiceApplication.class, args);
	}
}
