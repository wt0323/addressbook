package com.homework.addressbook.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)//关于使用标签去除json数据中的空值问题
public class LayuiReturn {

    private Integer code;

    private String msg;

    private Integer count;

    private Object data;

    //0成功，1失败
    public static LayuiReturn success(int count ,Object data){
        LayuiReturn layuiReturn = new LayuiReturn();
        layuiReturn.setCode(0);
        layuiReturn.setCount(count);
        layuiReturn.setData(data);
        return layuiReturn;
    }

    public static LayuiReturn fail() {
        LayuiReturn layuiReturn = new LayuiReturn();
        layuiReturn.setCode(1);
        return layuiReturn;
    }
}
