package nkorange.secretary.core.utils;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import nkorange.secretary.core.memory.task.Task;

/**
 * @author pengfei.zhu.
 */
public class Akka {

    private static ActorSystem actorSystem;

    static {
        actorSystem = ActorSystem.create("Secretary");
    }

    public static ActorSystem system() {
        return actorSystem;
    }

    public static ActorRef actorFor(String path) {
        return actorSystem.actorFor(path);
    }

    public static ActorRef actorForBrain() {
        return actorSystem.actorFor("/user/brain");
    }

    public static void tell(String path, Object msg) {
        actorSystem.actorFor(path).tell(msg, ActorRef.noSender());
    }

    public static void tellUser(String msg) {
        actorSystem.actorFor("/user/speaker").tell(msg, ActorRef.noSender());
    }

    public static void tellBrain(String msg) {
        actorSystem.actorFor("/user/brain").tell(msg, ActorRef.noSender());
    }

    public static void tellManager(String msg, ActorRef sender) {
        actorSystem.actorFor("/user/manager").tell(msg, sender);
    }

    public static void addTask(Task task, ActorRef sender) {
        actorSystem.actorFor("/user/taskManager").tell(task, sender);
    }

    public static void tellManager(String msg) {
        actorSystem.actorFor("/user/manager").tell(msg, ActorRef.noSender());
    }

    public static ActorRef actorOf(Class<? extends UntypedActor> clazz, String name) {

        return actorSystem.actorOf(Props.create(clazz), name);
    }

}
