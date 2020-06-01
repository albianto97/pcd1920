package pcd.ass02.sol.es03;

import pcd.ass02.sol.common.ConceptGraph;
import pcd.ass02.sol.common.InputListener;
import pcd.ass02.sol.common.View;

public class Controller implements InputListener {

	private Master master;
	private Wikipedia wiki;
	private ConceptGraph conceptGraph;
	private View view;

	public Controller(Wikipedia wiki, ConceptGraph conceptGraph, View view) {
		this.wiki = wiki;
		this.view = view;
		this.conceptGraph = conceptGraph;
	}

	@Override
	public void notifyStarted(String concept, int maxLevel) {
		master = new Master(concept, maxLevel, wiki, conceptGraph, view);
		master.start();
	}

	@Override
	public void notifyStopped() {
		master.notifyStopped();
	}
	
}
