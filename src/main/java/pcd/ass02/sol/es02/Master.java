package pcd.ass02.sol.es02;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import pcd.ass02.sol.common.Concept;
import pcd.ass02.sol.common.ConceptGraph;
import pcd.ass02.sol.common.View;

public class Master extends AbstractVerticle {

	private String rootConcept;
	private ConceptGraph conceptGraph;
	private Wikipedia wiki;
	private int maxLevels;
	private View view;
	private boolean stopped;
	private long timerId;
	private static final long PERIOD = 200000; /* 20 secs */ 
	private HashMap<String, Integer> visited;
	private static final Logger logger = Logger.getLogger("[master]");

	public Master(Wikipedia wiki, ConceptGraph graph, View view) {
		this.wiki = wiki;
		this.view = view;
		this.conceptGraph = graph;
		visited = new HashMap<String, Integer>();

	}

	public void start() {
		logger.info("booted");
		getVertx().eventBus()
		.consumer("controller-events", message -> {
			JsonObject ev = (JsonObject) message.body();
			String event = ev.getString("event");
			if (event.equals("started")) {
				visited.clear();
				rootConcept = ev.getString("concept");
				maxLevels = ev.getInteger("maxLevels");
				conceptGraph.init(rootConcept, maxLevels);
				stopped = false;
				buildAndRefresh(0);
				timerId = getVertx().setPeriodic(PERIOD, this::buildAndRefresh);
			} else if (event.equals("stopped")) {
				stopped = true;
				vertx.cancelTimer(timerId);
				logger.info("stopped");
				view.setStopped();
			}
		});

	}

	void buildAndRefresh(long id) {
		visited.clear();
		view.setRunning();
		// logger.setLevel(java.util.logging.Level.OFF);
		if (!stopped) {
			buildAndRefresh(rootConcept, 0)
			.onSuccess(res -> {
				view.setIdle();
				logger.setLevel(java.util.logging.Level.INFO);
				if (!stopped) {
					logger.info("completed - " + conceptGraph.getSize() + " nodes");
				} else {
					logger.info("stopped");
				}
			});
		}
	}

	private Future<Void> buildAndRefresh(String concept, int level) {
		Promise<Void> promise = Promise.promise();

		boolean toBeExplored = false;
		Integer prev = visited.get(concept);

		/* 
		 * Explore the concept if concept has not already been explored or 
		 * already explored but at different (greater) level from the root 
		 */
		if (prev == null || prev > level) {
			toBeExplored = true;
			visited.put(concept, level);
		}
		
		if (!toBeExplored || stopped) {
			promise.complete(null);
		} else {
				wiki
				.getConcept(concept)
				.onSuccess((Concept co) -> {
					if (co != null) {
						boolean added = conceptGraph.addConceptIfNotAlreadyPresent(co);
						if (added) {
							logger.info("new concept added: " + concept + " - " + conceptGraph.getSize());
						} else {
							logger.info("concept already found: " + concept);		
						}
						if (level < conceptGraph.getMaxLevel()) {
							List<String> linked = co.getLinkedConcepts();
							LinkedList<String> completed = new LinkedList<String>();
							for (String linkedConcept : linked) {
								if (stopped) {
									promise.complete();
									break;
								}
								buildAndRefresh(linkedConcept, level + 1)
								.onSuccess(res -> {
									completed.add(linkedConcept);
									if (completed.size() == linked.size()) {
										promise.complete();
									}
								});
							}
						} else {
							promise.complete();
						}
					} else {
						// logger.info("Max level reached with " + concept);
						promise.complete(null);
					}
				});
		}
		return promise.future();
	}

}
