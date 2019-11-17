package com.example.demo.web.dao;

import com.example.demo.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author vghosthunter
 */
public interface AuditLogRepository extends JpaRepository<AuditLog, String> {
}
