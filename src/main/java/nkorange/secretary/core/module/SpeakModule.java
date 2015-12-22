package nkorange.secretary.core.module;

import nkorange.secretary.core.Action;

import java.util.Queue;

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
