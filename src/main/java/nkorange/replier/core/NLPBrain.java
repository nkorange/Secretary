package nkorange.replier.core;

import nkorange.replier.Speaker;
import nkorange.replier.core.memory.Memory;
import nkorange.replier.core.module.Module;
import nkorange.replier.core.module.ModuleManager;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author pengfei.zhu.
 */
public class NLPBrain extends Thread implements Brain {

    private Speaker speaker;
    private ModuleManager moduleManager;
    private Memory memory;

    //@PostConstruct
    public void init() {
        //start();
        moduleManager.start();
    }

    public void think(String words) {

        // TODO check memory first
        moduleManager.intoQueue(words);
    }

    public void act(Command cmd) {

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

    }

    public void answer(String words) {
        speaker.say(words);
    }

    public void attach(Speaker speaker) {
        this.speaker = speaker;
    }

    public Speaker getCurrentSpeaker() {
        return speaker;
    }

    public void run() {
        while (true);
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public void setModuleManager(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }
}
