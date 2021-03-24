package com.ecommerce.demo.service;

import com.ecommerce.demo.entity.model.CommodityInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CommodityService {
    /**
     * 图片上传
     * @param file
     * @return
     */
    Map<String,String> uploadImg(MultipartFile file);

    /**
     * 商品上架
     * @param shelveInfo
     * @return
     */
    boolean commodityShelve(CommodityInfo shelveInfo);
    /**
     * 商品下架
     * @param id
     * @return
     */
    boolean commodityRemove(int id);

    /**
     * 展示上架的商品
     * @param state
     * @return
     */
    List<CommodityInfo> findAllCommoditiesByState(Integer state);
}
