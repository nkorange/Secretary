package nkorange.secretary;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import nkorange.secretary.core.NLPBrain;
import nkorange.secretary.core.utils.Akka;
import nkorange.secretary.panel.ChatPanel;

/**
 * @author pengfei.zhu.
 */
public class DefaultReplier extends UntypedActor implements Replier {


    private ActorRef brain;

    private ActorRef texter;

    private ActorRef speaker;

    private ChatPanel chatPanel;

    public DefaultReplier() {
        init();
    }

    private void init() {

        chatPanel = new ChatPanel();
        brain = Akka.system().actorOf(Props.create(NLPBrain.class), "brain");
        texter = Akka.system().actorOf(Props.create(Texter.class), "texter");
        speaker = Akka.system().actorOf(Props.create(Speaker.class), "speaker");
    }

    public void start() {
        init();
    }

    public void stop() {

    }

    @Override
    public void onReceive(Object o) throws Exception {

        if (o instanceof String) {
            chatPanel.insert((String) o);
        }
    }
}


