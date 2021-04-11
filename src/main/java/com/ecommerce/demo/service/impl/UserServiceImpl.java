package com.ecommerce.demo.service.impl;

import com.ecommerce.demo.entity.model.AuthCodeInfo;
import com.ecommerce.demo.entity.model.ShoppingCartInfo;
import com.ecommerce.demo.entity.model.UserInfo;
import com.ecommerce.demo.mapper.UserMapper;
import com.ecommerce.demo.service.UserService;
import com.ecommerce.demo.utils.MD5Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 刘剑
 * @data 2021/03/24
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 用户注册
     *
     * @param userInfo
     * @return
     */
    @Override
    public boolean registUser(UserInfo userInfo) {
        String password = MD5Utils.MD5(userInfo.getPassWord());
        userInfo.setPassWord(password);
        int row = userMapper.userRegist(userInfo);
        if (row == 1) {
            return true;
        }
        return false;
    }

    /**
     * 通过用户名查找用户
     *
     * @param userName
     * @return
     */
    @Override
    public boolean findUserByUserName(String userName) {
        int row = userMapper.findUserByUserName(userName);
        if (row == 0) {
            return true;
        }
        return false;
    }

    /**
     * 通过用户名和密码查找用户
     *
     * @param userInfo
     * @return
     */
    @Override
    public boolean findUserByUserNameAndPwd(UserInfo userInfo) {
        userInfo.setPassWord(MD5Utils.MD5(userInfo.getPassWord()));
        int row = userMapper.findUserByUserNameAndPwd(userInfo);
        if (row == 1) {
            return true;
        }
        return false;
    }

    /**
     * 通过手机号查找用户
     *
     * @param userPhone
     * @return
     */
    @Override
    public boolean findUserByPhone(String userPhone) {
        int row = userMapper.findUserByUserPhone(userPhone);
        if (row == 1) {
            return true;
        }
        return false;
    }

    /**
     * 获取验证码
     *
     * @param userPhone
     */
    @Override
    public Integer getAuthCode(String userPhone) {
        Integer codeNumber = userMapper.getAuthCode(userPhone);
        if (codeNumber == null) {
            return -1;
        }
        return codeNumber;
    }

    /**
     * 存储验证码
     *
     * @param authCodeInfo
     */
    @Override
    public void storeAuthCode(AuthCodeInfo authCodeInfo) {
        userMapper.storeAuthCode(authCodeInfo);
    }

    /**
     * 加入商品到购物车
     *
     * @param shoppingCartInfo
     * @return
     */
    @Override
    public boolean addShoppingCart(ShoppingCartInfo shoppingCartInfo) {
        int row = userMapper.addShoppingCart(shoppingCartInfo);
        if (row > 0) {
            return true;
        }
        return false;
    }

    /**
     * 从购物车移除商品
     *
     * @param commodityId
     * @return
     */
    @Override
    public boolean delCommodityInCart(Integer commodityId) {
        int row = userMapper.delCommodityCart(commodityId);
        if (row > 0) {
            return true;
        }
        return false;
    }

    /**
     * 减少购物车莫商品数量
     *
     * @param commodityId
     * @return
     */
    @Override
    public boolean reduceGoodsAmount(Integer commodityId) {
        int row = userMapper.reduceGoodsAmounts(commodityId);
        if (row > 0){
            return true;
        }
        return false;
    }

    /**
     * 购物车商品数量增加
     * @param commodityId
     * @return
     */
    @Override
    public boolean addAmountCart(Integer commodityId) {
        int row = userMapper.addAmountsCart(commodityId);
        if (row > 0){
            return true;
        }
        return false;
    }

    /**
     * 展示购物车列表
     * @param hashmap
     * @return
     */
    @Override
    public PageInfo<ShoppingCartInfo> findShoppingCartList(Map<String, String> hashmap) {
        int pageNumber = Integer.parseInt(hashmap.get("pageNumber"));
        int pageSize = Integer.parseInt(hashmap.get("pageSize"));
        String userId = hashmap.get("userId");
        PageHelper.startPage(pageNumber,pageSize);
        List<ShoppingCartInfo> list = userMapper.getShoppingCartList(userId);
        return new PageInfo<>(list);
    }
}
