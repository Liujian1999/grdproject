package com.ecommerce.demo.mapper;

import com.ecommerce.demo.entity.model.CommodityInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityMapper {
    /**
     * 商品上架
     * @param shelveInfo
     * @return
     */
    int commodityShelve( CommodityInfo shelveInfo);

    /**
     * 商品下架
     * @param id
     * @return
     */
    int commodityRemove(@Param("id") Integer id);

    /**
     * 查询商品详情
     * @param id
     * @return
     */

    CommodityInfo findCommodityById(@Param("id") Integer id);

    /**
     * 查看商品列表
     * @param timeStamp
     * @return
     */

    List<CommodityInfo> findAllCommodities(@Param("invalidTime") long timeStamp);
}
