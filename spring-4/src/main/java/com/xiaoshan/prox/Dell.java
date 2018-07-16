package com.xiaoshan.prox;

import org.junit.Test;

/**
 * @author YushanZhao
 * @Date:2018/7/15
 */
public class Dell implements Sale {

    @Override
    public void salePc() {

        System.out.println("del戴尔厂商销售电脑一台");
    }

    @Override
    public int salePrice(){
        int a = 10 / 0 ;
        System.out.println("售价3000元");
        return 3000;
    }

}
