package nkorange.secretary.core;

import nkorange.secretary.Speaker;

/**
 * Core interface of the replier
 *
 * @author pengfei.zhu.
 */
public interface Brain {

    public void init();

    public void think(String words);

    public void act(Command cmd);

    public void attach(Speaker speaker);

    public Speaker getCurrentSpeaker();

    public void answer(String msg);
}
