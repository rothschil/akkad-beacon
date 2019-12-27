package xyz.wongs.weathertop.design.observer.asy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import xyz.wongs.weathertop.design.observer.Article;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName Fan
 * @Description 观察者，即订阅者
 * @author WCNGS@QQ.COM
 * @Github <a>https://github.com/rothschil</a>
 * @date 2019/12/25 16:36
 * @Version 1.0.0
*/
@Slf4j
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FanManager implements Observer {

    private String fanName;

    /**
     * 线程池
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Override
    public void update(Observable o, Object arg) {
        executorService.execute(()->{
            BloggerManager blogger=(BloggerManager)o;
            Article article=(Article)arg;
            log.error("线程{}，粉丝{}，看见{}的博主发表主题为{}的微博，内容为{}",Thread.currentThread().getName(),fanName,blogger.getBlogName(),article.getTopicName(),article.getContext());
        });
    }

    public FanManager() {

    }
    public FanManager(String fanName) {
        this.fanName = fanName;
    }

    public void setFanName(String fanName) {
        this.fanName = fanName;
    }
}
