package com.kaven.weixinsell.utils;

import com.kaven.weixinsell.vueObject.ResultVueObject;

import java.util.List;

public class ResultVueObjectUtil<T> {

    public static<T> ResultVueObject success(T resultVueObject){
        ResultVueObject result = new ResultVueObject();
        result.setData(resultVueObject);
        result.setCode(0);
        result.setMsg("成功");

        return result;
    }

    public static ResultVueObject success(){
        return success(null);
    }

    public static ResultVueObject error(Integer code,String msg){
        ResultVueObject result = new ResultVueObject();
        result.setCode(code);
        result.setMsg(msg);

        return result;
    }
}
