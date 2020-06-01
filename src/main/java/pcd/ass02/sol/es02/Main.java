package pcd.ass02.sol.es02;

import io.vertx.core.Vertx;
import pcd.ass02.sol.common.ConceptGraph;
import pcd.ass02.sol.common.View;
import pcd.ass02.sol.common.ViewModel;

public class Main {
	public static void main(String[] args) throws Exception {
		Vertx  vertx = Vertx.vertx();

		/* model */
		Wikipedia wiki = new Wikipedia(vertx);
		ConceptGraph conceptGraph = new ConceptGraph();

		/* view - viewmodel */
		ViewModel viewModel = new ViewModel(conceptGraph);		
		View view = new View(1200, 800, viewModel);
		ViewController viewController = new ViewController(view, viewModel);
		conceptGraph.addListener(viewController);
		vertx.deployVerticle(viewController);

		/* controller */
		Controller controller = new Controller(vertx, wiki, conceptGraph, view);
		view.addInputListener(controller);
		
		view.showUp();
	}

}
