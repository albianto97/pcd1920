package pcd.ass02.sol.es02;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import pcd.ass02.sol.common.*;

public class ViewController extends AbstractVerticle implements ConceptGraphListener  {

	private View view;
	private ViewModel viewModel;
	
	public ViewController(View view, ViewModel viewModel) {
		this.view = view;
		this.viewModel = viewModel;
	}
	
	public void start() {
		EventBus eb = vertx.eventBus();
		eb.consumer("concept-graph-events", message -> {
			if (message.body().equals("new-concept")) {
				viewModel.update();
				view.refresh();
			} else if (message.body().equals("init")) {
				viewModel.reset();
			}
		});
	}
	
	@Override
	public void notifyConceptGraphChanged() {
		EventBus eb = vertx.eventBus();
		eb.send("concept-graph-events", "new-concept");
	}

	@Override
	public void notifyInit(String root, int maxLevels) {
		EventBus eb = vertx.eventBus();
		eb.send("concept-graph-events", "init");
	}

}
