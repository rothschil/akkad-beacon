package xyz.wongs.weathertop.design.observer.asy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import xyz.wongs.weathertop.base.utils.SpringContextHolder;
import xyz.wongs.weathertop.design.observer.Article;

@Component
public class BrcManager {

    @Autowired
    private BloggerManager bloggerManager;
//
//    @Autowired
//    private WriterManager writerManager;

    @Async
    public void brcToBlog(String blogName, Article article){
        bloggerManager.setBlogName(blogName);
        FanManager fanManager;
        WriterManager writerManager;
        for(int i=0;i<6;i++){
            fanManager = SpringContextHolder.getBean("fanManager");
            fanManager.setFanName("粉丝"+i);
            bloggerManager.addObserver(fanManager);
        }
        bloggerManager.productArticle(article);
    }
}
