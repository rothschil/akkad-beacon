
# 1. 前情内容

[浅析Java设计模式【1】——观察者](https://www.jianshu.com/p/445b68341877)

[浅析Java设计模式【2】——适配器](https://www.jianshu.com/p/02cb3e960377)

[浅析Java设计模式【3】——代理](https://www.jianshu.com/p/5e604c585263)


# 2. 目录

<!-- TOC -->

- [1. 前情内容](#1-前情内容)
- [2. 目录](#2-目录)
- [3. 概念](#3-概念)
- [4. 静态代理](#4-静态代理)
- [5. 动态代理](#5-动态代理)
    - [5.1. JDK动态代理](#51-jdk动态代理)
        - [5.1.1. 实现](#511-实现)
        - [5.1.2. 演示结果](#512-演示结果)
    - [5.2. cglib代理](#52-cglib代理)
        - [5.2.1. 引入依赖包](#521-引入依赖包)
        - [5.2.2. 代理类](#522-代理类)
        - [5.2.3. 测试](#523-测试)
        - [5.2.4. 演示结果](#524-演示结果)
    - [5.3. jdk代理与cglib代理比较](#53-jdk代理与cglib代理比较)

<!-- /TOC -->

# 3. 概念

百度百科对它的定义是：对其他对象提供一种代理以控制对这个对象的访问。

代理模式更多的是一种安全层面的考虑，保护被访问对象的安全，在访问者和被访问者之间起一种中介作用。在我们具体应用中都会遇到需要记录掌控方法的执行前后的动作。

从生成方式的不通又在代理中分为静态代理、动态代理以及Cglib代理。

# 4. 静态代理

代理类在程序运行前就已经定义好，其与目标类（被代理类）的关系在程序运行前就已经确立，属于事前约定的范畴。这一点非常类似于企业与企业法律顾问间的关系，他们之间的代理关系，并不是在“官司“发生后才建立的，而是之前就确立好的一种关系。在这一点上动态代理可以理解为官司已经开始，才临时聘请熟谙法律的律师。

~~~

package xyz.wongs.weathertop.design.proxy;

/**
 * @ClassName Loginable
 * @Description 定义接口
 * @author WCNGS@QQ.COM
 * @Github <a>https://github.com/rothschil</a>
 * @date 2019/12/27 15:31
 * @Version 1.0.0
*/
public interface Loginable {
    void login();
}


package xyz.wongs.weathertop.design.proxy.stat;

import lombok.extern.slf4j.Slf4j;
import xyz.wongs.weathertop.design.proxy.Loginable;

import java.util.Random;

/**
 * @ClassName LoginService
 * @Description 登陆实现类
 * @author WCNGS@QQ.COM
 * @Github <a>https://github.com/rothschil</a>
 * @date 2019/12/27 15:32
 * @Version 1.0.0
*/
@Slf4j
public class LoginService implements Loginable {

    @Override
    public void login(){
        try {
            Thread.sleep(new Random().nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.error("登陆成功");
    }
}

package xyz.wongs.weathertop.design.proxy.stat;

import lombok.extern.slf4j.Slf4j;
import xyz.wongs.weathertop.design.proxy.Loginable;

/**
 * @ClassName LoginServiceProxy
 * @Description 实际代理类
 * @author WCNGS@QQ.COM
 * @Github <a>https://github.com/rothschil</a>
 * @date 2019/12/27 15:32
 * @Version 1.0.0
*/
@Slf4j
public class LoginServiceProxy implements Loginable {

    private Loginable loginable;

    public LoginServiceProxy() {
    }

    public LoginServiceProxy(Loginable loginable) {
        this.loginable = loginable;
    }

    @Override
    public void login() {
        long start = System.currentTimeMillis();
        log.error("start proxy : " + start);
        loginable.login();
        long end = System.currentTimeMillis();
        log.error("end proxy : " + end);
        log.error("spend all time : " + (end - start) + " ms.");
    }
}

~~~

![静态代理](https://i.loli.net/2019/12/27/UV195OfR7aHhMFW.png)

上面的一个登陆例子中，我们通过代理对象来获取我们想要的目标（LoginService），外界并不知道目标是如何实现的，无形之中保护了真正的意图。

小结一下：
静态代理对象（LoginServiceProxy）有两个特征：

+ 它内部**包含**着被代理对象（Loginable）实现接口的引用。

+ 它又实现了被代理对象（Loginable）实现的接口。

这两个特征既是特点同时也带有劣势，就是代理的对象必须提前知晓，当被代理对象发生变更，相应的代理对象也要跟着变更，还是有点不便捷。那有没有一种手段可在运行过程中动态地产生一个代理对象，非但再也不用维护代理类，更提高整体的效率。答案是有的，这种手段就是下来的动态代理。

# 5. 动态代理

 JAVA中的AOP切面就是基于动态代理来实现的，但是它用到了两种代理模式分别为jdk动态代理和cglib动态代理，当然这两种代理各有优劣，这一点我们在最后会统一总结。

## 5.1. JDK动态代理

在静态代理的模块上做一下改造，沿用接口和接口实现部分，新增一个

### 5.1.1. 实现

~~~
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


~~~

### 5.1.2. 演示结果

~~~
@Test
public void testDycProxy(){
    LoginServiceDycProxy lsdp = new LoginServiceDycProxy();
    Loginable loginable = (Loginable)lsdp.getInstall(new LoginService());
    loginable.login();
}
~~~

~~~

17:48:57.793 [main] ERROR xyz.wongs.weathertop.design.proxy.dyc.jdk.LoginServiceDycProxy - Begin 执行方法前的操作
17:49:00.594 [main] ERROR xyz.wongs.weathertop.design.proxy.LoginService - 登陆成功
17:49:00.594 [main] ERROR xyz.wongs.weathertop.design.proxy.dyc.jdk.LoginServiceDycProxy - End 执行方法后的操作
~~~

## 5.2. cglib代理

### 5.2.1. 引入依赖包

~~~
<dependency>
    <groupId>cglib</groupId>
    <artifactId>cglib</artifactId>
    <version>3.3.0</version>
</dependency>
~~~

### 5.2.2. 代理类

~~~
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

~~~

### 5.2.3. 测试

~~~

@Test
public void testDycProxyCglib(){
    LoginServiceDycProxyCglib lsdp = new LoginServiceDycProxyCglib();
    Loginable loginable = (Loginable)lsdp.getInstall(new LoginService());
    loginable.login();
}
~~~

### 5.2.4. 演示结果

~~~
18:00:53.993 [main] ERROR xyz.wongs.weathertop.design.proxy.dyc.cglib.LoginServiceDycProxyCglib - Begin 执行方法前的操作
18:00:57.787 [main] ERROR xyz.wongs.weathertop.design.proxy.LoginService - 登陆成功
18:00:57.787 [main] ERROR xyz.wongs.weathertop.design.proxy.dyc.cglib.LoginServiceDycProxyCglib - End 执行方法后的操作
~~~

## 5.3. jdk代理与cglib代理比较

- 实现机制：jdk动态代理是由java内部的反射机制来实现的，cglib动态代理底层则是借助asm来实现的；

- 效率上：反射机制在类的生成过程中比较高效，而asm机制在类生成之后的执行过程中比较高效，当然也有可以通过将asm生成的类接入缓存，这样也可以解决asm生成类过程低效问题；

- 应用上： jdk动态代理的应用需要依赖目标类均基于统一的接口，而cglib则无限制；

综上所述，我们在实际过程中基于第三方库实现的动态代理应用在综合效率上更有优势。