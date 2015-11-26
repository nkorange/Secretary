package nkorange.replier.core.module;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author pengfei.zhu.
 */
public abstract class SustainableModule {

    protected LinkedBlockingDeque<String> inputs = new LinkedBlockingDeque<String>();

    private volatile boolean ended = true;

    public boolean end() {
        return ended;
    }

    public void setEnd(boolean ended) {
        this.ended = ended;
    }

    public void put(String input) {
        try {
            inputs.put(input);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
