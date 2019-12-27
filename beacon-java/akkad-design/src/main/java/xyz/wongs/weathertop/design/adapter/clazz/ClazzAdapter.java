package xyz.wongs.weathertop.design.adapter.clazz;

import lombok.extern.slf4j.Slf4j;
import xyz.wongs.weathertop.design.adapter.Adaptee;
import xyz.wongs.weathertop.design.adapter.Target;

@Slf4j
public class ClazzAdapter extends Adaptee implements Target {

    @Override
    public int v5() {
        int v5 = super.v220()/44;
        log.error("电压转换成功 {}",v5);
        return v5;
    }

    @Override
    public int v100() {
        int v110 = super.v220()/2;
        log.error("电压转换成功 {}",v110);
        return v110;
    }
}
