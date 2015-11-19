package nkorange.replier.core.module;

import nkorange.replier.core.Action;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author pengfei.zhu.
 */
public class SpeakModule {

    private Queue<Action> outQueue;

    public void attach(Queue<Action> queue) {
        outQueue = queue;
    }

    public void say(String words) {

        Action action = Action.speakAction(words);
        outQueue.add(action);
    }
}
