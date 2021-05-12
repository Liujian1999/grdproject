package com.ecommerce.demo.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.ecommerce.demo.entity.MyException;
import com.ecommerce.demo.utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 拦截token
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */

    @Override
    @Transactional
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isNotBlank(token)){
            Map<String,Claim>claims = JWTUtils.checkJWT(token);
            if (StringUtils.isNotBlank((CharSequence) claims.get("userId"))&&StringUtils.isNotBlank((CharSequence) claims.get("userName"))) {
                request.setAttribute("userId", claims.get("userId"));
                request.setAttribute("userName", claims.get("userName"));
            }
            return true ;
        }else {
            throw new MyException("未登录，请重新登录","1000");
        }
    }

}
