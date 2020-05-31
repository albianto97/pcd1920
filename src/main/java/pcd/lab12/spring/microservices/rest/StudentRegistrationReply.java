package pcd.lab12.spring.microservices.rest;

public class StudentRegistrationReply {

	private String matriculationNumber;
	private String registrationStatus;

	public String getMatriculationNumber() {
		return matriculationNumber;
	}

	public void setMatriculationNumber(final String matriculationNumber) {
		this.matriculationNumber = matriculationNumber;
	}

	public String getRegistrationStatus() {
		return registrationStatus;
	}

	public void setRegistrationStatus(final String registrationStatus) {
		this.registrationStatus = registrationStatus;
	}

	/**
	 * Builder Class
	 */
	public static class Builder {
		private StudentRegistrationReply reply = new StudentRegistrationReply();

		public Builder matriculationNumber(final String matriculationNumber) {
			reply.matriculationNumber = matriculationNumber;
			return this;
		}

		public Builder registrationStatus(final String registrationStatus) {
			reply.registrationStatus = registrationStatus;
			return this;
		}

		public StudentRegistrationReply build() {
			return this.reply;
		}

	}
}
