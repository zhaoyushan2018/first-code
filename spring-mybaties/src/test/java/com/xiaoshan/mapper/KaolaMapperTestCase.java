package com.xiaoshan.mapper;

import com.xiaoshan.Application;
import com.xiaoshan.BaseTestCase;
import com.xiaoshan.entity.Kaola;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * @author YushanZhao
 * @Date:2018/7/19
 */
public class KaolaMapperTestCase extends BaseTestCase {

    @Autowired
    private KaolaMapper kaolaMapper;

    @Test
    public void testFindKaolaById(){
        Kaola kaola = kaolaMapper.selectByPrimaryKey(3000);
        System.out.println(kaola);
    }

    @Test
    public void testSaveKaola(){
        Kaola kaola = new Kaola();
        kaola.setProductName("Iphone 1");
        kaola.setPrice(new BigDecimal(8888));
        kaola.setMarketPrice(new BigDecimal(6666));
        kaola.setPlace("China");
        kaola.setCommentNum(3333);
        kaola.setTypeId(33);

        int count = kaolaMapper.insert(kaola);
        int id = kaola.getId();
        System.out.println("count:" + count + "\nid:" + id);
    }
















}
