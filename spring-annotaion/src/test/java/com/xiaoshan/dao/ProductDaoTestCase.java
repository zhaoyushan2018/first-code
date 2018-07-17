package com.xiaoshan.dao;

import com.xiaoshan.BaseTestCase;
import com.xiaoshan.entity.Product;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author YushanZhao
 * @Date:2018/7/17
 */
public class ProductDaoTestCase extends BaseTestCase {

    @Autowired
    private ProductDao productDao;

    @Test
    public void testSave() {
        Product product = new Product();
        product.setProductName("137macPro");
        product.setProductInventory(88);

        productDao.save(product);
    }


    @Test
    public void testFindById() {
        Product product = productDao.findById(3);
        System.out.println(product);
        Assert.assertNotNull(product);
    }


    @Test
    public void testFindAll() {
        List<Product> productList = productDao.findAll();
        for (Product product : productList){
            System.out.println("product:" + product);
        }
    }

    @Test
    public void testCount() {
        int count = productDao.count();
        System.out.println("count:" + count);
        Assert.assertEquals(count, 4);
    }

    @Test
    public void findListMap() {
        List<Map<String, Object>> mapList = productDao.findListMap();
        for(Map<String, Object> maps : mapList){
            for(Map.Entry<String, Object> product : maps.entrySet()){
                System.out.println(product.getKey());
                System.out.println(product.getValue());
                System.out.println(product.getClass());
            }
        }
    }

    @Test
    public void findByName() {
        Product product = productDao.findByName("戴尔");
        System.out.println("product:" + product);
        Assert.assertNotNull(product);
    }
}