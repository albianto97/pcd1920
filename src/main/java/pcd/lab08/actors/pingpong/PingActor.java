package pcd.lab08.actors.pingpong;

import akka.actor.*;

public class PingActor extends AbstractActor {

	public void preStart() {
		  final ActorRef ponger = getContext().actorOf(Props.create(PongActor.class), "ponger");
		  ponger.tell(new PingMsg(0), getSelf());  //getSelf è chi lo invia, 0 è quello che passa
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(PongMsg.class, msg -> {
			  System.out.println("PONG received: "+  msg.getValue());
			  getSender().tell(new PingMsg( msg.getValue() + 1), getSelf());
		}).build();
	}
	
}
