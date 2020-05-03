package pcd.lab08.actors.stash;

import akka.actor.AbstractActorWithStash;

public class ActorWithProtocol extends AbstractActorWithStash {
	@Override
	public Receive createReceive() {
		return openableBehaviour();
	}

	private Receive openableBehaviour() {
		return receiveBuilder()
				.matchEquals("open", s -> {
					System.out.println("opened.");
					getContext().become(openedBehaviour(), false);
				})
				.matchAny(msg -> stash())
				.build();
	}

	private Receive openedBehaviour() {
		return receiveBuilder()
				.matchEquals("write", ws -> {
					System.out.println("do write.");
				})
				.matchEquals("close", cs -> {
					System.out.println("close.");
					unstashAll();
					getContext().unbecome();
				})
				.matchAny(msg -> stash())
				.build();
	}
}
