package pcd.lab08.actors.macrostepsem;

import akka.actor.AbstractActor;

public class UnstoppableActor extends AbstractActor {
	private boolean stopped;

	public Receive createReceive() {
		return receiveBuilder()
		.match(PrintMsg.class, msg -> {
			stopped = false;
			for (int i = 0; i < msg.getNum() && !stopped; i++) {
				System.out.println(" " + i);				
			}
		}).match(StopMsg.class, msg -> {
			System.out.println("stopped!");
			stopped = true;
		}).build();
	}
}
