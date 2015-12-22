package nkorange.secretary;

import akka.actor.ActorRef;
import akka.actor.Props;
import nkorange.secretary.core.NLPBrain;
import nkorange.secretary.core.utils.Akka;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author pengfei.zhu.
 */
public class DefaultReplier implements Replier {


    private ActorRef brain;

    private Listener listener;

    private ActorRef speaker;

    private Worker worker;

    private ExecutorService executor = Executors.newFixedThreadPool(100);

    private boolean inited = false;

    private void init() {
        if (!inited) {

            brain = Akka.system().actorOf(Props.create(NLPBrain.class), "brain");
            speaker = Akka.system().actorOf(Props.create(ScreenWriter.class), "speaker");
            listener.init();
            worker.init();
        }
        inited = true;
    }

    public String listen() {
        return listener.read();
    }

    public void recognize() {

    }

    public void start() {

        String words;

        init();

        while (true) {
            words = listen();
            analyze(words);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void analyze(String words) {
        Akka.tellBrain(words);
    }

    public void stop() {

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}


