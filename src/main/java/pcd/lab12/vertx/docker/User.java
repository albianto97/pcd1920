package pcd.lab12.vertx.docker;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

public class User {

	private String id, description, phone;
	
	public User(final String id, final String description, final String phone) {
		this.id = id;
		this.description = description;
		this.phone = phone;
	}
	
	public User(JsonObject json) {
	    this.id = json.getString("id");
	    this.description = json.getString("description");
	    this.phone = json.getString("phone");
	  }

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String toString() {
		return Json.encodePrettily(this);
	}
	
	public JsonObject toJson() {
		return new JsonObject()
				.put("id", this.id)
				.put("description", this.description)
				.put("phone", this.phone);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if(obj instanceof User) {
			final User other = (User) obj;
			
			return this.id.equals(other.id) 
					&& this.description.equals(other.description) 
					&& this.phone.equals(other.phone);
		}
		
		return false;
	}
}
