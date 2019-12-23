package com.homework.addressbook.entity;

import lombok.Data;

@Data
public class Navigation {
    private Integer id;
    private String navName;
    private Integer navLv;
    private Integer navParent;
    private String navUrl;
    private Integer navUse;
}
