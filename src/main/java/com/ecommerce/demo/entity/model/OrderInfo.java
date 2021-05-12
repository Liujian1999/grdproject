package com.ecommerce.demo.entity.model;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderInfo {
    /**
     * 订单名称
     */
    private String orderName;

    /**
     * 订单id
     */
    private Integer id;
    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 订单原价
     */
    private double originalPrice;

    /**
     * 订单成交价
     */
    private float sellPrice;

    /**
     * 订单状态
     */
    private Integer orderState;

    /**
     * 订单创建时间
     */
    private long createTime;

    /**
     * 商品列表
     */
    private JSONArray commodityInfoList;

    /**
     * 订单商品信息
     */
    private String commodityPropertyJson;
}
