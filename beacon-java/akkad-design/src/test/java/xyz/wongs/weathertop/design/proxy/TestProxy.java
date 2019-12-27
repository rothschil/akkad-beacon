package xyz.wongs.weathertop.design.proxy;

import org.junit.Test;
import xyz.wongs.weathertop.design.proxy.stat.LoginServiceProxy;

public class TestProxy {

    @Test
    public void testStatProxy(){
        Loginable loginable = new LoginServiceProxy(new LoginService());
        loginable.login();
    }
}
