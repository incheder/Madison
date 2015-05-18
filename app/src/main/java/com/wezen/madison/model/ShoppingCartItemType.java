package com.wezen.madison.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eder on 5/7/15.
 */
public enum ShoppingCartItemType {
    NORMAL_ITEM(0),
    TOTAL(1);

    private final int value;
    private static Map<Integer,ShoppingCartItemType> map = new HashMap<>();
    static {
        for(ShoppingCartItemType bType : ShoppingCartItemType.values()){
            map.put(bType.value,bType);
        }
    }

    private ShoppingCartItemType(int value) {
        this.value = value;
    }

    public static ShoppingCartItemType valueOf(int value){
        return  map.get(value);
    }

    public int getValue(){
        return value;
    }




}
