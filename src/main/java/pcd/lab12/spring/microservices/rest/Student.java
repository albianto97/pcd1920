package pcd.lab12.spring.microservices.rest;

public class Student {

	private String name;
	private int age;
	private String matriculationNumber;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(final int age) {
		this.age = age;
	}

	public String getMatriculationNumber() {
		return matriculationNumber;
	}

	public void setMatriculationNumber(final String matriculationNumber) {
		this.matriculationNumber = matriculationNumber;
	}
}
