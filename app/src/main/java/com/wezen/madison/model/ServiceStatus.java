package com.wezen.madison.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eder on 4/13/15.
 */
public enum ServiceStatus {
    COMPLETO(0),
    POR_REALIZAR(1),
    CANCELADO(2);

    private final int value;
    private static Map<Integer,ServiceStatus> map = new HashMap<>();
    static {
        for(ServiceStatus bType : ServiceStatus.values()){
            map.put(bType.value,bType);
        }
    }

    private ServiceStatus(int value) {
        this.value = value;
    }

    public static ServiceStatus valueOf(int value){
        return  map.get(value);
    }

    public int getValue(){
        return value;
    }




}
