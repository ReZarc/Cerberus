package com.clinic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.clinic.mapper")
public class ClinicAPP {

    public static void main(String[] args) {
        SpringApplication.run(ClinicAPP.class,args);
    }

}
