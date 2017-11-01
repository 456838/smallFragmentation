package com.salton123.appxmly.wrap;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/14 20:53
 * ModifyTime: 20:53
 * Description:
 */
public class ApiException extends Throwable {
    public  Object object;
    public ApiException(Object entiy){
        this.object = entiy;
    }
}
