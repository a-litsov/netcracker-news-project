package com.netcracker.adlitsov.newsproject.articlesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class ArticlesServiceApplication {

	@GetMapping(value = "/news/{id}", produces="application/json")
	News getNewsById(@PathVariable("id") int id) {
		return new News(id, "News #" + id);
	}

	public static void main(String[] args) {
		SpringApplication.run(ArticlesServiceApplication.class, args);
	}
}
