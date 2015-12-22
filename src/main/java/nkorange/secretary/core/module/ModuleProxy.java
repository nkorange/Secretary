package nkorange.secretary.core.module;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import nkorange.secretary.core.utils.Akka;

import java.lang.reflect.Method;

/**
 * @author pengfei.zhu.
 */
public class ModuleProxy implements MethodInterceptor {

    private Object target;

    public Object getInstance(Object target) {

        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        if (method.getName() == CradleModule.REPORT) {
            say((String) objects[0]);
            return null;
        }
        return methodProxy.invokeSuper(o, objects);
    }

    private void say(String msg) {
        Akka.tellManager(msg);
    }
}
