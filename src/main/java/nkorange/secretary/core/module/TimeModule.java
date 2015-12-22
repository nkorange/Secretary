package nkorange.secretary.core.module;

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

        // TODO: load all persistent tasks
    }

    public void setAlarm() {

        setEnd(false);
        String time = take();
        Date date = TimeUtil.interpretTime(time);

        report("提醒内容是什么？");
        String remindContent = take();

    }

    public void report(String s) {

    }

    private class Task extends TimerTask {

        @Override
        public void run() {

        }
    }
}
