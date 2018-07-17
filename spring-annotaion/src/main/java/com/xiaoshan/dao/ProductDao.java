package com.xiaoshan.dao;

import com.xiaoshan.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author YushanZhao
 * @Date:2018/7/17
 */
//添加到spring里面
@Repository
public class ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 存储product对象
     * @param product
     */
    public void save(Product product){
        String sql = "insert into product (product_name, product_inventory) values (?,?)";
        jdbcTemplate.update(sql, product.getProductName(), product.getProductInventory());
    }

    /*public Product findById(Integer id){
        String sql = "select * from product where id = ?";
        return jdbcTemplate.queryForObject(sql, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setProductName(rs.getString("product_name"));
                product.setProductInventory(rs.getInt("product_inventory"));
                return product;
            }
        }, id);
    }*/

    //根据id查找对应product对象返回

    public Product findById(Integer id){
        String sql = "select * from product where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Product.class), id);
    }

    public Product findByName(String productName){
        String sql = "select * from product where product_name = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Product.class), productName);
    }

    public List<Product> findAll(){
        String sql = "select * from product";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    public int count(){
        String sql = "select count(*) from product";
        return jdbcTemplate.queryForObject(sql, new SingleColumnRowMapper<Long>()).intValue();
    }

    public List<Map<String, Object>> findListMap(){
        String sql = "select * from product";
        return jdbcTemplate.query(sql, new ColumnMapRowMapper());
    }

}
