package com.example.demo.config.interceptor;

import com.example.demo.dto.user.UserDto;
import com.example.demo.entity.AuditLog;
import com.example.demo.web.dao.AuditLogRepository;
import com.example.demo.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author vghosthunter
 */
@Component
public class AuditInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuditLog auditLog = new AuditLog();
        auditLog.setMethod(request.getMethod());
        auditLog.setPath(request.getRequestURI());
        auditLogRepository.save(auditLog);

        request.setAttribute("auditLogId", auditLog.getId());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //应该覆盖 afterCompletion 因为不管处理失败还是成功都会调用
        String id = (String) request.getAttribute("auditLogId");
        AuditLog auditLog = auditLogRepository.findById(id).get();
        auditLog.setStatus(response.getStatus());

        auditLogRepository.save(auditLog);
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAware<String>() {
            @Override
            public Optional<String> getCurrentAuditor() {
                UserDto user = userService.currentUser();
                return Optional.ofNullable(user != null ? user.getId() : null);
            }
        };
    }
}
