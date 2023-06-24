package com.clinic.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class PatientOrder extends OrderDemo {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date checkInTime; //预约时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date checkOutTime;  //结束时间
    private Integer type;   //看诊科类(1牙体牙髓 2口腔黏膜 3牙周 4口腔种植 5口腔修复 6儿童口腔科 7老年口腔科 8无痛治疗中心 9急诊综合诊疗中心)
    public Integer dentistryId;
    public String dentistryName;
    public Integer roomId;
    public String roomName;
    public Integer count;
}
