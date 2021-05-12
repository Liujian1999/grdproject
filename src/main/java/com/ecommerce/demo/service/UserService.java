package com.ecommerce.demo.service;


import com.ecommerce.demo.entity.model.AuthCodeInfo;
import com.ecommerce.demo.entity.model.ShoppingCartInfo;
import com.ecommerce.demo.entity.model.UserInfo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author liujian
 */
public interface UserService {

    /**
     * 用户注册
     * @param userInfo
     * @return
     */
    boolean registUser(UserInfo userInfo);

    /**
     * 通过用户名查找用户
     * @param userName
     * @return
     */
    boolean findUserByUserName(String userName,String userPhone);

    /**
     * 校验账号是否正确
     * @param user
     * @return
     */
    UserInfo findUserByUserNameAndPwd(UserInfo user);

    /**
     * 通过手机号查找用户
     * @param userPhone
     * @return
     */
    UserInfo findUserByPhone(String userPhone);

    /**
     * 获得手机验证码
     * @param userPhone
     * @return
     */
    Integer getAuthCode(@Param("userPhone")String userPhone);

    /**
     * 储存验证码
     * @param authCodeInfo
     */
    void  storeAuthCode(AuthCodeInfo authCodeInfo);

    /**
     * 购物车新增商品
     * @param shoppingCartInfo
     * @return
     */
    boolean addShoppingCart(ShoppingCartInfo shoppingCartInfo);

    /**
     * 从购物车移除商品
     * @param commodityId
     * @return
     */
    boolean delCommodityInCart(List<Integer> commodityId);

    /**
     * 减少购物车莫商品数量
     * @param commodityId
     * @return
     */
    boolean reduceGoodsAmount(Integer commodityId);

    /**
     * 购物车商品数量增加
     * @param commodityId
     * @return
     */
    boolean addAmountCart(Integer commodityId);

    /**
     * 展示购物车列表
     * @param hashmap
     * @return
     */
    PageInfo<ShoppingCartInfo> findShoppingCartList(Map<String, String> hashmap);

    boolean userUpdate(UserInfo userInfo);
}
