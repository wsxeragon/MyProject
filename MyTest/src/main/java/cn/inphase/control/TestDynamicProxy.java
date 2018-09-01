package cn.inphase.control;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/*
 * 
 无论哪种方式实现动态代理,本质其实都是对字节码的修改,区别是从哪里进行切入修改字节码.
1. cglib–IOC beanFactory获取bean时,动态构建字节码,生成这个类
2. java Proxy– 获取或装载bean时,对字节码进行动态构建,装载,实例化
3. Aspectj– 代码编译时,进行织入修改字节码
4. instrumentation– 在类装载时.
 * 
*/
public class TestDynamicProxy implements InvocationHandler {

    // 这个就是我们要代理的真实对象
    private Object o;

    // 构造方法，给我们要代理的真实对象赋初值
    public TestDynamicProxy(Object subject) {
        this.o = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在代理真实对象前我们可以添加一些自己的操作
        System.out.println("代理开始");

        System.out.println("Method:" + method);

        // 当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
        method.invoke(proxy, args);

        // 在代理真实对象后我们也可以添加一些自己的操作
        System.out.println("代理结束");
        return null;
    }

    public static void main(String[] args) {
        TOM t = new TOM();

        InvocationHandler handler = new TestDynamicProxy(t);
        /*
         * 通过Proxy的newProxyInstance方法来创建我们的代理对象，我们来看看其三个参数 第一个参数
         * handler.getClass().getClassLoader()
         * ，我们这里使用handler这个类的ClassLoader对象来加载我们的代理对象
         * 第二个参数realSubject.getClass().getInterfaces()，我们这里为代理对象提供的接口是真实对象所实行的接口
         * ，表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了 第三个参数handler， 我们这里将这个代理对象关联到了上方的
         * InvocationHandler 这个对象上
         */
        TOM fakeTom = (TOM) Proxy.newProxyInstance(handler.getClass().getClassLoader(), t.getClass().getInterfaces(),
                handler);

        fakeTom.hello();
        fakeTom.bye();

    }

}

interface A {
    void hello();

    void bye();
}

class TOM implements A {

    @Override
    public void hello() {
        System.out.println("hello");
    }

    @Override
    public void bye() {
        System.out.println("bye");
    }

}
