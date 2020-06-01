package pcd.ass02.sol.common;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ConceptGraph {
	private ConcurrentHashMap<String, Concept> concepts;
	private String root;
	private int maxLevels;
	private List<ConceptGraphListener> listeners;
	
    public ConceptGraph() {
    	concepts = new ConcurrentHashMap<String, Concept>();
    	listeners = new LinkedList<ConceptGraphListener>();
    }

    public void init(String root, int maxLevels) {
    	this.root = root;
    	this.maxLevels = maxLevels;
    	concepts.clear();
		for (ConceptGraphListener l: listeners) {
			l.notifyInit(root, maxLevels);
		}
    }
    
    public Optional<String> getRoot() {
    	return root == null ? Optional.empty() : Optional.of(root);
    }
    
    public int getMaxLevel() {
    	return maxLevels;
    }
    
    public Collection<Concept> getConceptsList() {
    	return concepts.values();
    }
    public Optional<Concept> getConcept(String concept) {
    	Concept c = concepts.get(concept);
    	return c == null ? Optional.empty() : Optional.of(c);
    }

    public Optional<Concept> getRootConcept() {
    	return root == null ? Optional.empty() : getConcept(root);
    }

    public boolean addConceptIfNotAlreadyPresent(Concept co) {
    	Concept prev = concepts.putIfAbsent(co.getContent(), co);
    	if (prev == null) {
    		for (ConceptGraphListener l: listeners) {
    			l.notifyConceptGraphChanged();
    		}
    	}
    	return prev == null;
    }
    
    public int getSize() {
    	return concepts.size();
    }

    public void addListener(ConceptGraphListener l) {
    	listeners.add(l);
    }
}
