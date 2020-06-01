package pcd.ass02.sol.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ViewModelUpdater extends Thread {

	private ViewModel viewModel;
	private HashMap<String,String> visited;
	
	public ViewModelUpdater(ViewModel viewModel) {
		this.viewModel = viewModel;
		visited = new HashMap<String,String>();
	}
	
	public void run() {
		try {
			long t = System.currentTimeMillis();
			while (true) {

				long dt = System.currentTimeMillis() - t;
				applyRepulsion(dt);
				
				visited.clear();
				Optional<ViewModelNode> node = viewModel.getRootNode();
				if (node.isPresent()) {
					checkAttraction(dt, node.get(), 0);
				}
				t = System.currentTimeMillis();

				try {
					Thread.sleep(10);
				} catch (Exception ex) {}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	private void applyRepulsion(long dt) {
		Collection<ViewModelNode> nodes = viewModel.getNodes();
		ArrayList<ViewModelNode> list = new ArrayList<ViewModelNode>(nodes);
		for (int i = 0; i < list.size(); i++) {
			ViewModelNode node = list.get(i);
			V2d force = new V2d(0,0);
			for (int j = 0; j < list.size(); j++) {
				if (j != i) {
					ViewModelNode node2 = list.get(j);
					V2d v = new V2d(node.getPos(),node2.getPos());
					double abs = v.abs();
					force = force.sum(v.mul(1.0 / (abs*abs)));
				}
			}
			node.setPos(node.getPos().sum(force.mul(dt*0.000005)));										
		}
	}

	private void checkAttraction(long dt, ViewModelNode node, int level) {
		String key = node.getConcept().getContent();
		String vis = visited.get(key);
		if (vis == null) {
			visited.put(key,key);
			List<ViewModelNode> linked = node.getLinked();
			for (ViewModelNode l: linked) {
				V2d v = new V2d(node.getPos(),l.getPos());
				double abs = v.abs();
				l.setPos(l.getPos().sum(v.mul(dt*0.00001 / abs)));
				checkAttraction(dt, l, level + 1);
			}
		}
	}
}
