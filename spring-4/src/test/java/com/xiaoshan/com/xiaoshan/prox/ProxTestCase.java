package com.xiaoshan.com.xiaoshan.prox;

import com.xiaoshan.prox.Dell;
import com.xiaoshan.prox.Lenovo;
import com.xiaoshan.prox.Prox;
import com.xiaoshan.prox.Sale;
import com.xiaoshan.prox.jdk.MyInvocationHandler;
import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.Properties;

/**
 * @author YushanZhao
 * @Date:2018/7/15
 */
public class ProxTestCase {

    private Dell dell = new Dell();
    private Lenovo lenovo = new Lenovo();
    private Prox prox = new Prox(lenovo);

    @Test
    public void testSalePc(){
        prox.salePc();
    }

  /*  private Prox prox = new Prox();

    @Test
    public void testSalePc(){
            prox.salePc();
    }*/


  @Test
    public void testDynamicProxy(){

        Lenovo lenovo = new Lenovo();
        Dell dell = new Dell();

        //创建MyInvocationHandler对象, 传入目标对象
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(dell);

        //动态产生代理类对象
        Sale sale = (Sale) Proxy.newProxyInstance(lenovo.getClass().getClassLoader(),
                lenovo.getClass().getInterfaces(),
                myInvocationHandler);
        sale.salePc();
    }





}
