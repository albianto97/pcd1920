package pcd.lab04.monitors.resman;

import java.util.ArrayList;
import java.util.List;

public class TestResManager {

	public static void main(String[] args) throws InterruptedException {
		
		int nResources = 5;
		int nWorkers = 10;

		ResManager resMan = new ResManagerImpl(nResources);

		List<Worker> workers = new ArrayList<Worker>();
		for (int i = 0; i < nWorkers; i++) {
			workers.add(new Worker("Worker-"+i, resMan));
		}

		for (Worker w: workers) {
			w.start();
		}



		
	}
}
