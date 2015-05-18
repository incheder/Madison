package com.wezen.madison.model;

/**
 * Created by eder on 5/14/15.
 */
public class NavigationDrawerItem {
    private Integer icon;
    private String title;

    public NavigationDrawerItem(){}

    public NavigationDrawerItem(Integer icon,String title){
        setIcon(icon);
        setTitle(title);
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
