package pcd.lab12.spring.microservices.basic;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HelloMicroservice2 {
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") final String name) {
		return new Greeting(counter.incrementAndGet(), String.format("Hello, %s!", name));
	}
	
	public static void main(final String[] args) {
		/*final SpringApplication app = new SpringApplication(HelloMicroservice2.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        app.run(args);*/
		
		
		SpringApplication.run(HelloMicroservice2.class, args);
	}
}
