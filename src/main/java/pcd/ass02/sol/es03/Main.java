package pcd.ass02.sol.es03;
import pcd.ass02.sol.common.ConceptGraph;
import pcd.ass02.sol.common.View;
import pcd.ass02.sol.common.ViewModel;

public class Main {

	public static void main(String[] args) throws Exception {

		/* model */
		Wikipedia wiki = new Wikipedia();
		ConceptGraph conceptGraph = new ConceptGraph();

		/* view - viewmodel */
		ViewModel viewModel = new ViewModel(conceptGraph);		
		View view = new View(800, 600, viewModel);
		ViewController viewController = new ViewController(view, viewModel);
		conceptGraph.addListener(viewController);

		/* controller */
		Controller controller = new Controller(wiki, conceptGraph, view);
		view.addInputListener(controller);
		
		view.showUp();
	}
	

}
