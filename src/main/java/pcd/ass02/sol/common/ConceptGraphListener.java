package pcd.ass02.sol.common;

public interface ConceptGraphListener {

	void notifyInit(String root, int maxLevels);
	
	void notifyConceptGraphChanged();
	
}
