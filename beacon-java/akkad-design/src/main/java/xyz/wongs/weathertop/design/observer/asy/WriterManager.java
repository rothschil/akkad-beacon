package xyz.wongs.weathertop.design.observer.asy;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import xyz.wongs.weathertop.design.observer.Article;

import java.util.Observable;

@Slf4j
@Component
public class WriterManager extends Observable {
    @Getter
    private String writerName;

    public WriterManager(){
    }
    public WriterManager(String writerName) {
        this.writerName = writerName;
    }

    public void productArticle(WriterManager writerManager, Article article){
        log.error("作家{}发表主题为{}的文章",writerManager.writerName,article.getTopicName());
        setChanged();
        notifyObservers(article);
    }
}
