package nkorange.secretary;

import akka.actor.UntypedActor;
import nkorange.secretary.core.utils.Akka;
import org.springframework.stereotype.Component;

/**
 * @author pengfei.zhu.
 */
@Component
public class Texter extends UntypedActor implements Response {

    public void init() {

    }

    public void say(String msg) {
        Akka.tellReplier("小秘：" + msg);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        say((String) message);
    }
}
