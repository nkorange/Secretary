package nkorange.secretary.core.module;

import akka.actor.ActorRef;
import nkorange.secretary.core.Action;
import nkorange.secretary.core.memory.task.Task;
import nkorange.secretary.core.utils.Akka;
import nkorange.secretary.core.utils.TimeUtil;

import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pengfei.zhu.
 */
public class TimeModule extends SustainableModule implements CradleModule {

    private ConcurrentHashMap<Long, Task> tasks = new ConcurrentHashMap<Long, Task>();

    public TimeModule() {

    }

    public void setAlarm() {

        setEnd(false);
        report("什么时候提醒您？");
        String time = take();
        Date date = TimeUtil.interpretTime(time);

        report("提醒内容是什么？");
        String remindContent = take();
        Task task = new Task(date.getTime(), Action.speakAction(remindContent));
        Akka.addTask(task, ActorRef.noSender());
    }

    public void report(String s) {

    }

}
