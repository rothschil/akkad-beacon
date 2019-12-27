package xyz.wongs.weathertop.design.observer.asy;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.wongs.weathertop.design.observer.Article;

import java.util.Observable;

/**
 * @ClassName Blogger
 * @Description 被观察者，对观察者对象的引用进行抽象保存，发布者，异步方式
 * @author WCNGS@QQ.COM
 * @Github <a>https://github.com/rothschil</a>
 * @date 2019/12/25 16:36
 * @Version 1.0.0
*/
@Slf4j
@Component
public class BloggerManager extends Observable {

    @Getter
    private String blogName;

    public BloggerManager(){
    }

    public BloggerManager(String blogName) {
        this.blogName = blogName;
    }

    public void productArticle(Article article){
        log.error("博主{}发表主题为{}的微博",blogName,article.getTopicName());
        setChanged();
        notifyObservers(article);
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }
}
