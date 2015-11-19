package nkorange.replier.core.module;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author pengfei.zhu.
 */
public class ModuleProxy implements MethodInterceptor {

    private Object target;

    private SpeakModule speakModule;

    public ModuleProxy(SpeakModule module) {
        speakModule = module;
    }

    public Object getInstance(Object target) {

        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        if (method.getName() == BaseModule.REPORT) {
            say((String) objects[0]);
            return null;
        }
        methodProxy.invokeSuper(o, objects);
        return null;
    }

    private void say(String msg) {
        speakModule.say(msg);
    }
}
