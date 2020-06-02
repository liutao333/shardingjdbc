package com.example.demo.util;


import com.cbim.common.exception.CoreExceptionEnum;
import com.cbim.common.vo.MessageEntity;

public class ResultUtil {

    public static MessageEntity success(Object data){

        return new MessageEntity(CoreExceptionEnum.OK.getCode(), data, "成功");

    }

    public static MessageEntity fail(Exception e){
        e.printStackTrace();
        return new MessageEntity(CoreExceptionEnum.E_FAIL.getCode(), e.getLocalizedMessage(),e.getLocalizedMessage());

    }
    public static MessageEntity fail(String e){
        return new MessageEntity(CoreExceptionEnum.E_FAIL.getCode(), e, e);
    }

}
