package com.xiaoshan.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

/**
 * @author YushanZhao
 * @Date:2018/7/14
 */
public class BaseServiceTestCase {

    @Test
    public void show(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BaseService baseService = (BaseService) context.getBean("baseService");

        System.out.println(baseService.getId());
        System.out.println(baseService.getName());
        System.out.println(baseService.getScore());

        List<String> stringList = baseService.getStringList();
        for (String string : stringList){
            System.out.println(string);
        }

        Set<Integer> numSets = baseService.getNumSets();
        for(Integer num : numSets){
            System.out.println(num);
        }

        Map<String, String> maps = baseService.getMaps();
        for (Map.Entry<String, String> map : maps.entrySet()){
            System.out.println(map.getKey());
            System.out.println(map.getValue());
        }

        Properties properties = baseService.getProperties();
        Enumeration<Object> keys = properties.keys();

        while(keys.hasMoreElements()){
            Object key = keys.nextElement();
            Object value = properties.get(key);
            System.out.println(key);
            System.out.println(value);
        }

    }

}
