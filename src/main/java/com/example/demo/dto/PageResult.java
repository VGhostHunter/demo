package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 分页结果
 * @author vghosthunter
 */
@Data
@AllArgsConstructor
public class PageResult<T> {

    private long total;

    private List<T> items;
}
