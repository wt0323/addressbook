package com.homework.addressbook.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String userName;
    private String password;
    private String remark;
    private String nickName;
    private Integer lv;
}
