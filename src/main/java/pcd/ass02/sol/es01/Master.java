package pcd.ass02.sol.es01;

import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

import pcd.ass02.sol.common.*;

public class Master extends Thread {
	
    private final ForkJoinPool exec;
	private ScheduledExecutorService timerExec;
	private static final long PERIOD = 100000; /* 10 secs */ 

	private String rootConcept;
	private ConceptGraph conceptGraph;
	private Wikipedia wiki;
	private int maxLevels;
	private Flag stopFlag;
	private View view;
	private volatile boolean stopped;
	
    private static final Logger logger = Logger.getLogger("[master]");
    
	public Master(String rootConcept, int maxLevels, Wikipedia wiki, ConceptGraph graph, Flag stopFlag, View view) {
		exec = new ForkJoinPool();
		this.rootConcept = rootConcept;
		this.wiki = wiki;
		this.maxLevels = maxLevels;
		this.conceptGraph = graph;
		this.view = view;
		this.stopFlag = stopFlag;
	}

	public void run() {
		logger.info("started");
		conceptGraph.init(rootConcept, maxLevels);
		// logger.setLevel(java.util.logging.Level.OFF);

		HashMap<String, Integer> visited = new HashMap<String, Integer>();
		stopped = false;
		
		/* check periodically for changes */
		
		while (!stopped) {
			logger.info("Running");
			view.setRunning();
			visited.clear();
			ForkJoinTask<?> fut2 = exec.submit(new BuildAndRefreshConceptNodeTask(visited, rootConcept, wiki, conceptGraph, 0, stopFlag));
			fut2.join();
			if (!stopFlag.isSet()) {
				logger.info("completed - " + conceptGraph.getSize());		
				view.setIdle();
			}
			try {
				Thread.sleep(PERIOD);
			} catch (InterruptedException ex) {
			}
		}
	}
	
	public void notifyStopped() {
		stopped = true;
		logger.info("stopped");						
		view.setStopped();
		interrupt();
	}
}
