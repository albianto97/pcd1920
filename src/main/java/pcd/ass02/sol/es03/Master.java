package pcd.ass02.sol.es03;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import pcd.ass02.sol.common.Concept;
import pcd.ass02.sol.common.ConceptGraph;
import pcd.ass02.sol.common.View;

public class Master extends Thread {

	private String rootConcept;
	private ConceptGraph conceptGraph;
	private Wikipedia wiki;
	private int maxLevel;
	private HashMap<String, Integer> visited;
	private View view;
	private volatile boolean stopped;
	private static final long PERIOD = 100000; /* 10 secs */ 
	private static final Logger logger = Logger.getLogger("[master]");
	
	public Master(String rootConcept, int maxLevel, Wikipedia wiki, ConceptGraph graph, View view) {
		this.rootConcept = rootConcept;
		this.maxLevel = maxLevel;
		this.wiki = wiki;
		this.conceptGraph = graph;
		this.view = view;
		this.stopped = false;
		visited = new HashMap<String, Integer>();
	}

	public void run() {
		logger.info("started");
		conceptGraph.init(rootConcept, maxLevel);
		Flowable
		.interval(0, PERIOD, TimeUnit.MILLISECONDS)
		.takeWhile( val -> !stopped)
		.subscribe(time -> {
			visited.clear();
			view.setRunning();
			buildConceptFlow(rootConcept, 0)
			.subscribe(co -> {
				conceptGraph.addConceptIfNotAlreadyPresent(co);
			}, t -> {
				view.setIdle();
				logger.info("error: " + t);			
			}, () -> {
				if (!stopped) {
					view.setIdle();
					logger.info("completed");		
					logger.info("Size: " + conceptGraph.getSize() + " nodes");		
				}
			});
		});
	}

	private Flowable<Concept> buildConceptFlow(String concept, int level){
		if (stopped) {
			return Flowable.empty();
		}		
		boolean toBeExplored = false;
		synchronized(visited) {
			Integer prev = visited.get(concept);
			if (prev == null || (prev > level)) {
				toBeExplored = true;
				visited.put(concept, level);
			}
		}
		if (toBeExplored) {
			Flowable<Concept> single = wiki.getConcept(concept);		
			Flowable<Concept> linked = 
				single
				.flatMap(c -> {
					if (level < conceptGraph.getMaxLevel()) {
						return Flowable
						.fromIterable(c.getLinkedConcepts())
						.parallel()
						.runOn(Schedulers.io())
						.flatMap(linkedConcept -> {
							return buildConceptFlow(linkedConcept, level + 1);
						})
						.sequential();
					} else {
						return Flowable.just(c);
					}
				});
			return Flowable.merge(single, linked);	
		} else {
			return Flowable.empty();
		}
	}
	
	public void notifyStopped() {
		stopped = true;
		logger.info("stopped");						
		view.setStopped();
	}

}
