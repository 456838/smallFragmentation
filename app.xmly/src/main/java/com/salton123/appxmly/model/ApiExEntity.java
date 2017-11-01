package com.salton123.appxmly.model;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/14 20:51
 * ModifyTime: 20:51
 * Description:
 */
public class ApiExEntity {
    public int code ;

    public String errorMsg ;

    public ApiExEntity(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }
}
