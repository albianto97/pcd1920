package pcd.ass01.seq;

/**
 * Bodies simulation - legacy code: sequential, unstructured
 * 
 * @author aricci
 */
public class SequentialBodySimulationMain {

    public static void main(String[] args) {
                
    	SimulationViewer viewer = null; 
    	// SimulationViewer viewer = new SimulationViewer(620,620);

    	Simulator sim = new Simulator(viewer);
        sim.execute();

    }
}
