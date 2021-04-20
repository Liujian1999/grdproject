package com.ecommerce.demo.entity.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShoppingCartInfo {
    /**
     * id
     */
    private Integer id;

    /**
     * 商品id
     */
    private Integer commodityId;

    /**
     * 商品数量
     */
    private Integer amount;

    /**
     * 商品价格
     */
    private BigDecimal commodityPrice;

    /**
     * 商品实际售价
     */
    private BigDecimal commodityRealPrice;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商品图片
     */
    private String commodityImg;

    /**
     *商品描述
     */
    private String commodityDescribe;

    /**
     * 商品名称
     */
    private String commodityName;
}
