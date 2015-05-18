package com.wezen.madison.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eder on 4/13/15.
 */
public enum  BeverageType {
    BRANDY(0),
    CERVEZA(1),
    CHAMPAGNE(2),
    GINEBRA(3),
    LICOR(4),
    RON(5),
    TEQUILA(6),
    VINO(7),
    VODKA(8),
    WHISKEY(9);

    private final int value;
    private static Map<Integer,BeverageType> map = new HashMap<>();
    static {
        for(BeverageType bType : BeverageType.values()){
            map.put(bType.value,bType);
        }
    }

    private BeverageType(int value) {
        this.value = value;
    }

    public static BeverageType valueOf(int value){
        return  map.get(value);
    }

    public int getValue(){
        return value;
    }




}
