package pcd.ass02.sol.es01;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.logging.Logger;

import pcd.ass02.sol.common.*;

public class BuildAndRefreshConceptNodeTask extends RecursiveTask<Void> {

	private String concept;
	private ConceptGraph conceptGraph;
	private int level;
	private Wikipedia wiki;
	private Flag stopFlag;
	private HashMap<String, Integer> visited;
	private static final Logger logger = Logger.getLogger("tasks");

	public BuildAndRefreshConceptNodeTask(HashMap<String, Integer> visited, String concept, Wikipedia wiki, ConceptGraph graph, int level, Flag stopFlag) {
		this.visited = visited;
		this.concept = concept;
		this.conceptGraph = graph;
		this.level = level;
		this.wiki = wiki;
		this.stopFlag = stopFlag;
	}

	public Void compute() {
		if (stopFlag.isSet()) {
			return null;
		}
		
		/* 
		 * Explore the concept if concept has not already been explored or 
		 * already explored but at different (greater) level from the root 
		 */
		
		boolean toBeExplored = false;
		synchronized(visited) {
			Integer prev = visited.get(concept);
			if (prev == null ||  (prev > level)) {		
				toBeExplored = true;
				visited.put(concept, level);
			}
		}

		if (toBeExplored) {
				Optional<Concept> con = wiki.getConcept(concept);				
				if (con.isPresent()) {
					boolean added = conceptGraph.addConceptIfNotAlreadyPresent(con.get());
					if (added) {
						logger.info("new concept added: " + concept + " - " + con.get().getLinkedConcepts().size());
					} else if (!added) {
						logger.info("concept already present: " + concept);
					}
				
				/* fork tasks about linked concepts */
					
				if (level < conceptGraph.getMaxLevel()) {
					List<BuildAndRefreshConceptNodeTask> tasks = new LinkedList<BuildAndRefreshConceptNodeTask>();
					for (String linkedConcept : con.get().getLinkedConcepts()) {
						if (stopFlag.isSet()) {
							return null;
						}
						BuildAndRefreshConceptNodeTask task = new BuildAndRefreshConceptNodeTask(visited, linkedConcept, wiki, conceptGraph, level + 1,
								stopFlag);
						task.fork();
						tasks.add(task);
					}
					for (BuildAndRefreshConceptNodeTask t : tasks) {
						t.join();
					}
				} else {
					logger.info("reached max level " + concept);
				}
				return null;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
