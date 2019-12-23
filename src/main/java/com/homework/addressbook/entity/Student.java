package com.homework.addressbook.entity;

import lombok.Data;
@Data
public class Student {
    private Integer id;
    private String loginName;
    private String password;
    private String name;
    private Integer sex;
    private Integer majorId;
    private Integer studentClass;
    private String inSchool;
    private String leaveSchool;
    private String workLocation;
    private String city;
    private String phone;
    private String email;
    private Integer status;
    private Integer loginNum;
    private String loginTime;
    private Integer type;

    private String major;
}
