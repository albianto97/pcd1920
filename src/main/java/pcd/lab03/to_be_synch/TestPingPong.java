package pcd.lab03.to_be_synch;

/**
 * Unsynchronized version
 * 
 * @TODO make it sync 
 * @author aricci
 *
 */
public class TestPingPong {

	public static void main(String[] args) {
		new Pinger().start();
		new Ponger().start();
	}

}


//problema che loro non parlano ed eseguono come vogliono.
//soluzione è mettere semaforo o monitor