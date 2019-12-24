package com.homework.addressbook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.homework.addressbook.system.mapper"})
public class AddressbookApplication {
    public static void main(String[] args) {
        SpringApplication.run(AddressbookApplication.class, args);
    }

}
