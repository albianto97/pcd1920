package pcd.lab08.actors.hello;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {
  public static void main(String[] args) throws Exception  {
    ActorSystem system = ActorSystem.create("MySystem");
    
    ActorRef act = system.actorOf(Props.create(HappyActor.class));
    act.tell(new HelloMsg("World"), ActorRef.noSender()); //senza far sapere chi lo ha inviato
    act.tell("Another msg", ActorRef.noSender());
    
    Thread.sleep(1000);
  }
}
