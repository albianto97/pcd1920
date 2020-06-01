package pcd.ass02.sol.es02;

import java.util.concurrent.ExecutorService;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import pcd.ass02.sol.common.ConceptGraph;
import pcd.ass02.sol.common.ConceptGraphListener;
import pcd.ass02.sol.common.Flag;
import pcd.ass02.sol.common.InputListener;
import pcd.ass02.sol.common.View;
import pcd.ass02.sol.common.ViewModel;

public class Controller implements InputListener {

	private Master master;
	private Wikipedia wiki;
	private ConceptGraph conceptGraph;
	private View view;
	private Flag stopFlag;
	private Vertx vertx;
	

	public Controller(Vertx vertx, Wikipedia wiki, ConceptGraph conceptGraph, View view) {
		this.wiki = wiki;
		this.vertx = vertx;
		this.view = view;
		this.conceptGraph = conceptGraph;
		stopFlag = new Flag();
		Master master = new Master(wiki, conceptGraph, view);
		vertx.deployVerticle(master);
	}

	@Override
	public void notifyStarted(String concept, int maxLevel) {
		stopFlag.reset();
		EventBus eb = vertx.eventBus();		
		JsonObject startedEv = new JsonObject()
				.put("event", "started")
				.put("concept", concept)
				.put("maxLevels", maxLevel);		
		eb.send("controller-events", startedEv);
	}

	@Override
	public void notifyStopped() {
		stopFlag.set();
		EventBus eb = vertx.eventBus();
		JsonObject stoppedEv = new JsonObject()
				.put("event", "stopped");
		eb.send("controller-events", stoppedEv);
		
	}
}
