package pcd.lab08.actors.macrostepsem;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {
  public static void main(String[] args) {
    ActorSystem system = ActorSystem.create("MySystem");
    //ActorRef act = system.actorOf(Props.create(StoppableActor.class));
    ActorRef act = system.actorOf(Props.create(UnstoppableActor.class));
    act.tell(new PrintMsg(100000), ActorRef.noSender());
    
    try {
    		Thread.sleep(100);
    } catch (Exception ex){}
    
    act.tell(new StopMsg(), ActorRef.noSender());
    System.out.println("!! stop sent !!");
  }
}
