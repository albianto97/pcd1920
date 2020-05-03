package pcd.lab08.actors.pingpong;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {
  public static void main(String[] args) {
	//DEPRECATED
    //akka.Main.main(new String[] { PingActor.class.getName() });
    
    ActorSystem system = ActorSystem.create("MySystem");
    ActorRef pinger = system.actorOf(Props.create(PingActor.class), "pinger");
  }
}
