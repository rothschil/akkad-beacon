
# 1. 概念

百度百科对它的定义是：对其他对象提供一种代理以控制对这个对象的访问。

代理模式更多的是一种安全层面的考虑，保护被访问对象的安全，在访问者和被访问者之间起一种中介作用。在我们具体应用中都会遇到需要记录掌控方法的执行前后的动作。

从生成方式的不通又在代理中分为静态代理、动态代理以及Cglib代理。

# 2. 静态代理

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

# 3. 动态代理

