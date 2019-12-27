
和以往一样，先搞清楚概念，这在我们学习过程中是非常重要的一环，只有弄清楚它的基本概念以及适用场景，才能帮助我们学习理知识点，才能知道什么时候用、怎么用。

# 1. 概念

适配这两个词，顾名思义，核心就是转换调配，比如生活中大家所用手机，在手机需要充电的时候，面对民用电交流电220V和我们手机实际只需要十几瓦电压电流输入有很大差别，而且手机与插座的对接物理形态也不一致，这时候手机适配插头的作用就凸显出来，它为我们做电压电流和插座物理形态的转换。

通过案例说明，我们总结出既然适配就需要涉及三个角色：

- 适配者（Adaptee）：抽象类，可以理解为我们现有的组件和资源，对照着例子中的物件，它就是220V电源插座

- 适配器（Adapter）：抽象类，连接目标和适配者的中间对象，相当于手机电源插头转换器

- 目标（Target）：一个接口，我们期待的结果。

适配器模式是我们JAVA中最常用的一种结构模式，它包括3种形式：类适配器模式、对象适配器模式、接口适配器模式（或又称作缺省适配器模式）。由适配器模式引出的其他设计还有好多种，这些模式一个章节说不完，往后章节中有机会，只能挑着来说。

下面我们将重点阐述这几个适配器。

# 2. 类适配器模式

类适配器的核心就是继承。

注：因为我用到lombok注解，所以在实际类图中多一层Slf4j实现，各位看官可根据实际情况自行调整，后面样例都如此，故就不在过多赘述。

![类适配器实现](https://i.loli.net/2019/12/26/o6ukpi4OfFWelBy.png)

**ClazzAdapter中没有eating()方法，为了可以更好的对外提供服务功能，我们利用ClazzAdapter适配器将Target和Adaptee衔接整合，其中ClazzAdapter则继承Adaptee。**

## 2.1. 适配者（Adaptee）

~~~
@Slf4j
public class Adaptee {

    public int v220(){
        log.error("标准电压输出");
        return 220;
    }
}
~~~

## 2.2. 目标（Target）

~~~
public interface Target {

    int v220();

    int v5();

    int v100();
}
~~~

## 2.3. 适配器（Adapter）

~~~
@Slf4j
public class ClazzAdapter extends Adaptee implements Target {

    @Override
    public int v5() {
        int v5 = super.v220()/44;
        log.error("电压转换成功 {}",v5);
        return v5;
    }

    @Override
    public int v100() {
        int v110 = super.v220()/44;
        log.error("电压转换成功 {}",v110);
        return v110;
    }
}
~~~

## 2.4. 测试样例

~~~
@Test
public void testClassAdapter(){
    ClazzAdapter clazzAdapter = new ClazzAdapter();
    clazzAdapter.v5();
    clazzAdapter.v100();
    clazzAdapter.v220();
}
~~~

![类适配演示结果](https://i.loli.net/2019/12/27/kcoDESAXjvGIVhq.png)

# 3. 对象适配器模式

对象适配器模式的特征就是将适配者<Adaptee>注入适配器<ObjAdapter>中，结构层次相当于一种组合。

![依赖继承关系](https://i.loli.net/2019/12/27/AuskdIqvyw6BeVz.png)

~~~
@Slf4j
public class ObjAdapter implements Target {

    private Adaptee adaptee;

    public ObjAdapter() {
    }


    public ObjAdapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public int v220() {
        log.error("对象适配器模式 标准电压");
        return adaptee.v220();
    }

    @Override
    public int v5() {
        int v5 = v220()/44;
        log.error("对象适配器模式；电压转换成功 {}",v5);
        return v5;
    }

    @Override
    public int v100() {
        int v110 = v220()/2;
        log.error("对象适配器模式；电压转换成功 {}",v110);
        return v110;
    }
}
~~~

测试过程中我们利用new一个适配者<Adaptee>作为适配器的构造函数的参数，注入进去。

![对象适配器模式](https://i.loli.net/2019/12/27/JXfOseMYFBc5udt.png)

# 4. 类适配器与对象适配器比较

类适配器实现是基于JAVA的继承，直接继承适配者，这样就存在无法对适配者的子类进行适配，也就是说我们无法使用适配者的子类。

对象适配器基于组合的方式，所以它可以适配适配者所有的子类以及派生类，另一方面对象适配器在扩展行为的过程中非常方便。

所以在日常开发中，我们基于组合/聚合优于继承的原则，可使用对象适配器。当然实际情况可能会比较复杂，我们也要具体问题具体分析，选择一种最适合的方式才是王道

# 5. 接口适配器模式

也称为缺省适配模式，假如有这样的场景，有一个或者几个很大的接口，N个方法，实际上并不需要都实现该接口的所有方法，那么我们抽象的个中间类，实现大接口，将方法实现都置空，而后再用其他类继承抽象类，用哪些方法，就覆写哪些方法。

代码就不贴。

# 6. 总结

适配器这样的设计是为了提高代码复用性和代码有更好的拓展，我们在实际使用中要更多的立足现实场景，也不应该过分追求适配。 