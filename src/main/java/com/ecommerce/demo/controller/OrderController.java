package com.ecommerce.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;

import com.ecommerce.demo.entity.model.CommodityInfo;
import com.ecommerce.demo.entity.model.OrderInfo;
import com.ecommerce.demo.service.OrderService;
import com.ecommerce.demo.utils.OrderUtils;
import com.ecommerce.demo.utils.ResponseJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("order")
public class OrderController{
    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    public JSONObject orderCreate(@RequestBody OrderInfo orderInfo) throws Exception {
        if (Objects.isNull(orderInfo)) {
            return ResponseJson.buildFail("参数不能为空");
        }
        orderInfo.setCommodityPropertyJson(JSONObject.toJSONString(orderInfo.getCommodityInfoList()));
        OrderInfo orderinfo = orderService.createOrder(orderInfo);
        //为了让前台显示更加精炼，不显示
        orderinfo.setCommodityPropertyJson(null);
        return ResponseJson.buildSuccess("成功！",orderinfo);
    }


    @PostMapping("/pay")
    public JSONObject payMent(@RequestBody OrderInfo orderInfo) {
        if (Objects.isNull(orderInfo)) {
            return ResponseJson.buildFail("参数不能为空");
        }
        String result = orderService.aliPay(orderInfo);
        return ResponseJson.buildSuccess("true", result);
    }

    @PostMapping("/payList")
    public JSONObject payList(@RequestBody Integer[] commodityIds) {
        List<Integer> commodityIdList = new ArrayList<>(8);
        Collections.addAll(commodityIdList, commodityIds);
        List<CommodityInfo> commodityInfoList = orderService.getPayList(commodityIdList);
        return ResponseJson.buildSuccess("成功！", commodityInfoList);
    }

    @GetMapping("orderList")
    public JSONObject orderList() {
        List<OrderInfo> orderInfoList = orderService.findOrderList();
        for (OrderInfo orderInfo : orderInfoList) {
         orderInfo.setCommodityInfoList(JSONArray.parseArray(orderInfo.getCommodityPropertyJson()));
         orderInfo.setCommodityPropertyJson(null);
        }
        return ResponseJson.buildSuccess("true", orderInfoList);
    }

    /**
     * 异步通知
     * @param response
     * @param request
     * @throws IOException
     * @throws AlipayApiException
     */
    @RequestMapping(value = "/payNotify", method = RequestMethod.POST)
    public void notifyUrl(HttpServletResponse response, HttpServletRequest request) throws IOException, AlipayApiException {
        try(PrintWriter out = response.getWriter()) {
            boolean signVerified = OrderUtils.vetifyForm(request);
            if (!signVerified) {
                System.out.println("验签失败");
                out.print("fail");
                return;
            }
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            if (trade_status.equals("TRADE_FINISHED")) {
                //todo 退款
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                // 注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                orderService.setOrderState(out_trade_no);
            }
            out.print("success");
        }
    }

    /**
     * 同步通知
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws AlipayApiException
     */
    @RequestMapping(value = "/payReturn", method = RequestMethod.GET)
    public boolean returnUrl(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        request.setCharacterEncoding("utf-8");//乱码解决，这段代码在出现乱码时使用
        //获取支付宝GET过来反馈信息
        boolean signVerified = OrderUtils.vetifyForm(request);
        if (signVerified) {
            log.info("验签成功-跳转到成功后页面");
            //跳转至支付成功后的页面,
            return true;
        } else {
            log.info("验签失败-跳转到立即购买页面重新购买");
            return false;
        }
    }

}
