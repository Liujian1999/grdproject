package com.ecommerce.demo.service;

import com.ecommerce.demo.entity.model.OrderInfo;

public interface OrderService {

    String aliPay(OrderInfo orderInfo);

    void createOrder(OrderInfo orderInfo) throws Exception;
}
