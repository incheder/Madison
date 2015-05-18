package com.wezen.madison.model;

/**
 * Created by eder on 5/5/15.
 */
public class ItemForCheckout {

    private Integer addOrDeleteImageRes;
    private Integer amount;
    private Beverage beverage;
    private Double total;
    private ShoppingCartItemType type;


    public ItemForCheckout(){}

    public ItemForCheckout(Integer addOrDeleteImageRes, Integer amount, Beverage beverage,ShoppingCartItemType type, Double total){
        setAddOrDeleteImageRes(addOrDeleteImageRes);
        setAmount(amount);
        setBeverage(beverage);
        setType(type);
        setTotal(total);
    }


    public Integer getAddOrDeleteImageRes() {
        return addOrDeleteImageRes;
    }

    public void setAddOrDeleteImageRes(Integer addOrDeleteImageRes) {
        this.addOrDeleteImageRes = addOrDeleteImageRes;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Beverage getBeverage() {
        return beverage;
    }

    public void setBeverage(Beverage beverage) {
        this.beverage = beverage;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public ShoppingCartItemType getType() {
        return type;
    }

    public void setType(ShoppingCartItemType type) {
        this.type = type;
    }
}
