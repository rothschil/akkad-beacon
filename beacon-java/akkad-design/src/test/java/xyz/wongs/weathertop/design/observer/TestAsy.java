package xyz.wongs.weathertop.design.observer;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.wongs.weathertop.BaseAppTest;
import xyz.wongs.weathertop.design.observer.asy.BrcManager;
import xyz.wongs.weathertop.design.observer.asy.WriterManager;
import xyz.wongs.weathertop.design.observer.sync.Blogger;
import xyz.wongs.weathertop.design.observer.sync.Fan;

public class TestAsy extends BaseAppTest {

    @Autowired
    BrcManager brcManager;


    @Test
    public void testObserver() {
        Blogger blogger = new Blogger("Sam.Von.Abram");
        Fan fan1 = new Fan("张三");
        Fan fan2 = new Fan("李四");
        Fan fan3 = new Fan("朱元昌");
        blogger.addObserver(fan1);
        blogger.addObserver(fan2);
        blogger.addObserver(fan3);

        Article article1 = new Article("富豪","怎么成为富豪");

        blogger.productArticle(blogger,article1);

    }

    @Test
    public void testAys() {
        Article article1 = new Article("富豪","怎么成为富豪");
        brcManager.brcToBlog("Sam.Von.Abram",article1);
    }
}
