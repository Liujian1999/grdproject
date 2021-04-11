package com.ecommerce.demo.service;

import com.ecommerce.demo.entity.model.CommodityInfo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CommodityService {
    /**
     * 图片上传
     * @param file
     * @return
     */
    Map<String,String> uploadImg(MultipartFile file) throws IOException;

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
     * @param id
     * @return
     */
    CommodityInfo findCommodityById(Integer id);

    /**
     * 商品列表展示
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageInfo<CommodityInfo> findAllCommodities(int pageNumber, int pageSize);
}
