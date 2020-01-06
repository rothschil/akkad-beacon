package xyz.wongs.weathertop.design.proxy.dyc.jdk;

import lombok.extern.slf4j.Slf4j;
import xyz.wongs.weathertop.design.proxy.Loginable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Slf4j
public class LoginServiceDycProxy implements InvocationHandler {

    private Loginable loginable;

    public Object getInstall(Loginable loginable){
        this.loginable = loginable;
        return Proxy.newProxyInstance(loginable.getClass().getClassLoader(),loginable.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.error("Begin 执行方法前的操作");

        if(method.getName().equalsIgnoreCase("login")){
            loginable.login();
        }
        log.error("End 执行方法后的操作");
        return null;
    }
}
