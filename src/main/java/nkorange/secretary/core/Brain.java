package nkorange.secretary.core;

import nkorange.secretary.Response;

/**
 * Brain interface of the replier
 *
 * @author pengfei.zhu.
 */
public interface Brain {

    public void think(String words);

    public void answer(String msg);
}
