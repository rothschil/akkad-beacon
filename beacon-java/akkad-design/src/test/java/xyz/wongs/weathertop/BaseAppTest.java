package xyz.wongs.weathertop;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xyz.wongs.weathertop.design.observer.Article;
import xyz.wongs.weathertop.design.observer.sync.Blogger;
import xyz.wongs.weathertop.design.observer.sync.Fan;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AkkadDesignApplicatin.class)
public abstract class BaseAppTest {


}
