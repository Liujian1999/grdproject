package com.ecommerce.demo.service;

import com.ecommerce.demo.entity.model.CommodityInfo;
import com.ecommerce.demo.entity.model.OrderInfo;

import java.util.List;

public interface OrderService {

    String aliPay(OrderInfo orderInfo);

    void createOrder(OrderInfo orderInfo) throws Exception;

    List<CommodityInfo> getPayList(List<Integer> commodityIdList);

    List<OrderInfo> findOrderList();

    void setOrderState(String orderNumber);
}
