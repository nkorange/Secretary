package nkorange.replier;

import org.springframework.stereotype.Component;

/**
 * @author pengfei.zhu.
 */
@Component
public class ScreenWriter implements Speaker {
    public void init() {

    }

    public void say(String msg) {
        System.out.println(msg);
    }
}
