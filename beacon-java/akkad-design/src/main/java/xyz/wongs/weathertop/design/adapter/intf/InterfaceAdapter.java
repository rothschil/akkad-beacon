package xyz.wongs.weathertop.design.adapter.intf;

import lombok.extern.slf4j.Slf4j;
import xyz.wongs.weathertop.design.adapter.Target;

@Slf4j
public class InterfaceAdapter implements Target,Adapteeable {

    @Override
    public int v220() {
        log.error("标准电压输出");
        return 220;
    }

    @Override
    public int v5() {
        int v5 = v220()/44;
        log.error("电压转换成功 {}",v5);
        return v5;
    }

    @Override
    public int v100() {
        int v110 = v220()/2;
        log.error("电压转换成功 {}",v110);
        return v110;
    }
}
