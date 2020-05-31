package pcd.lab12.spring.microservices.rest;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class App {
	public static void main(final String[] args) {
		final SpringApplication app = new SpringApplication(App.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        app.run(args);
	}
	
	@GetMapping("/students")
	@ResponseBody
	public List<Student> getStudent() {
		return Registry.instance().getAllStudents();
	}
	
	@GetMapping("/students/{matriculationNumber}")
	@ResponseBody
	public Student getStudentByMatriculationNumber(@PathVariable("matriculationNumber") final String matriculationNumber) {
		return Registry.instance().getStudentByMatriculationNumber(matriculationNumber);
	}
	
	@PostMapping("/students")
	@ResponseBody
	public StudentRegistrationReply registerStudent(@RequestBody Student student) {
		Registry.instance().addStudent(student);

		return new StudentRegistrationReply.Builder()
				.matriculationNumber(student.getMatriculationNumber())
				.registrationStatus("Successful")
				.build();
	}
	
	@PutMapping("/students")
	@ResponseBody
	public String updateStudentRecord(@RequestBody Student stdn) {	   
	    return Registry.instance().updateStudent(stdn);		
	}	
	
	@DeleteMapping("/students/{regNum}")
	@ResponseBody
	public String deleteStudentRecord(@PathVariable("regNum") String regdNum) {		   
	    return Registry.instance().deleteStudent(regdNum);		
	}
}
