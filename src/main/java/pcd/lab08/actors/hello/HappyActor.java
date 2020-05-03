package pcd.lab08.actors.hello;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class HappyActor extends AbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(HelloMsg.class, msg -> {
			System.out.println("Hello " + msg.getContent());
		})./* matchAny(m -> {
			System.out.println("Message not recognized: "+m);
		}).*/build();
	}
}

/*
 * public class HappyActor extends UntypedActor { 
 *   public void onReceive(Object msg) { 
 *     if (msg instanceof HelloMsg) { 
 *       HelloMsg hello = (HelloMsg) msg;
 *       System.out.println("Hello "+hello.getContent()); 
 *     } else unhandled(msg); 
 *   } 
 * }
 */
