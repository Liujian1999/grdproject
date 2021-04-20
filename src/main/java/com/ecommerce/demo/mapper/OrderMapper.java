package com.ecommerce.demo.mapper;

import com.ecommerce.demo.entity.model.CommodityInfo;
import com.ecommerce.demo.entity.model.OrderInfo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
    void orderCreate(@Param("orderInfo") OrderInfo orderInfo);

    List<CommodityInfo> getPayList(List<Integer> commodityIdList);

    List<OrderInfo> findOrderList();

    void setOrderState(@Param("orderNumber") String orderNumber);

    void setClosedOrderState(@Param("validTime") long validTime);
}
