package com.xiaoshan.prox.cglib;

/**
 * @author YushanZhao
 * @Date:2018/7/16
 */
public class ProxySale extends Sale {

    @Override
    public void salePc() {
        System.out.println("前面的服务AAAAAA");
        super.salePc();
        System.out.println("后面的服务VVVVVV");
    }
}
