package xyz.wongs.weathertop.design.bridge;

/**
 * @author WCNGS@QQ.COM
 * @ClassName Abstraction$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/8$ 15:55$
 * @Version 1.0.0
 */
abstract public class Abstraction {
    protected Implementor implementor;

    protected Abstraction(Implementor implementor) {
        this.implementor = implementor;
    }
    public abstract void operation();
}

interface Implementor {
    public void operation();
}

class ImplementorA implements Implementor {
    public void operation(){
        System.out.println(" A -- 具体实现化(Concrete Implementor)角色被访问");
    }
}

class ImplementorB implements Implementor {
    public void operation(){
        System.out.println(" B -- 具体实现化(Concrete Implementor)角色被访问");
    }
}

class RefinedAbstraction extends Abstraction{

    protected RefinedAbstraction(Implementor implementor) {
        super(implementor);
    }

    @Override
    public void operation() {
        System.out.println("扩展抽象化(Refined Abstraction)角色被访问");
        implementor.operation();
    }
}

class BridgeTest {
    public static void main(String[] args) {
        Implementor imple = new ImplementorA();
        Abstraction abs = new RefinedAbstraction(imple);
        abs.operation();
    }
}