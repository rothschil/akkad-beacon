package xyz.wongs.weathertop.design.observer.sync;

import lombok.extern.slf4j.Slf4j;
import xyz.wongs.weathertop.design.observer.Article;

import java.util.Observable;
import java.util.Observer;

/**
 * @ClassName Fan
 * @Description 观察者，即订阅者
 * @author WCNGS@QQ.COM
 * @Github <a>https://github.com/rothschil</a>
 * @date 2019/12/25 16:36
 * @Version 1.0.0
*/
@Slf4j
public class Fan implements Observer {

    private String fanName;


    @Override
    public void update(Observable o, Object arg) {
        Blogger blogger=(Blogger)o;
        Article article=(Article)arg;
        log.error("粉丝{}，看见{}的博主发表主题为{}的微博，内容为{}",fanName,blogger.getBlogName(),article.getTopicName(),article.getContext());
    }

    public Fan(String fanName) {
        this.fanName = fanName;
    }
}
