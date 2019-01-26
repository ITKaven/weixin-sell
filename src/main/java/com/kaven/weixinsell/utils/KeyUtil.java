package com.kaven.weixinsell.utils;

import java.util.Random;

public class KeyUtil {

    public static synchronized String  getUniqueKey(){
        Integer number = (new Random()).nextInt(900000)+100000;
        return System.currentTimeMillis()+number.toString();
    }
}
