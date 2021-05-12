package com.ecommerce.demo.entity.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CommodityInfo {
    /**
     * 商品id
     */
    private Integer id;

    /**
     * 商品图片
     */
    private String commodityImg;
    /**
     * 商品描述
     */
    private String commodityDescribe ;
    /**
     * 商品原价
     */
    private BigDecimal commodityPrice;
    /**
     * 商品实际售价
     */
    private BigDecimal commodityRealPrice;
    /**
     * 商品折扣率
     */
    private double commissionRate;
    /**
     * 商品上架时间
     */
    private long shelveTime;
    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 商品上架状态
     */

    private Integer state;

    /**
     * 上架方式
     */

    private boolean isCarousel;

}
