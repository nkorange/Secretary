package nkorange.secretary.core.memory.task;

import akka.actor.UntypedActor;

import java.util.LinkedList;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pengfei.zhu.
 */
public class TaskManager extends UntypedActor {

    private ConcurrentHashMap<Long, LinkedList<Task>> tasks = new ConcurrentHashMap<Long, LinkedList<Task>>();

    private Timer timer = new Timer(true);

    public TaskManager() {
        // TODO: load persistent tasks
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof Task) {
            // Receiving a new task:
            Task task = (Task) message;
            if (tasks.containsKey(task.getExecuteTime())) {
                tasks.get(task.getExecuteTime()).add(task);
            } else {
                LinkedList<Task> list = new LinkedList<Task>();
                list.add(task);
                tasks.put(task.getExecuteTime(), list);
            }

            timer.schedule(task, task.getExecuteTime());
        }

    }
}
