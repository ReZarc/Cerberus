package com.clinic.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SurgeryOrder extends OrderDemo {
    private Integer id;     //手术id
    public Integer userId; //病人id
    public Integer roomId;
    public String roomName;
    private Integer describe;   //手术细节
    public Integer type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date surgeryInTime; //开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date surgeryOutTime;  //结束时间

}
