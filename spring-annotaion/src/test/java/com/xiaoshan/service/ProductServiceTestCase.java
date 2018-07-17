package com.xiaoshan.service;

import com.xiaoshan.BaseTestCase;
import com.xiaoshan.entity.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @author YushanZhao
 * @Date:2018/7/17
 */
public class ProductServiceTestCase extends BaseTestCase {

    @Autowired
    private ProductService productService;

    @Test
    public void testBatchSave() throws Exception{
        Product product = new Product();
        product.setProductName("Iphone 1");
        product.setProductInventory(1);

        Product product1 = new Product();
        product1.setProductName(null);
        product1.setProductInventory(2);

        List<Product> productList = Arrays.asList(product, product1);
        productService.batchSave(productList);

    }

}
