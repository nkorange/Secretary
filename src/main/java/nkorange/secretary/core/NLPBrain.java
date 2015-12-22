package nkorange.secretary.core;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import nkorange.secretary.core.memory.Memory;
import nkorange.secretary.core.module.ModuleManager;
import nkorange.secretary.core.utils.Akka;

/**
 * @author pengfei.zhu.
 */
public class NLPBrain extends UntypedActor {

    private ActorRef moduleManager;
    private Memory memory;

    public NLPBrain() {
        init();
    }

    //@PostConstruct
    public void init() {
        //start();
        moduleManager = Akka.system().actorOf(Props.create(ModuleManager.class), "manager");
    }

    public void think(String words) {

        // TODO check memory first
        Akka.tellManager(words, getSelf());
    }

/*    public void act(Command cmd) {

        Module module = moduleManager.getModule(cmd.getModuleId());
        Class clazz = module.getInstance().getClass();
        try {
            Method method = clazz.getDeclaredMethod(module.getEntry());
            method.invoke(module.getInstance(), cmd.getArgs());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }*/

    public void answer(String words) {
        Akka.tellUser(words);
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    @Override
    public void onReceive(Object message) {

        System.out.println("brain:" + message);
        if (message instanceof Action) {
            if (Action.isSpeakAction((Action) message)) {
                answer(((Action) message).getArgs());
            }
        } else {
            think((String) message);
        }

    }
}
