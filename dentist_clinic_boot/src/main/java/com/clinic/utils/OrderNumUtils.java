package com.clinic.utils;

import java.util.Date;

public class OrderNumUtils {
    public static String getOrderNum() {
        Long num = new Date().getTime()/100+1234567890;
        return num.toString();
    }


}
