package xyz.wongs.weathertop.design.decorator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductManager implements Person {

    @Override
    public void run() {
        log.error("乘坐滴滴优享专车");
    }

    @Override
    public void sleep() {
        log.error("为项目进展飞快，每天加班到很晚，睡眠时间不足");
    }

    @Override
    public void eat() {
        log.error("为项目进展飞快，也很少按时吃饭");
    }

    @Override
    public void drink() {
        log.error("为项目进展飞快，也很少喝水");
    }
}
