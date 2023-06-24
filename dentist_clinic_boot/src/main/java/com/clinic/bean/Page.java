package com.clinic.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : 15081
 * @DATE : 2023-01-02 14:07
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page {

    private Integer pageNum; //当前页
    private Integer pageSize; //每页记录数
    private Object rows; //用来封装列表数据
    private Integer pageCount; //总页数
    private long total; //总记录数
    private Integer pre; //上一页
    private Integer next; //下一页

}
