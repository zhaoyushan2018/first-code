package com.xiaoshan.erp.controller;

import com.xiaoshan.erp.dto.ResponseBean;
import com.xiaoshan.erp.entity.Car;
import com.xiaoshan.erp.entity.Customer;
import com.xiaoshan.erp.exception.ServiceException;
import com.xiaoshan.erp.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author YushanZhao
 * @Date:2018/8/2
 */
@Controller
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

   /* @PostMapping("/new")
    public String carNew(Car car, Customer customer){
        //System.out.println("新增car: " + car);
        //System.out.println("新增customer: " + customer);

        //新增(客户车辆)信息
        carService.addCarAndCustomer(car, customer);

        return "redirect:/order/list";
    }*/
   @PostMapping("/new")
   @ResponseBody
   public ResponseBean carNew(Car car, Customer customer){
       //新增(客户车辆)信息
       carService.addCarAndCustomer(car, customer);
       car.setCustomer(customer);

       return ResponseBean.success(car);
   }


    /**
     *  验证车牌号码是否重复
     * @return
     */
    @GetMapping("/check/licenceNo")
    @ResponseBody
    public boolean checkLicenceNo(String licenceNo){
        System.out.println("要验证的车牌号码是: " + licenceNo);

        boolean result = carService.checkLicenceNo(licenceNo);

        return result;
    }

    /**
     *  验证客户身份证号码是否重复
     * @return
     */
    @GetMapping("/check/idCard")
    @ResponseBody
    public boolean checkidCard(String idCard){
        System.out.println("要验证的客户身份证号码是: " + idCard);

        boolean result = carService.checkIdCard(idCard);

        return result;
    }

    @GetMapping("/check")
    @ResponseBody
    public ResponseBean check(String licenceNo){
        System.out.println("要验证的车牌号码是------>: " + licenceNo);

        Car car = null;
        try {
            car = carService.findCarAndCustomerByLicenceNo(licenceNo);
        } catch (ServiceException e) {
            return ResponseBean.error(e.getMessage());
        }

        if(car != null){
            return ResponseBean.success(car);
        }

        return ResponseBean.error("暂未录入...");
    }


    /* - - - - - - - -  - - - - - - - - - - - - - - - - - - - - -  - - - - - - - - - - - - -  - - - - - - - -  - - - - - - - - - - - - -  - - - - - - - -  - - - - - - - - - - - - - */

/*
    @GetMapping("")
    public String checkLicenceNo(){

        return null;
    }
*/


}
