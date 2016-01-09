package nkorange.secretary.entry;

import akka.actor.ActorRef;
import akka.actor.Props;
import nkorange.secretary.DefaultReplier;
import nkorange.secretary.Replier;
import nkorange.secretary.core.NLPBrain;
import nkorange.secretary.core.utils.Akka;
import nkorange.secretary.exceptions.SysInitException;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author pengfei.zhu.
 */
public class MainApp {

    private static ClassPathXmlApplicationContext context;

    private static ActorRef replier;

    public static void main(String[] args) {

        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        replier = Akka.system().actorOf(Props.create(DefaultReplier.class), "replier");
    }

}
