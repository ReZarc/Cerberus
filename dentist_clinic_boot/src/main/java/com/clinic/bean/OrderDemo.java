package com.clinic.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDemo {
    private Integer id;     //订单ID
    public String  onum;     //订单号
    public Integer status; //订单状态
    public Integer age;    //年龄
    public String phone;   //  手机号
    public Integer userId;  //病人ID
    public Integer doctorId;  //医生ID
    public String doctorName;     //医生姓名
    public String patientName;     //病人姓名
    public Integer price;      //价格
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date createTime;        //建立时间
    private static final long serialVersionUID = 1L;
}
