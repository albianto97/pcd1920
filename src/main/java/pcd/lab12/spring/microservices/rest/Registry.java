package pcd.lab12.spring.microservices.rest;

import java.util.ArrayList;
import java.util.List;

public class Registry {	
	private static Registry instance = new Registry();
	
	public static Registry instance() {
		return instance;
	}
	
	private List<Student> records;
	
	private Registry(){
		records = new ArrayList<Student>();
	}
	
	public List<Student> getAllStudents() {
		return records;
	}
	
	public Student getStudentByMatriculationNumber(final String matriculationNumber) {
		return records.stream().filter(s -> s.getMatriculationNumber().equals(matriculationNumber)).findFirst().orElse(null);
	}
	
	public void addStudent(final Student std) {
		records.add(std);
	}
	
	public String updateStudent(final Student std) {
		for(final Student s : records) {
			if(s.getMatriculationNumber().equals(std.getMatriculationNumber())) {
				records.set(records.indexOf(s), std);
				return "Update done";
			}
		}
		
		return "Student not found!";
	}
	
	public String deleteStudent(final String matriculationNumber) {
		for(final Student s : records) {
			if(s.getMatriculationNumber().equals(matriculationNumber)) {
            	records.remove(s);
            	return "Delete successful";
            }
		}
		
		return "Student not found!";
	}
}
