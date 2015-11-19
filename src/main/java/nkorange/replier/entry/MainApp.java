package nkorange.replier.entry;

import nkorange.replier.Replier;
import nkorange.replier.exceptions.SysInitException;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author pengfei.zhu.
 */
public class MainApp {

    private static ClassPathXmlApplicationContext context;

    private static Replier replier;

    private static volatile boolean running = true;

    public static void main(String[] args) {

        try {
            init();
        } catch (SysInitException e) {
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {

                synchronized (MainApp.class) {
                    running = false;
                    MainApp.class.notify();
                }
            }

        });

        start();

        synchronized (MainApp.class) {
            while (running) {
                try {
                    MainApp.class.wait();
                } catch (Throwable e) {
                }
            }
        }

    }

    private static void init() throws SysInitException {

        try {
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
        } catch (BeansException e) {
            throw new SysInitException(e.getMessage());
        } finally {
            if (context == null) {
                throw new SysInitException("context is null!");
            }
        }

        System.out.println("context:" + context);

        replier = context.getBean(Replier.class);
    }

    private static void start() {
        replier.start();
    }

}
