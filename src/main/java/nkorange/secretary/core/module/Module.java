package nkorange.secretary.core.module;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author pengfei.zhu.
 */
public class Module {

    private long id;

    private String source;

    private String entry;

    private Object instance;

    private String[] args;

    private Router router;

    public boolean end() {
        Class clazz = instance.getClass();
        Object res = null;
        try {
            Method method = clazz.getDeclaredMethod("end");
            res = method.invoke(instance);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (res == null) {
            throw new RuntimeException("not get the result of the method");
        }

        return (Boolean) res;

    }

    public void put(String input) {

        Class clazz = instance.getClass();
        try {
            Method method = clazz.getDeclaredMethod("put", String.class);
            method.invoke(instance, input);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void process(String[] args) {

        Class clazz = instance.getClass();
        Method method;
        try {
            method = clazz.getDeclaredMethod(entry);
            if (method.isVarArgs()) {
                method.invoke(instance, args);
            } else {
                method.invoke(instance);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public boolean needArg() {
        return args != null && args.length > 0;
    }
}
