package pcd.ass02.sol.es01;

import pcd.ass02.sol.common.*;

public class Main {

	public static void main(String[] args) throws Exception {
		
		/* model */
		Wikipedia wiki = new Wikipedia();
		ConceptGraph conceptGraph = new ConceptGraph();

		/* view - viewmodel */
		ViewModel viewModel = new ViewModel(conceptGraph);		
		View view = new View(1200, 800, viewModel);
		ViewController viewController = new ViewController(view, viewModel);
		conceptGraph.addListener(viewController);

		/* controller */
		Controller controller = new Controller(wiki, conceptGraph, view);
		view.addInputListener(controller);
		
		view.showUp();
	}

}
