package com.homework.addressbook.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.homework.addressbook.util.RequestUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * 登陆拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //使用最简单的session提取用户信息
        HttpSession session = request.getSession();
        //登录时存放的user信息
        Object user = session.getAttribute("user");
        //如果不是白名单内的   接口  将会重定向  到登录页面
        if (user == null) {
            //如果为空。获取请求。
            boolean ajax = RequestUtil.isAjax(request);
            if (ajax) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = null;
                try {
                    JSONObject res = new JSONObject();
                    res.put("code", "-1");
                    out = response.getWriter();
                    out.append(res.toString());
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //返回登录
                response.sendRedirect("/");
            }
            System.out.println("拦截："+request.getRequestURI());
            return false;
        }//不为空，放行
        return true;
    }


}
