package pcd.ass02.sol.common;

import java.util.List;

public class Concept {

	private String content;
	private int level;
	private List<String> linkedConcepts;
	
	public Concept(String content, List<String> linkedConcepts) {
		this.content = content;
		this.linkedConcepts = linkedConcepts;
	}
	
	public String getContent() {
		return content;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public List<String> getLinkedConcepts(){
		return linkedConcepts;
	}
	
	public String toString() {
		return content + " linked: " + linkedConcepts;
	}

}

