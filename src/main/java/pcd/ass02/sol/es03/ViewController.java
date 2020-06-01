package pcd.ass02.sol.es03;

import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import pcd.ass02.sol.common.*;

public class ViewController implements ConceptGraphListener {

	private PublishSubject<String> updates;
	
	public ViewController(View view, ViewModel viewModel) {
		updates = PublishSubject.create();
		updates
		.observeOn(Schedulers.computation());
		
		updates
		.filter(e -> e.equals("updated"))
		.subscribe(e -> {
			viewModel.update();
			view.refresh();
		});

		updates
		.filter(e -> e.equals("init"))
		.subscribe(e -> {
			viewModel.reset();
		});
	}
	
	@Override
	public void notifyConceptGraphChanged() {
		updates.onNext("updated");
	}

	@Override
	public void notifyInit(String root, int maxLevels) {
		updates.onNext("init");
	}

}
