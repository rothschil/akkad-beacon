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
