package com.ecommerce.demo.utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.ecommerce.demo.config.AlipayConfig;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OrderUtils {

    public static String generateOrderNumber() {
       String uuid  = UUID.randomUUID().toString();
       return  uuid;
    }

    /**
     * 支付宝验签
     * @param request
     * @return
     */
    public static boolean vetifyForm(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        request.setCharacterEncoding("utf-8");//乱码解决，这段代码在出现乱码时使用
        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String str : requestParams.keySet()) {
            String name = str;
            String[] values =  requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
        return signVerified;
    }
}
