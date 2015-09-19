package com.wezen.madison.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eder on 4/13/15.
 */
public enum HomeServiceStatus {
    COMPLETO(0),
    POR_REALIZAR(1),
    CANCELADO(2);

    private final int value;
    private static Map<Integer,HomeServiceStatus> map = new HashMap<>();
    static {
        for(HomeServiceStatus bType : HomeServiceStatus.values()){
            map.put(bType.value,bType);
        }
    }

    private HomeServiceStatus(int value) {
        this.value = value;
    }

    public static HomeServiceStatus valueOf(int value){
        return  map.get(value);
    }

    public int getValue(){
        return value;
    }




}
