package com.abc.cmspring2.securities;

import com.abc.cmspring2.controllers.AuthController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor 
{

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception 
    {

        String uri = request.getRequestURI();
        String ctx = request.getContextPath();
        if (uri.startsWith(ctx + "/login")
                || uri.startsWith(ctx + "/logout")
                || uri.startsWith(ctx + "/change-password")
                || uri.startsWith(ctx + "/assets/")
                || uri.startsWith(ctx + "/css/")
                || uri.startsWith(ctx + "/js/")) 
        {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null) 
        {
            response.sendRedirect(ctx + "/login");
            return false;
        }
        SessionUser su = (SessionUser) session.getAttribute(AuthController.SESSION_USER);
        if (su == null) 
        {
            response.sendRedirect(ctx + "/login");
            return false;
        }
        if (su.isMustChangePassword()) 
        {
            response.sendRedirect(ctx + "/change-password");
            return false;
        }
        return true;
    }
}
