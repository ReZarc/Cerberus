package com.clinic.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   //自动生成属性的set get方法
@AllArgsConstructor  //自动生成全参的构造方法
@NoArgsConstructor  //自动生成无参的构造方法
public class UserDemo {
    public Integer id; //用户id
    public String username;//用户名
    public String password;//密码
    public String nickname;//昵称
    public Integer age;    //年龄
    public String phone;   //手机号
    public String name;    //姓名
    public Integer gender; //性别
    public Integer type;   //类型  1病人  2医生  3药师  4管理员
}
