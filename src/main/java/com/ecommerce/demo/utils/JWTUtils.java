package com.ecommerce.demo.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ecommerce.demo.entity.model.ManagerInfo;
import com.ecommerce.demo.entity.MyException;
import com.ecommerce.demo.entity.model.UserInfo;

import java.util.Date;
import java.util.Map;

public class JWTUtils {
    /**
     * 过期时间
     */
    private static final long EXPIRE = 60000 * 60 * 12;

    /**
     * 加密秘钥
     */
    private static final String SECRET = "yuanguoguo";
    /**
     * 令牌前缀
     */
    private static final String PREFIX = "graduationProject";
    /**
     * 令牌名称
     */
    private static final String SUBJECT = "liujian";

    /**
     * JWT生成token
     * @param userInfo
     * @return
     */
    public static String generateToken(UserInfo userInfo) {
        String token = JWT.create()
                .withSubject(SUBJECT)
                .withClaim("userName", userInfo.getUserName())
                .withClaim("userId", userInfo.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE))
                .sign(Algorithm.HMAC256(SECRET));
        token = PREFIX + token;
        return token;
    }


    public static String generateToken(ManagerInfo managerInfo) {
        String token = JWT.create()
                .withSubject(SUBJECT)
                .withClaim("userName", managerInfo.getManagerName())
                .withClaim("userId", managerInfo.getManagerName())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE))
                .sign(Algorithm.HMAC256(SECRET));
        token = PREFIX + token;
        return token;
    }

    /**
     * 认证token并解析
     * @param token
     * @return
     */
    public static Map<String, Claim> checkJWT(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();//用密码获得解密
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token.replace(PREFIX,""));//解析token
        }catch (Exception e){
            throw new MyException("登录已过期，请重新登录","200");
        }
        return jwt.getClaims();
    }
}
