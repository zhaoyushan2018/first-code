package com.xiaoshan.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;

/**
 * @author YushanZhao
 * @Date:2018/7/23
 */
@RequestMapping("/file")
@Controller
public class FileUploadController {

    @GetMapping("/upload")
    public String fileUpload(){
        return "file/upload";
    }

    @PostMapping("/upload")
    public String fileUpload(MultipartFile file, RedirectAttributes attributes){
        //上传控件的name属性值
        System.out.println(file.getName());
        // 文件大小
        System.out.println(file.getSize());
        //文件原始名称
        System.out.println(file.getOriginalFilename());
        // 上传文件是否为空
        System.out.println(file.isEmpty());
        // MIME类型
        System.out.println(file.getContentType());

       if(!file.isEmpty()){
           try {
               //文件上传
               File director = new File("d:/temp/img/");
               if (!director.exists()) {
                   director.mkdirs();
               }

               OutputStream outputStream = new FileOutputStream(new File(director, file.getOriginalFilename()));
               //获得上传文件的二进制流
               InputStream inputStream = file.getInputStream();
               IOUtils.copy(inputStream, outputStream);

               outputStream.flush();
               outputStream.close();
               inputStream.close();

           }catch (Exception e){
               e.printStackTrace();
           }
       } else {
           System.out.println("文件上传不能为空");
           attributes.addFlashAttribute("message","文件上传不能为空");
       }



        return "redirect:/file/upload";
    }
}
