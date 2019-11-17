package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 一个基础的实体类 包含一些审计的字段
 * @author vghosthunter
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseAuditEntity {

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @CreatedBy
    private String createBy;

    @LastModifiedBy
    private String lastModifier;

}
