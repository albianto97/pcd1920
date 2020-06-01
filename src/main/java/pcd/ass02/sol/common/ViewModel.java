package pcd.ass02.sol.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class ViewModel {

	private ConcurrentHashMap<String,ViewModelNode> nodes;
	private Random gen;
	private ConceptGraph concepts;
	private HashMap<String, ViewModelNode> visited;
	
	public ViewModel(ConceptGraph concepts) {
		gen = new Random();
		this.concepts = concepts;
		nodes = new ConcurrentHashMap<String,ViewModelNode>();
		visited = new HashMap<String, ViewModelNode>();
		new ViewModelUpdater(this).start();
	}
	
	public void reset() {
		nodes.clear();
	}
	
	public void update() {
		visited.clear();
		Optional<Concept> co = concepts.getRootConcept();
		if (co.isPresent()) {
			refresh(co.get(), concepts, 0, null);
		}
	}

	private void refresh(Concept concept, ConceptGraph concepts, int level, ViewModelNode parent) {
		try {
			String key = concept.getContent();
			ViewModelNode node = nodes.get(key);
			if (node == null) {
				P2d pos = null;
				if (parent != null) {
					P2d parentPos = parent.getPos();
					double angle = gen.nextDouble()*Math.PI*2;
					pos = new P2d(parentPos.x + Math.cos(angle)/(level + 1), parentPos.y + Math.sin(angle)/(level + 1));
				} else {
					pos = new P2d(0,0);
				}
				double weight = 100.0/(level + 1);
				node = new ViewModelNode(concept, pos, weight);
				nodes.put(key, node);
			}
			visited.put(key, node);
			
	        List<String> linked = concept.getLinkedConcepts();
		    for (String s: linked) {
		    	Optional<Concept> co = concepts.getConcept(s);
	    		if (co.isPresent()) {
	    			if (visited.get(s) == null) {
		    			refresh(co.get(), concepts,  level + 1, node);
					    ViewModelNode linkedNode = nodes.get(s);
					    node.addLinkedIfNotPresent(linkedNode);
		    		}			    
		    	}
	        }
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public int getSize() {
		return nodes.size();
	}
		
	public Collection<ViewModelNode> getNodes(){
		return nodes.values();
	}

	public Optional<ViewModelNode> getRootNode() {
		Optional<String> root = concepts.getRoot();
		if (root.isPresent()) {
			ViewModelNode n = nodes.get(root.get());
			return n == null ? Optional.empty() : Optional.of(n);
		} else {
			return Optional.empty();
		}
	}
	public ViewModelNode getNode(String key) {
		return nodes.get(key);
	}
}
