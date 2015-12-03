package com.sweetdum.mapnav.entity;

import java.util.ArrayList;

/**
 * Created by Mengxiao Lin on 2015/12/3.
 */
public class PositionNode {
    private String mark;
    private ArrayList<RoadEdge> edges;

    public PositionNode(String mark) {
        this.mark = mark;
        edges=new ArrayList<>();
    }

    public void addEdge(RoadEdge e){
        edges.add(e);
    }
}
