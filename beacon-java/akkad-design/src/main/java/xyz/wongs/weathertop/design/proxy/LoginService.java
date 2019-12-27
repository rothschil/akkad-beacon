package xyz.wongs.weathertop.design.proxy;

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
