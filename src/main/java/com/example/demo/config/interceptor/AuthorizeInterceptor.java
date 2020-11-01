package com.example.demo.config.interceptor;

import com.example.demo.dto.user.UserDto;
import com.example.demo.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vghosthunter
 */
@Component
public class AuthorizeInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        UserDto userDto = userService.currentUser();

        if(userDto == null) {
            response.setContentType("text/plain");
            response.getWriter().write("Unauthorized");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            return false;
        } else if(!userDto.hasPermission("operate")) {
            response.setContentType("text/plain");
            response.getWriter().write("Forbidden");
            response.setStatus(HttpStatus.FORBIDDEN.value());

            return false;
        }

        return true;
    }
}
