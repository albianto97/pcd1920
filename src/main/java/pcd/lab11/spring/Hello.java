package pcd.lab11.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class Hello {

    @GetMapping
    public String helloService() {
        return "Hello!";
    }

}