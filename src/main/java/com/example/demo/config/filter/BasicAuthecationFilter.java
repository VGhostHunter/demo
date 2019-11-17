package com.example.demo.config.filter;

import com.example.demo.dto.user.UserDto;
import com.example.demo.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author vghosthunter
 */
@Component
public class BasicAuthecationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if(!StringUtils.isEmpty(authHeader)) {
            if(authHeader.contains("Basic ")) {
                String token64 = authHeader.substring(authHeader.indexOf(" ") + 1);
                String token = new String(Base64Utils.decodeFromString(token64));
                String[] items = StringUtils.split(token, ":");
                if(items != null && items.length == 2) {
                    String username = items[0];
                    String password = items[1];

                    UserDto userDto = userService.check(username, password);
                    if(userDto != null) {
                        HttpSession session = request.getSession(false);
                        if(session != null) {
                            //防止session定向攻击
                            session.invalidate();
                        }
                        request.getSession().setAttribute("user", userDto);
                        request.getSession().setAttribute("delete", "yes");
                    }
                }
            }
        }
        try{
            filterChain.doFilter(request, response);
        } finally {
            //销毁Session
            HttpSession session = request.getSession(false);
            if(session != null) {
                if(session.getAttribute("delete") != null){
                    session.invalidate();
                }
            }
        }
    }
}
