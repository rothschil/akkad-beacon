package xyz.wongs.weathertop.design.observer.sync;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import xyz.wongs.weathertop.design.observer.Article;

import java.util.Observable;

/**
 * @ClassName Blogger
 * @Description 被观察者，对观察者对象的引用进行抽象保存，发布者
 * @author WCNGS@QQ.COM
 * @Github <a>https://github.com/rothschil</a>
 * @date 2019/12/25 16:36
 * @Version 1.0.0
*/
@Slf4j
public class Blogger extends Observable {

    @Getter
    private String blogName;

    public Blogger(String blogName) {
        this.blogName = blogName;
    }

    public void productArticle(Blogger blogger, Article article){
        log.error("博主{}发表主题为{}的微博",blogger.getBlogName(),article.getTopicName());
        setChanged();
        notifyObservers(article);
    }
}
