package com.xiaoshan.erp.controller;


import com.xiaoshan.erp.util.Constant;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author YushanZhao
 * @Date:2018/7/26
 */
public class Test {

    Logger logger = LoggerFactory.getLogger(Test.class);

   @org.junit.Test
   public void testPassword(){
      // String salt = "!@#$%^&*()_+";
       String num = "222";
       String password = DigestUtils.md5Hex(num + Constant.DEFAULT_SALT);
       System.out.println(password);
   }



}
