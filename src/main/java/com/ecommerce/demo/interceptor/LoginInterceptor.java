package com.ecommerce.demo.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.ecommerce.demo.utils.JWTUtils;
import com.ecommerce.demo.utils.ResponseJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json ; charset=utf-8");
        PrintWriter writer = response.getWriter();
        String token = request.getHeader("token");
        if (StringUtils.isNotBlank(token)){
            Map<String,Claim>claims = JWTUtils.checkJWT(token);
            if (StringUtils.isNotBlank((CharSequence) claims.get("userId"))&&StringUtils.isNotBlank((CharSequence) claims.get("userName"))) {
                request.setAttribute("userId", claims.get("userId"));
                request.setAttribute("userName", claims.get("userName"));
            }
            return true ;
        }
        writer.print(objectMapper.writeValueAsString(ResponseJson.buildFail("未登录,请先登录")));
        writer.flush();
        writer.close();
        return false;
    }

}
