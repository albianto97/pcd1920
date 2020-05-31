package pcd.lab12.spring.microservices.basic;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HelloMicroservice {
	
	@GetMapping("/info")
	public String info() {
		return "I'm a Spring Boot based microservice";
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") final String name) {
		return String.format("Hello, %s!", name);
	}
	
	public static void main(final String[] args) {
		final SpringApplication app = new SpringApplication(HelloMicroservice.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        app.run(args);
	}
}
