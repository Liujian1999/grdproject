package com.ecommerce.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ecommerce.demo.entity.model.AuthCodeInfo;
import com.ecommerce.demo.entity.model.ManagerInfo;
import com.ecommerce.demo.entity.MyException;
import com.ecommerce.demo.entity.model.ShoppingCartInfo;
import com.ecommerce.demo.entity.model.UserInfo;
import com.ecommerce.demo.service.UserService;
import com.ecommerce.demo.utils.AuthCodeUtils;
import com.ecommerce.demo.utils.JWTUtils;
import com.ecommerce.demo.utils.ResponseJson;
import com.github.pagehelper.PageInfo;
import com.zhenzi.sms.ZhenziSmsClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author liujian
 * @data 2021/03/24
 */
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
    /**
     * 榛子云url
     */
    private final String apiUrl = "https://sms_developer.zhenzikj.com";
    /**
     * 榛子云AppId
     */
    private final String appId = "107954";
    /**
     * 榛子云appSecret
     */
    private final String appSecret = "f734e8f0-de41-4317-a3a9-f7bfacbdfc4f";

    @Autowired
    UserService userService;

    /**
     * 用户注册
     *
     * @param userInfo
     * @return
     */
    @PostMapping("/userRegist")
    public JSONObject userRegist(@RequestBody UserInfo userInfo) throws Exception {

        boolean flag = false;
        if (userInfo == null) {
            return ResponseJson.buildFail("用户信息不能为空");
        }
        try {
            Integer code = new AuthCodeUtils().getAuthCode(userInfo.getUserPhone());
            if (code == -1) {
                return ResponseJson.buildFail("验证码未生效，请重新发送");
            }
            flag = userService.findUserByUserName(userInfo.getUserName(),userInfo.getUserPhone());
            if (flag == false) {
                return ResponseJson.buildFail("该用户已存在");
            } else if (!userInfo.getCode().equals(String.valueOf(code))) {
                return ResponseJson.buildFail("验证码输入错误");
            }
            flag = userService.registUser(userInfo);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new Exception();
        }

        return ResponseJson.buildSuccess("注册成功!");
    }

    /**
     * 用户登录
     *
     * @param userInfo
     * @return
     */
    @PostMapping("/userLogin")
    public JSONObject userLogin(@RequestBody UserInfo userInfo) {
        Map<String, Object> map = new HashMap<>(10);
        /**
         * 以手机验证码登录
         */
        try {
            if (StringUtils.isNotBlank(userInfo.getUserPhone())) {
                /**
                 * 查找该用户是否存在
                 */
                UserInfo user = userService.findUserByPhone(userInfo.getUserPhone());
                if (user == null) {
                    return ResponseJson.buildFail("该用户不存在,请先注册！");
                }
                Integer code = new AuthCodeUtils().getAuthCode(userInfo.getUserPhone());
                if (code == -1) {
                    return ResponseJson.buildFail("验证码未生效，请重新发送");
                }
                //校验验证码
                if (userInfo.getCode().equals(String.valueOf(code))) {
                    String token = JWTUtils.generateToken(userInfo);
                    map.put("token", token);
                    map.put("user" , user);
                    return ResponseJson.buildSuccess("登录成功！", map);
                } else {
                    return ResponseJson.buildFail("验证码输入不正确，登录失败");
                }
            } else {
                /**
                 * 以账号密码登录
                 */
                UserInfo user = userService.findUserByUserNameAndPwd(userInfo);
                if (user != null) {
                    String token = JWTUtils.generateToken(userInfo);
                    map.put("token", token);
                    map.put("user" , user);
                    return ResponseJson.buildSuccess("登录成功！", map);
                }
                return ResponseJson.buildFail("账号或密码不正确，登录失败");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new MyException("传输数据有误，请检查", "300");
        }

    }

    /**
     * 商铺管理员登录
     *
     * @param managerInfo
     * @return
     */
    //todo 暂时写死，之后修改
    @PostMapping("/shopLogin")
    public JSONObject ManagerLogin(@RequestBody ManagerInfo managerInfo) {
        Map<String, String> map = new HashMap<>(10);
        if ("admin".equals(managerInfo.getManagerName()) && "123456".equals(managerInfo.getPassword())) {
            String token = JWTUtils.generateToken(managerInfo);
            map.put("token", token);
            return ResponseJson.buildSuccess("登录成功！", map);
        }
        return ResponseJson.buildFail("账号或密码不正确，登录失败");
    }


    /**
     * 验证码发送
     *
     * @param phoneNumber
     * @return
     */
    @GetMapping(value = "/phone/code")
    public JSONObject getCode(@RequestParam("phoneNumber") String phoneNumber) throws Exception {
        AuthCodeInfo authCodeInfo = new AuthCodeInfo();
        authCodeInfo.setSendTime(System.currentTimeMillis());
        authCodeInfo.setUserPhone(phoneNumber);
        JSONObject json;
        ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
        String code = String.valueOf(new Random().nextInt(899999) + 100000);
        Map<String, Object> params = new HashMap<String, Object>(10);
        params.put("number", phoneNumber);
        params.put("templateId", "3423");
        //这个参数就是短信模板上那两个参数
        String[] templateParams = new String[2];
        templateParams[0] = code;
        templateParams[1] = "2分钟";
        params.put("templateParams", templateParams);
        authCodeInfo.setCodeNumber(Integer.parseInt(code));
        //存储验证码
        AuthCodeUtils.storeAuthCode(authCodeInfo);
        String result = client.send(params);
        json = JSONObject.parseObject(result);
        if (json.getInteger("code") != 0) {
            return ResponseJson.buildFail("短信发送失败,code:" + code);
        }
        json = new JSONObject();
        json.put("memPhone", phoneNumber);
        json.put("createTime", System.currentTimeMillis());
        return ResponseJson.buildSuccess("发送成功", json);
    }

    /**
     * 商品加入购物车
     */
    @PostMapping("addCart")
    public JSONObject addCart(@RequestBody ShoppingCartInfo shoppingCartInfo) {
        if (Objects.isNull(shoppingCartInfo)) {
            return ResponseJson.buildFail("参数不能为空！");
        }
        boolean flag = userService.addShoppingCart(shoppingCartInfo);
        if (flag) {
            return ResponseJson.buildSuccess("加入购物车成功！");
        } else {
            return ResponseJson.buildFail("加入购物车失败！");
        }
    }

    /**
     * 从购物车移除商品
     */
    @PostMapping("delInCart")
    public JSONObject delCommodityInCart(@RequestBody List<Integer>commodityId) {
        if (commodityId == null) {
            return ResponseJson.buildFail("商品id不能为空");
        }
        boolean flag = userService.delCommodityInCart(commodityId);
        if (flag) {
            return ResponseJson.buildSuccess("移除成功！");
        } else {
            return ResponseJson.buildFail("移除失败！");
        }
    }

    /**
     * 购物车减少某一商品数量
     */
    @GetMapping("reduceGoodsAmount/{commodityId}")
    public JSONObject reduceGoodsAmount(@PathVariable("commodityId") Integer commodityId) {
        if (commodityId == null) {
            return ResponseJson.buildFail("商品id不能为空");
        }
        boolean flag = userService.reduceGoodsAmount(commodityId);
        if (flag) {
            return ResponseJson.buildSuccess("减少数量成功！");
        } else {
            return ResponseJson.buildFail("减少数量失败！");
        }
    }

    /**
     * 购物车商品数量增加
     */
    @GetMapping("addAmountCart/{commodityId}")
    public JSONObject addCart(@PathVariable("commodityId") Integer commodityId) {
        if (commodityId == null) {
            return ResponseJson.buildFail("商品id不能为空");
        }
        boolean flag = userService.addAmountCart(commodityId);
        if (flag) {
            return ResponseJson.buildSuccess("增加数量成功！");
        } else {
            return ResponseJson.buildFail("增加数量失败！");
        }
    }

    /**
     * 购物车列表
     */
    @PostMapping("shoppingCartList")
    public JSONObject shoppingCartList(@RequestBody Map<String,String> hashmap) {
        if (hashmap.isEmpty()) {
            return ResponseJson.buildFail("商品id不能为空");
        }
        PageInfo<ShoppingCartInfo>pageInfo = userService.findShoppingCartList(hashmap);
        return ResponseJson.buildSuccess("展示成功",pageInfo);
    }

    /**
     * 修改用户密码
     */
    @PostMapping("/update")
    public JSONObject userUpdate(@RequestBody UserInfo userInfo){
        Integer code = new AuthCodeUtils().getAuthCode(userInfo.getUserPhone());
        if (code == -1) {
            return ResponseJson.buildFail("验证码未生效，请重新发送");
        }
        boolean flag = userService.userUpdate(userInfo);
        if (flag){
            return ResponseJson.buildSuccess("修改成功！");
        }
        return ResponseJson.buildFail("修改失败！");
    }
}

