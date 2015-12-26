package nkorange.secretary.core;

import nkorange.secretary.core.utils.Akka;

/**
 * @author pengfei.zhu.
 */
public class Action {

    private static enum ActionType {
        SPEAK
    }

    private ActionType type;

    private String args;

    private Action(ActionType type, String args) {
        this.type = type;
        this.args = args;
    }

    public void act() {
        if (type == ActionType.SPEAK) {
            Akka.tellUser(args);
        }
    }

    public String getArgs() {
        return args;
    }

    public static Action speakAction(String words) {
        return new Action(ActionType.SPEAK, words);
    }

    public static boolean isSpeakAction(Action action) {
        return action.type == ActionType.SPEAK;
    }
}
