package nkorange.secretary.core.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author pengfei.zhu.
 */
public class TaskManager {

    private ScheduledExecutorService executor;

    public TaskManager() {
        executor = Executors.newScheduledThreadPool(100);
    }

}
