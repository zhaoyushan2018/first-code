package com.xiaoshan.erp.service.impl;

import com.xiaoshan.erp.entity.Car;
import com.xiaoshan.erp.entity.CarExample;
import com.xiaoshan.erp.entity.Customer;
import com.xiaoshan.erp.entity.CustomerExample;
import com.xiaoshan.erp.exception.ServiceException;
import com.xiaoshan.erp.mapper.CarMapper;
import com.xiaoshan.erp.mapper.CustomerMapper;
import com.xiaoshan.erp.service.CarService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YushanZhao
 * @Date:2018/8/2
 */
@Service
public class CarServiceImpl implements CarService {

    Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public void addCarAndCustomer(Car car, Customer customer) {

        //客户身份证时唯一的, 根据身份证号码查询该客户是否存在
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andIdCardEqualTo(customer.getIdCard());
        List<Customer> customerList = customerMapper.selectByExample(customerExample);

        //如果客户不存在则添加用户信息 获得自动生成的主键值, 如果存在则直接获取id
        Integer customerId = null;
        if(customerList == null || customerList.size() == 0){
            customerMapper.insertSelective(customer);
            customerId = customer.getId();

            logger.info("添加客户信息: {}",customer);
        } else {
            //因为客户身份证号码是唯一的, 如果存在只会有一个(所以回去集合的第一个对象 然后获取Id)
            customerId = customerList.get(0).getId();
        }

        //校验车辆是否存在
        //不存在则添加车辆信息
        car.setCustomerId(customerId);
        carMapper.insertSelective(car);

        logger.info("添加车辆信息: {}",car);
    }

    /**
     * 验证车牌号码是否重复
     *
     * @param licenceNo 车牌号码
     * @return
     */
    @Override
    public boolean checkLicenceNo(String licenceNo) {
        CarExample carExample = new CarExample();
        carExample.createCriteria().andLicenceNoEqualTo(licenceNo);
        List<Car> carList = carMapper.selectByExample(carExample);

        if(carList != null && carList.size() > 0){
            return false;
        }

        return true;
    }

    /**
     * 验证客户身份证号码是否重复
     *
     * @param idCard 客户身份证号码
     * @return
     */
    @Override
    public boolean checkIdCard(String idCard) {
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andIdCardEqualTo(idCard);
        List<Customer> customerList = customerMapper.selectByExample(customerExample);

        if(customerList != null && customerList.size() > 0){
            return false;
        }
        return true;
    }

    /**
     * 根据车牌号码查找汽车和车主
     *
     * @param licenceNo 车牌号码
     * @return
     */
    @Override
    public Car findCarAndCustomerByLicenceNo(String licenceNo) throws ServiceException{
        if(StringUtils.isEmpty(licenceNo)){
            throw new ServiceException("参数异常...");
        }

        Car car = carMapper.findCarAndCustomerByLicenceNo(licenceNo);

        return car;
    }


}
