package com.xiaoshan.erp.controller.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author YushanZhao
 * @Date:2018/7/24
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "资源未找到")
public class NotFoundException extends RuntimeException {
}
