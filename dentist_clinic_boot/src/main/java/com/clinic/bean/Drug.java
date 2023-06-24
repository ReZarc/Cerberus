package com.clinic.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Drug {
    private Integer id;     //药物id
    private String name;    //药物名称
    private String describe;    //药物简介
    private String tag;     //药物标签  过敏慎用之类
    private Integer price;   //药物价格
}
