package nkorange.secretary;

import akka.actor.UntypedActor;
import org.springframework.stereotype.Component;

/**
 * @author pengfei.zhu.
 */
@Component
public class ScreenWriter extends UntypedActor implements Speaker {

    public void init() {

    }

    public void say(String msg) {
        System.out.println(msg);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        say((String) message);
    }
}
