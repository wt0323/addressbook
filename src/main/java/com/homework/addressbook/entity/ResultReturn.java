package com.homework.addressbook.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 封装响应状态和信息        0成功，  1失败
 * @author 康言先森
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultReturn{

    private Integer code; // 0：代表成功 1：代表失败
    private String msg;

    private Object data;

    /**
     *
     * @param msg 返回信息
     * @return 返回成功状态,带数据
     */
    public static ResultReturn success(String msg) {
        ResultReturn ResultReturn = new ResultReturn();
        ResultReturn.setCode(0);
        ResultReturn.setMsg(msg);
        return ResultReturn;
    }
    public static ResultReturn success(String msg,Object data) {
        ResultReturn ResultReturn = new ResultReturn();
        ResultReturn.setCode(0);
        ResultReturn.setMsg(msg);
        ResultReturn.setData(data);
        return ResultReturn;
    }


    /**
     *
     * @return返回成功不带数据
     */
    public static ResultReturn success() {
        ResultReturn ResultReturn = new ResultReturn();
        ResultReturn.setCode(0);
        return ResultReturn;
    }

    public static ResultReturn success(Object data){
        ResultReturn ResultReturn = new ResultReturn();
        ResultReturn.setCode(0);
        ResultReturn.setData(data);
        return ResultReturn;
    }


    /**
     *
     * @param msg 返回信息
     * @return 返回失败，带数据
     */
    public static ResultReturn fail(String msg) {
        ResultReturn ResultReturn = new ResultReturn();
        ResultReturn.setCode(1);
        ResultReturn.setMsg(msg);
        return ResultReturn;
    }

    public static ResultReturn fail() {
        ResultReturn ResultReturn = new ResultReturn();
        ResultReturn.setCode(1);
        return ResultReturn;
    }
}
