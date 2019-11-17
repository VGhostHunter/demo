package com.example.demo.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author vghosthunter
 */
@Table
@Entity
@Data
@DynamicUpdate()
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class AuditLog extends BaseAuditEntity {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    private String method;

    private String path;

    private Integer status;

}
