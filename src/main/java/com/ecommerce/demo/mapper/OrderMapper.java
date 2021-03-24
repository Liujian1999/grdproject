package com.ecommerce.demo.mapper;

import com.ecommerce.demo.entity.model.OrderInfo;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {
    void orderCreate(@Param("orderInfo") OrderInfo orderInfo);
}
