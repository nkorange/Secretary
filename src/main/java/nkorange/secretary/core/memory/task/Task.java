package nkorange.secretary.core.memory.task;

import nkorange.secretary.core.Action;

import java.util.Date;
import java.util.TimerTask;

/**
 * @author pengfei.zhu.
 */
public class Task extends TimerTask {

    private long executeTime;

    private int executeCount;

    private long period;

    private Action action;

    public Task(Action action) {
        this(System.currentTimeMillis(), action);
    }

    public Task(long executeTime, Action action) {
        this(executeTime, 1, 0, action);
    }

    public Task(long executeTime, long period, Action action) {
        this(executeTime, -1, period, action);
    }

    public Task(long executeTime, int executeCount, long period, Action action) {
        this.executeTime = executeTime;
        this.executeCount = executeCount;
        this.period = period;
        this.action = action;
    }

    @Override
    public void run() {

        action.act();
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public int getExecuteCount() {
        return executeCount;
    }

    public long getPeriod() {
        return period;
    }
}
