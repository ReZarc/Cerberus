package com.clinic.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 15081
 * @DATE : 2022-12-30 10:40
 *  响应数据类
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo<T> {

    private Integer code; // 状态码
    private String msg; // 响应信息
    private  T  data; //响应数据

    private  final static Integer SUCCESS_CODE =  200;
    private  final static Integer ERROR_CODE = 201;

    private  final static String SUCCESS_MSG = "success";
    private  final static String ERROR_MSG = "error";

    public ResultVo setData(T data){
        this.data = data;
        return  this;
    }

    public static <T> ResultVo<T> success(String msg){
        return  new ResultVo<>(SUCCESS_CODE,msg,null);
    }
    public static <T> ResultVo<T> error(String msg){
        return  new ResultVo<>(ERROR_CODE,msg,null);
    }


    public static <T> ResultVo<T> success(){
        return  new ResultVo<>(SUCCESS_CODE,SUCCESS_MSG,null);
    }
    public static <T> ResultVo<T> error(){
        return  new ResultVo<>(ERROR_CODE,ERROR_MSG,null);
    }

}
