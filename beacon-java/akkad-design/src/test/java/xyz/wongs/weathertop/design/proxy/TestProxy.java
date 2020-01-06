package xyz.wongs.weathertop.design.proxy;

import org.junit.Test;
import xyz.wongs.weathertop.design.proxy.dyc.cglib.LoginServiceDycProxyCglib;
import xyz.wongs.weathertop.design.proxy.dyc.jdk.LoginServiceDycProxy;
import xyz.wongs.weathertop.design.proxy.stat.LoginServiceProxy;

public class TestProxy {

    @Test
    public void testStatProxy(){
        Loginable loginable = new LoginServiceProxy(new LoginService());
        loginable.login();
    }

    @Test
    public void testDycProxy(){
        LoginServiceDycProxy lsdp = new LoginServiceDycProxy();
        Loginable loginable = (Loginable)lsdp.getInstall(new LoginService());
        loginable.login();
    }

    @Test
    public void testDycProxyCglib(){
        LoginServiceDycProxyCglib lsdp = new LoginServiceDycProxyCglib();
        Loginable loginable = (Loginable)lsdp.getInstall(new LoginService());
        loginable.login();
    }
}
