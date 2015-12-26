package nkorange.secretary.core.memory;

import akka.actor.ActorRef;
import akka.actor.Props;
import nkorange.secretary.core.memory.task.TaskManager;
import nkorange.secretary.core.module.ModuleManager;
import nkorange.secretary.core.utils.Akka;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author pengfei.zhu.
 */
public class Memory {

    private ActorRef taskManager;

    public static Memory loadMemory() {

        Memory memory = new Memory();
        return memory;
    }

    protected Memory() {

        taskManager = Akka.system().actorOf(Props.create(TaskManager.class), "taskManager");
    }

    protected void saveMemory() {

    }

}
