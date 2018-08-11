package com.xiaoshan.erp.service;

import com.xiaoshan.erp.entity.Car;
import com.xiaoshan.erp.entity.Customer;
import com.xiaoshan.erp.exception.ServiceException;

/**
 * @author YushanZhao
 * @Date:2018/8/2
 */
public interface CarService {

    /**
     * 新增客户与车辆的关联信息
     * @param car 车辆信息
     * @param customer 客户信息
     */
    void addCarAndCustomer(Car car, Customer customer);

    /**
     *  验证车牌号码是否重复
     * @param licenceNo 车牌号码
     * @return
     */
    boolean checkLicenceNo(String licenceNo);

    /**
     * 验证客户身份证号码是否重复
     * @param idCard 客户身份证号码
     * @return
     */
    boolean checkIdCard(String idCard);

    /**
     *  根据车牌号码查找汽车和车主
     * @param licenceNo 车牌号码
     * @return
     */
    Car findCarAndCustomerByLicenceNo(String licenceNo) throws ServiceException;
}
