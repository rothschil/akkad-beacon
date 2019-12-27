package xyz.wongs.weathertop.design.adapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Adaptee {

    public int v220(){
        log.error("标准电压输出");
        return 220;
    }
}
