package com.ecommerce.demo.mapper;

import com.ecommerce.demo.entity.model.AuthCodeInfo;
import com.ecommerce.demo.entity.model.ShoppingCartInfo;
import com.ecommerce.demo.entity.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    /**
     * 用户注册
     * @param userInfo
     * @return
     */
    int userRegist( UserInfo userInfo);
    /**
     * 通过用户名查找用户
     * @param userName
     * @return
     */
    int findUserByUserName(@Param("userName")String userName);
    /**
     * 通过用户名和密码查找用户
     * @param userInfo
     * @return
     */
    UserInfo findUserByUserNameAndPwd( UserInfo userInfo);
    /**
     * 通过手机号查找用户
     * @param userPhone
     * @return
     */
    UserInfo findUserByUserPhone(@Param("userPhone") String userPhone);

    /**
     * 储存验证码
     * @param authCodeInfo
     */
    void storeAuthCode(AuthCodeInfo authCodeInfo);

    /**
     * 获取验证码
     * @param userPhone
     */
    Integer getAuthCode(@Param("userPhone")String userPhone);

    /**
     * 加入商品到购物车
     * @param shoppingCartInfo
     * @return
     */
    int addShoppingCart(ShoppingCartInfo shoppingCartInfo);

    /**
     * 从购物车移除商品
     * @param commodityId
     * @return
     */
    int delCommodityCart(List<Integer> commodityId);


    /**
     * 减少购物车莫商品数量
     *
     * @param commodityId
     * @return
     */
    int reduceGoodsAmounts(@Param("commodityId") Integer commodityId);

    /**
     * 购物车商品数量增加
     * @param commodityId
     * @return
     */
    int addAmountsCart(Integer commodityId);

    /**
     * 商品列表展示
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<ShoppingCartInfo> getShoppingCartList(String userId);
}
