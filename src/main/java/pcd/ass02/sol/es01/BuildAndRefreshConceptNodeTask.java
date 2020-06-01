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
		
		/* check if the concept has been already visited */
		
		boolean toBeFetched = false;
		boolean toBeExplored = false;
		synchronized(visited) {
			Integer prev = visited.get(concept);
			if (prev == null) {			/* concept not already present */
				toBeFetched = true;
				toBeExplored = true;
				visited.put(concept, level);
			} else if (prev > level) {	/* concept present, but need to to be explored again */ 
				visited.put(concept, level);
				toBeExplored = true;
			}
		}

		if (toBeExplored) {
				Concept co = null;
				if (toBeFetched) {
					Optional<Concept> con = wiki.getConcept(concept);
					co = con.get();
				} else {
					Optional<Concept> con = conceptGraph.getConcept(concept);
					co = con.get();
				}

				/* fetch the concept from wiki if needed */
				if (co != null) {
					boolean added = conceptGraph.addConceptIfNotAlreadyPresent(co);
					if (added) {
						logger.info("new concept added: " + concept + " - " + co.getLinkedConcepts().size());
					} else if (!added) {
						logger.info("concept already present: " + concept);
					}
				
				/* fork tasks about linked concepts */
					
				if (level < conceptGraph.getMaxLevel()) {
					List<BuildAndRefreshConceptNodeTask> tasks = new LinkedList<BuildAndRefreshConceptNodeTask>();
					for (String linkedConcept : co.getLinkedConcepts()) {
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
