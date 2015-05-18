package com.wezen.madison.utils;

import android.content.Context;
import android.content.res.TypedArray;

import com.wezen.madison.R;
import com.wezen.madison.model.Beverage;
import com.wezen.madison.model.BeverageType;

import java.util.ArrayList;

/**
 * Created by eder on 4/13/15.
 */
public class Utils {

    private Utils(){}

    public static ArrayList<Beverage> fillDataSet(Context context, BeverageType type){
        ArrayList<Beverage> dataSet = new ArrayList<>();
        switch (type){
            case BRANDY:
                dataSet = createListWithArray(context,R.array.brandy,R.array.brandy_prices,R.array.brandy_images);
                break;
            case CERVEZA:
                dataSet = createListWithArray(context,R.array.beer,R.array.beer_prices,R.array.beer_images);
                break;
            case CHAMPAGNE:
                dataSet = createListWithArray(context,R.array.champagne,R.array.champagne_prices,R.array.champagne_images);
                break;
            case GINEBRA:
                dataSet = createListWithArray(context,R.array.ginebra,R.array.ginebra_prices,R.array.ginebra_images);
                break;
            case LICOR:
                dataSet = createListWithArray(context,R.array.licores,R.array.licores_prices,R.array.licores_images);
                break;
            case RON:
                dataSet = createListWithArray(context,R.array.ron,R.array.ron_prices,R.array.ron_images);
                break;
            case TEQUILA:
                dataSet = createListWithArray(context,R.array.tequila,R.array.tequila_prices,R.array.tequila_images);
                break;
            case VINO:
                dataSet = createListWithArray(context,R.array.vino,R.array.vino_prices,R.array.vino_images);
                break;
            case VODKA:
                dataSet = createListWithArray(context,R.array.vodka,R.array.vodka_prices,R.array.vodka_images);
                break;
            case WHISKEY:
                dataSet = createListWithArray(context,R.array.whiskey,R.array.whiskey_prices,R.array.whiskey_images);
                break;
        }
        return dataSet;
    }


    private static ArrayList<Beverage> createListWithArray(Context context, int nameArray, int priceArray, int imagesArray){
        ArrayList<Beverage> list = new ArrayList<>();
        String[] names = context.getResources().getStringArray(nameArray);
        String[] prices = context.getResources().getStringArray(priceArray);
        TypedArray images = context.getResources().obtainTypedArray(imagesArray);
        for(int i = 0; i < names.length; i++){
           Beverage mBeverage = new Beverage(names[i],prices[i],images.getResourceId(i,-1));
            list.add(mBeverage);
        }
        images.recycle();
        return list;
    }
}
