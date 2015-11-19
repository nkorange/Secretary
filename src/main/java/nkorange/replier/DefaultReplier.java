package nkorange.replier;

import nkorange.replier.core.Brain;
import nkorange.replier.core.Command;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author pengfei.zhu.
 */
public class DefaultReplier implements Replier {


    private Brain brain;

    private Listener listener;

    private Speaker speaker;

    private Worker worker;

    private ExecutorService executor = Executors.newFixedThreadPool(100);

    private boolean inited = false;

    private void init() {
        if (!inited) {
            brain.init();
            listener.init();
            speaker.init();
            worker.init();
            brain.attach(speaker);
        }
        inited = true;
    }

    public String listen() {
        return listener.read();
    }

    public void answer(String words) {
        speaker.say(words);
    }

    public void recognize() {

    }

    public void start() {

        String words;

        if (!inited) {
            init();
        }

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
        executor.submit(new AnalyzeTask(words));
    }

    public void stop() {

    }

    class AnalyzeTask implements Runnable {

        private String words;

        public AnalyzeTask(String words) {
            this.words = words;
        }

        public void run() {
            brain.think(words);
        }
    }

    public void setBrain(Brain brain) {
        this.brain = brain;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}


