package com.wezen.madison.utils;

/**
 * Created by eder on 05/01/2016.
 */
public class ToolbarColorSinleton {

    private static ToolbarColorSinleton instance = null;

    private Integer statusBarcolor;
    private Integer toolbarColor;
    private Integer fabColor;

    private ToolbarColorSinleton(){}

    public static ToolbarColorSinleton getInstance(){
        if(instance == null){
            instance = new ToolbarColorSinleton();
        }
        return instance;
    }

    public Integer getStatusBarcolor() {
        return statusBarcolor;
    }

    public void setStatusBarcolor(Integer statusBarcolor) {
        this.statusBarcolor = statusBarcolor;
    }

    public Integer getToolbarColor() {
        return toolbarColor;
    }

    public void setToolbarColor(Integer toolbarColor) {
        this.toolbarColor = toolbarColor;
    }

    public Integer getFabColor() {
        return fabColor;
    }

    public void setFabColor(Integer fabColor) {
        this.fabColor = fabColor;
    }
}
