
# 1. 概念

    当实体间存在某种一对多关系，即当一方发生改变，依赖于它的实体将收到通知这样的事件，也就是观察者模式，俗称发布订阅，也常被人称作监听器模式。但是不论怎么称呼它只属于行为型的设计模式。

    观察者模式两个概念：被观察者（1）、观察者（N），为了更方便理解，我们将被观察者比作一个工厂，观察者比作每个消费者。所以我们在设计开发需要注意这两个对象。下来我们将利用JDK自带的方式来编写用例，同时我们还穿插多线程方式的实现。

## 1.1. 简介分析

- 使用场景：适合构建关联场景，例如一个对象（目标对象）的状态发生改变，所有的依赖对象（观察者对象）都将得到通知，进行广播通知。

- 解决方式：使用面向对象技术，可以将这种依赖关系弱化。

- 举个栗子：

> 粉丝订阅大V的博主

> 博主发布微博文章。

> 粉丝们收到博主的发布

## 1.2. 优缺点

### 1.2.1. 优点

- 观察者和被观察者是抽象耦合的。
- 建立一套触发机制。

### 1.2.2. 缺点

- 如果一个被观察者对象有很多的直接和间接的观察者的话，将所有的观察者都通知到会花费很多时间
- 如果在观察者和观察目标之间有循环依赖的话，观察目标会触发它们之间进行循环调用，可能导致系统崩溃
- 观察者模式没有相应的机制让观察者知道所观察的目标对象是怎么发生变化的，而仅仅只是知道观察目标发生了变化。    

# 2. 2.2.单线程同步实现

JAVA 中已经有了对观察者模式的支持类，这次我们直接使用。

## 2.1. 观察者

~~~
import lombok.extern.slf4j.Slf4j;

import java.util.Observable;
import java.util.Observer;

/**
 * @ClassName Fan
 * @Description 观察者
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

~~~

## 2.2. 被观察者

~~~
package xyz.wongs.weathertop.design.observer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.util.Observable;

/**
 * @ClassName Blogger
 * @Description 被观察者，对观察者对象的引用进行抽象保存
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

    public void productArticle(Blogger blogger,Article article){
        log.error("博主{}发表主题为{}的微博",blogger.getBlogName(),article.getTopicName());
        setChanged();
        notifyObservers(article);
    }
}

~~~

## 2.3. 观察的目标

~~~
package xyz.wongs.weathertop.design.observer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Article {

    private String topicName;

    private String context;
}

~~~

## 2.4. 测试用例

~~~

@Slf4j
public class AppTest {

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
}

~~~

![演示结果](https://i.loli.net/2019/12/25/T1OavCtbYEmpwzg.png)

# 3. 多线程异步实现

上一节，我们用同步方式来实现观察者模式的样例，那么在实际过程中，我们可能会碰遇到一些业务场景，当受并发度不高、系统复杂度很小以及外部资源等条件制约，应用系统并未发展到需要使用MQ来进行业务解耦，那么我们就可以考虑利用观察者模式结合异步通过多线程这样的方案来解决。

还在上一节中的代码中进行改造，为了方便演示，利用了Springboot来构造整个演示项目。

## 3.1. 被观察者

被观察者比较简单，就是加了注解。

~~~
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

~~~

## 3.2. 观察者

观察者的设计有个注意项，我们一定要多加一个Scope，多例的方式来构造实体Bean，因为要在多线程环境下使用，这一点要格外注意。再有就是一个线程池的构造，定大小即可。

~~~
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
~~~

## 3.3. 发布装置

上面完成两个核心类的编写，下来我们再封装哥分布装置，用来接收专职绑定观察者和发布，话不多说，直接Code。

~~~

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
~~~

## 3.4. 测试用例

~~~

public class TestAsy extends BaseAppTest {

    @Autowired
    BrcManager brcManager;

    @Test
    public void testAys() {
        Article article1 = new Article("富豪","怎么成为富豪");
        brcManager.brcToBlog("Sam.Von.Abram",article1);
    }
}

~~~

![测试结果](https://i.loli.net/2019/12/26/b5VYdEscXz9gvPU.png)