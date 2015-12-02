package com.sweetdum.mapnav.entity;

/**
 * Created by Mengxiao Lin on 2015/12/2.
 */
public class TagPosition {
    private String mark;
    private String name;
    private int x,y;
    public TagPosition(String mark, String name, int x,int y){
        this.mark=mark;
        this.name=name;
        this.x=x;
        this.y=y;
    }
    public String getMark() {
        return mark;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
