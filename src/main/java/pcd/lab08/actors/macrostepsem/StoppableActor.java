package pcd.lab08.actors.macrostepsem;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;

public class StoppableActor extends AbstractActor {
	private boolean stopped;
	
	public Receive createReceive() {
		return receiveBuilder()
		.match(PrintMsg.class, msg -> {
			stopped = false;
			getSelf().tell(new PrintNextMsg(0, msg.getNum()), ActorRef.noSender());
		}).match(PrintNextMsg.class, msg -> {
			
			if (!stopped && (msg.getNum() < msg.getMax())){
				System.out.println(msg.getNum());
				getSelf().tell(new PrintNextMsg(msg.getNum() + 1, msg.getMax()), ActorRef.noSender());
			}
		}).match(StopMsg.class, msg -> {
			System.out.println("stopped!");
			stopped = true;
		}).build();
	}
}
