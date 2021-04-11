package com.ecommerce.demo.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.ecommerce.demo.config.AlipayConfig;
import com.ecommerce.demo.entity.MyException;
import com.ecommerce.demo.entity.model.OrderInfo;
import com.ecommerce.demo.mapper.OrderMapper;
import com.ecommerce.demo.service.OrderService;
import com.ecommerce.demo.utils.OrderUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author 刘剑
 * @data 2021/03/24
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Override
    public String aliPay(OrderInfo orderInfo) {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest aliPayRequest = new AlipayTradePagePayRequest();
       // aliPayRequest.setReturnUrl(AlipayConfig.return_url);
        //aliPayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，后台可以写一个工具类生成一个订单号，必填

        String orderNumber = orderInfo.getOrderNumber();
        //付款金额，从前台获取，必填
        float sellPrice =  orderInfo.getSellPrice();
        //订单名称，必填
        String orderName = orderInfo.getOrderName();
        aliPayRequest.setBizContent("{\"out_trade_no\":\"" + orderNumber + "\","
                + "\"total_amount\":" + sellPrice + ","
                + "\"subject\":\"" + orderName + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String result = null;
        try {
            result = alipayClient.pageExecute(aliPayRequest).getBody();
        } catch (AlipayApiException e) {
         throw  new MyException("支付失败","100");
        }
        return result;
    }

    /**
     * 创建订单
     * @param orderInfo
     * @throws Exception
     */
    @Override
    public void createOrder(OrderInfo orderInfo) throws Exception {
          orderInfo.setOrderNumber(OrderUtils.generateOrderNumber());
          try {
              orderMapper.orderCreate(orderInfo);

          }catch (Exception e){
              throw new Exception(e);
          }
    }
}
