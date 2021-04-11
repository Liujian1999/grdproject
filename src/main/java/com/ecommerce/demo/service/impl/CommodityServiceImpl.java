package com.ecommerce.demo.service.impl;

import com.ecommerce.demo.entity.MyException;
import com.ecommerce.demo.entity.model.CommodityInfo;
import com.ecommerce.demo.mapper.CommodityMapper;
import com.ecommerce.demo.service.CommodityService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
/**
 * @author 刘剑
 * @data 2021/03/24
 */

@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    CommodityMapper commodityMapper;

    /**
     * 图片上传
     * @param file
     * @return
     */
    @Override
    public Map<String, String> uploadImg(MultipartFile file) throws IOException {
        Map<String, String> map = new HashMap<>();
        //设置上传路径
        String filePath = File.separator + "usr" + File.separator + "local" + File.separator + "www"+File.separator + "img";
        //获取上传文件名
        String fileName = file.getOriginalFilename();
        //设置新的文件名，防止重复
        String localName = UUID.randomUUID().toString();
        //截取文件类型
        String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        //设置新的文件名
        fileName = localName + fileType;
        //获得文件总路径
        String realPath = filePath + File.separator + fileName;
        File dir = new File(realPath);
            file.transferTo(dir);
        map.put("fileName", fileName);
        map.put("url", realPath);
        return map;
    }

    /**
     * 商品上架
     * @param shelveInfo
     * @return
     */
    @Override
    public boolean commodityShelve(CommodityInfo shelveInfo) {
        int row = commodityMapper.commodityShelve(shelveInfo);
        if (row>0){
            return true;
        }
        return false;
    }

    /**
     * 商品下架
     * @param id
     * @return
     */
    @Override
    public boolean commodityRemove(int id) {
        int row = commodityMapper.commodityRemove(id);
        if (row>0){
            return true;
        }
        return false;
    }

    /**
     * 展示上架的商品
     * @param id
     * @return
     */
    @Override
    public CommodityInfo findCommodityById(Integer id) {
        return commodityMapper.findCommodityById(id);
    }

    /**
     * 商品列表展示
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<CommodityInfo> findAllCommodities(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber,pageSize);
        long timeStamp = System.currentTimeMillis();
        List<CommodityInfo> commodityInfos = commodityMapper.findAllCommodities(timeStamp);
        return new PageInfo<>(commodityInfos);
    }
}
