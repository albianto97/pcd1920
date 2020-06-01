package pcd.ass02.sol.es01;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pcd.ass02.sol.common.*;

public class ViewController implements ConceptGraphListener {

	private ExecutorService exec;
	private View view;
	private ViewModel viewModel;
	
	public ViewController(View view, ViewModel viewModel) {
		exec = Executors.newSingleThreadExecutor();
		this.viewModel = viewModel;
		this.view = view;
	}
	
	@Override
	public void notifyConceptGraphChanged() {
		exec.submit(() -> {
			viewModel.update();
			view.refresh();
		});
	}

	@Override
	public void notifyInit(String root, int maxLevels) {
		exec.submit(() -> {
			viewModel.reset();
		});
	}

}
