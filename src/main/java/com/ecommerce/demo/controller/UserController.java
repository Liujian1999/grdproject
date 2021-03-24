package com.ecommerce.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.ecommerce.demo.entity.model.ManagerInfo;
import com.ecommerce.demo.entity.MyException;
import com.ecommerce.demo.entity.model.UserInfo;
import com.ecommerce.demo.service.UserService;
import com.ecommerce.demo.utils.JWTUtils;
import com.ecommerce.demo.utils.ResponseJson;
import com.zhenzi.sms.ZhenziSmsClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
    /**
     * 榛子云url
     */
    private String apiUrl = "https://sms_developer.zhenzikj.com";
    /**
     * 榛子云AppId
     */
    private String appId = "107954";
    /**
     * 榛子云appSecret
     */
    private String appSecret = "f734e8f0-de41-4317-a3a9-f7bfacbdfc4f";
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
            flag = userService.findUserByUserName(userInfo.getUserName());
            if (flag == false) {
                return ResponseJson.buildFail("该用户名已存在");
            } else if () {
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
     * @param userInfo
     * @return
     */
    @PostMapping("/userLogin")
    public JSONObject userLogin(@RequestBody UserInfo userInfo) {
        Map<String,String> map = new HashMap<>();
        /**
         * 以手机验证码登录
         */
        try {
            if (StringUtils.isNotBlank(userInfo.getUserPhone())) {
                /**
                 * 查找该用户是否存在
                 */
                boolean flag = userService.findUserByPhone(userInfo.getUserPhone());
                if (flag == false) {
                    return ResponseJson.buildFail("该用户不存在,请先注册！");
                }
                //校验验证码
                if () {
                    String token = JWTUtils.generateToken(userInfo);
                    map.put("token",token);
                    return ResponseJson.buildSuccess("登录成功！",map);
                } else {
                    return ResponseJson.buildFail("验证码输入不正确，登录失败");
                }
            } else {
                /**
                 * 以账号密码登录
                 */
                boolean flag = userService.findUserByUserNameAndPwd(userInfo);
                if (flag == true) {
                    String token = JWTUtils.generateToken(userInfo);
                    map.put("token",token);
                    return ResponseJson.buildSuccess("登录成功！",map);
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
     * @param managerInfo
     * @return
     */
    //todo 暂时写死，之后修改
    @PostMapping("/shopLogin")
    public JSONObject ManagerLogin(@RequestBody ManagerInfo managerInfo){
        Map<String,String>map = new HashMap<>();
        if(managerInfo.getManagerName().equals("admin")&& managerInfo.getPassword().equals("123456")){
            String token = JWTUtils.generateToken(managerInfo);
            map.put("token",token);
            return ResponseJson.buildSuccess("登录成功！",map);
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
    public JSONObject getCode(@RequestParam("phoneNumber") String phoneNumber) {
        try {
            JSONObject json;
            ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
            String code = String.valueOf(new Random().nextInt(899999) + 100000);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("number", phoneNumber);
            params.put("templateId", "3423");
            //这个参数就是短信模板上那两个参数
            String[] templateParams = new String[2];
            templateParams[0] = code;
            templateParams[1] = "2分钟";
            params.put("templateParams", templateParams);
            String result = client.send(params);
            json = JSONObject.parseObject(result);
            if (json.getInteger("code") != 0)
                return ResponseJson.buildFail("短信发送失败,code:" + code);
            json = new JSONObject();
            json.put("memPhone", phoneNumber);
            json.put("createTime", System.currentTimeMillis());
            // 将认证码存入数据库
            return ResponseJson.buildSuccess("发送成功", json);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseJson.buildFail("发送失败");
        }
    }


}
