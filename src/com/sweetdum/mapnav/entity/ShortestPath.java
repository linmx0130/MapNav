package com.sweetdum.mapnav.entity;

import java.util.ArrayList;

/**
 * 用于在最短路算法和地图展示对象之间传递最短路径
 * Created by Mengxiao Lin on 2015/12/3.
 */
public class ShortestPath {
    private ArrayList<RoadEdge> nodes;
    private double length;
    public ShortestPath(){
        nodes=new ArrayList<>();
    }

    public ArrayList<RoadEdge> getEdges() {
        return nodes;
    }

    public void addEdge(RoadEdge edge) {
        this.nodes.add(edge);
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
