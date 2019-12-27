package xyz.wongs.weathertop.design.adapter;

import org.junit.Test;
import xyz.wongs.weathertop.design.adapter.clazz.ClazzAdapter;
import xyz.wongs.weathertop.design.adapter.intf.InterfaceAdapter;
import xyz.wongs.weathertop.design.adapter.obj.ObjAdapter;

public class TestClazzAdapter {

    @Test
    public void testClassAdapter(){
        ClazzAdapter clazzAdapter = new ClazzAdapter();
        clazzAdapter.v5();
        clazzAdapter.v100();
        clazzAdapter.v220();
    }

    @Test
    public void testObjectAdapter(){
        ObjAdapter objAdapter = new ObjAdapter(new Adaptee());
        objAdapter.v5();
        objAdapter.v100();
        objAdapter.v220();
    }

    @Test
    public void testIntfAdapter(){
        InterfaceAdapter intfAd = new InterfaceAdapter();
        intfAd.v5();
        intfAd.v100();
        intfAd.v220();
    }
}
