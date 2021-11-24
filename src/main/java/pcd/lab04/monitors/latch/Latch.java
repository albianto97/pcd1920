package pcd.lab04.monitors.latch;

public interface Latch {

	void countDown();

	void await() throws InterruptedException;
}
/*
COMMENTO DELL'ESERCIZIO: FINCHE T-B NON PARTE T-A NON PASSA. PERCHE è B CHE DECREMENTA
T-A RIMANE SEMPRE IN AWAIT
* */