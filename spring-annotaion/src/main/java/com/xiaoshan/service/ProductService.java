package com.xiaoshan.service;

import com.xiaoshan.dao.ProductDao;
import com.xiaoshan.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author YushanZhao
 * @Date:2018/7/17
 */
@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    @Transactional(rollbackFor = Exception.class)
    public void batchSave(List<Product> productList) throws Exception{
        for(Product product : productList){
            productDao.save(product);
            throw new Exception();
        }
    }

}
