package com.ecommerce.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.ecommerce.demo.entity.MyException;
import com.ecommerce.demo.entity.model.CommodityInfo;
import com.ecommerce.demo.service.CommodityService;
import com.ecommerce.demo.utils.ResponseJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/shelve")
public class CommodityController {
    @Autowired
    CommodityService commodityService;

    /**
     * 图片上传
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadImg")
    public JSONObject uploadImg(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseJson.buildFail("该文件不存在");
            } else {
                return ResponseJson.buildSuccess("上传成功！", commodityService.uploadImg(file));
            }
        } catch (Exception e) {
            throw new MyException("文件上传异常", "600");
        }
    }

    /**
     * 商品上架
     *
     * @param shelveInfo
     * @return
     */
    @PostMapping("/commodityShelve")
    public JSONObject commodityShelve(@RequestBody CommodityInfo shelveInfo) {
        if (Objects.isNull(shelveInfo)) {
            return ResponseJson.buildFail("商品信息不能为空");
        }
        //建立时间戳
        shelveInfo.setShelveTime(System.currentTimeMillis());
        boolean flag = commodityService.commodityShelve(shelveInfo);
        if (flag == true) {
            ResponseJson.buildSuccess("上架成功！");
        }
        return ResponseJson.buildFail("上架失败");
    }

    /**
     * 商品下架
     *
     * @param id
     * @return
     */
    @GetMapping("/commodityRemove/{id}")
    public JSONObject commodityRemove(@PathVariable("id") Integer id) {
        if(id==null){
            return ResponseJson.buildFail("商品id不能为空！");
        }
        boolean flag = commodityService.commodityRemove(id);
        if (flag == true) {
            ResponseJson.buildSuccess("下架成功！");
        }
        return ResponseJson.buildFail("下架失败,该商品不存在");
    }

    /**
     * 展示所有上架的商品
     * @return
     */
    @GetMapping("/shelvesInfo/{state}")
    public JSONObject shelvesInfo(@PathVariable("state") Integer state){
        Map<String,List> map = new HashMap<>();
        if(state==null){
            return ResponseJson.buildFail("state不能为空！");
        }
       List<CommodityInfo> commodities =  commodityService.findAllCommoditiesByState(state);
        map.put("commodities",commodities);
        return ResponseJson.buildSuccess("展示成功！",map);
    }
}
