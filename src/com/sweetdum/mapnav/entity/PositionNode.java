package com.sweetdum.mapnav.entity;

import java.util.ArrayList;

/**
 * 抽象图中的地标节点
 * Created by Mengxiao Lin on 2015/12/3.
 */
public class PositionNode {
    private String mark;
    private ArrayList<RoadEdge> edges;

    public PositionNode(String mark) {
        this.mark = mark;
        edges=new ArrayList<>();
    }

    public ArrayList<RoadEdge> getEdges() {
        return edges;
    }

    public void addEdge(RoadEdge e){
        edges.add(e);
    }

    public String getMark() {
        return mark;
    }

}
