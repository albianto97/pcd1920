package pcd.ass02.sol.es01;

import pcd.ass02.sol.common.*;

public class Controller implements InputListener {

	private Master master;
	private Wikipedia wiki;
	private ConceptGraph conceptGraph;
	private Flag stopFlag;
	private View view;

	public Controller(Wikipedia wiki, ConceptGraph conceptGraph, View view) {
		this.wiki = wiki;
		this.view = view;
		this.conceptGraph = conceptGraph;
		stopFlag = new Flag();
	}

	@Override
	public void notifyStarted(String concept, int maxLevel) {
		stopFlag.reset();
		master = new Master(concept, maxLevel, wiki, conceptGraph, stopFlag, view);
		master.start();
	}

	@Override
	public void notifyStopped() {
		stopFlag.set();
		master.notifyStopped();
	}
	
}
