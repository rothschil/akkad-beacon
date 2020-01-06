package xyz.wongs.weathertop.design.proxy.dyc.cglib;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class LoginServiceDycProxyCglib implements MethodInterceptor {

    public Object getInstall(Object object){
        return Enhancer.create(object.getClass(), this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        log.error("Begin 执行方法前的操作");
        methodProxy.invokeSuper(o,objects);
        log.error("End 执行方法后的操作");
        return null;
    }
}
