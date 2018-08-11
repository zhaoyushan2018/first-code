package com.xiaoshan.erp.controller;

/**
 * @author YushanZhao
 * @Date:2018/8/1
 */
public class TestOne {

    public static void main(String[] args) {

        //公鸡(cock)5元一只，母鸡(hen)3元一只，小鸡(chicken)1元三只，问100元怎样可以买100鸡？
        int cock = 0;
        int hen = 0;
        int chicken = 0;
        for(cock = 0; cock < 20; cock ++){
            for(hen = 0; hen <= 33; hen++){
                chicken = 100 - cock - hen;
                int p = chicken % 3;
                if(((5*cock+3*hen+chicken/3)==100)&&(p==0)){
                    System.out.print("    可以买公鸡的只数："+cock);
                    System.out.print("    可以买母鸡的只数："+hen);
                    System.out.print("    可以买小鸡的只数："+chicken);
                    System.out.println("\n");
                }

            }
        }

    }

}
