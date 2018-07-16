package com.xiaoshan.prox;

/**
 * @author YushanZhao
 * @Date:2018/7/15
 */
public class Prox implements Sale {

    //private Lenovo lenovo = new Lenovo();

    private Sale sale;
    public Prox(Sale sale){
        this.sale = sale;
    }

    @Override
    public void salePc() {
        System.out.println("++++++++++++++++++++++");
        sale.salePc();
        System.out.println("----------------------");
    }

    @Override
    public int salePrice() {
        return 0;
    }
}
