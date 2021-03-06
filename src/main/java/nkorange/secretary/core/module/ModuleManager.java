package nkorange.secretary.core.module;

import akka.actor.UntypedActor;
import nkorange.secretary.core.nlp.NLPInfo;
import nkorange.secretary.core.nlp.NLPProcessor;
import nkorange.secretary.core.nlp.StandfordNLPProcessor;
import nkorange.secretary.core.utils.Akka;
import nkorange.secretary.core.utils.XmlUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author pengfei.zhu.
 */
public class ModuleManager extends UntypedActor {

    private Map<String, Module> modules = new ConcurrentHashMap<String, Module>();

    private Map<String, Object> instances = new ConcurrentHashMap<String, Object>();

    private Module listeningModule = null;

    private ModuleClassLoader loader = new ModuleClassLoader();

    private static final String MODULE_PATH = "src/main/resource/memory/modules";

    private static final String MODULE_CONFIG = "src/main/resource/memory/modules.xml";

    private ExecutorService executor = Executors.newFixedThreadPool(100);

    private NLPProcessor processor;

    public ModuleManager() {

        processor = new StandfordNLPProcessor();
        loadAllModules();
    }

    public ModuleProxy createProxy() {
        return new ModuleProxy();
    }

    public void loadAllModules() {

        List<Module> list = XmlUtil.getAllModules(MODULE_CONFIG);

        if (list == null || list.isEmpty()) {
            return;
        }

        for (Module module : list) {
            loadModule(module.getId(), module.getSource(), module.getEntry(), module.getRouter(), module);
        }
    }

    private void loadModule(long id, String path, String entry, Router router, Module module) {

        if (StringUtils.isNotEmpty(path)) {
            loader.addModule(path);
        }

        String className = entry.substring(0, entry.lastIndexOf(".")); // get class name
        String methodName = entry.substring(entry.lastIndexOf(".") + 1);

        try {
            Class clazz = loader.loadClass(className);

            Object instance;
            if (instances.containsKey(className)) {
                instance = instances.get(className);
            } else {
                instance = clazz.newInstance();
                instances.put(className, instance);
            }
            ModuleProxy proxy = createProxy();
            Object proxyInstance = proxy.getInstance(instance);
            module.setInstance(proxyInstance);
            module.setEntry(methodName);
            module.setRouter(router);

            modules.put(String.valueOf(id), module);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void loadModule(long id, String path, String entry, String route) {
        loadModule(id, path, entry, new Router(route), new Module());
    }

    public long matchNLPInfo(NLPInfo info) {

        // to keep it simple for now, we don't tolerate tokens other than those included in Condition.exists list.
        // construct a Condition to match the modules:
        Condition condition = Condition.fromNLPInfo(info);

        for (Map.Entry<String, Module> entry : modules.entrySet()) {
            Module module = entry.getValue();

            // The first module we find matches the condition is returned
            if (module.getRouter().match(condition)) {
                return module.getId();
            }
        }

        return 0L;
    }

    public Module getModule(long id) {
        return modules.get(String.valueOf(id));
    }

    public String[] parseArguments(NLPInfo info) {
        return null;
    }

    @Override
    public void onReceive(Object message) throws Exception {

        String words = (String) message;

        System.out.println("manager:" + message);

        // If message is not sent from brain:
        if (getSender() != Akka.actorForBrain()) {
            // TODO, should evaluate the msg before delivering to user
            Akka.tellUser(words);
            return;
        }

        if (StringUtils.isEmpty(words)) {
            return;
        }

        if (listeningModule != null && !listeningModule.end()) {

            listeningModule.put(words);
            //listeningModule = null;
            return;
        }

        listeningModule = null;

        executor.submit(new Task(words));

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class Task extends Thread {

        private String words;

        public Task(String words) {
            this.words = words;
        }

        public void run() {
            process(words);
        }
    }

    private void process(String words) {

        System.out.println("task:" + words);
        // Delete all punctuations:
        words = words.replaceAll("[\\pP‘’“”]", "");
        List<NLPInfo> list = processor.analyze(words);

        if (list == null || list.isEmpty()) {
            report("没有理解到任何意思");
            return;
        }

        for (NLPInfo info : list) {

            Module module = getModule(matchNLPInfo(info));
            if (module == null) {
                report("不知道如何处理");
                return;
            }

            listeningModule = module;
            listeningModule.process(parseArguments(info));
        }

    }

    private void report(String words) {
        Akka.tellUser(words);
    }

    public void setProcessor(NLPProcessor processor) {
        this.processor = processor;
    }

}
