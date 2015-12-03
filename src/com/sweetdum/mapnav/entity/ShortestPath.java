package com.sweetdum.mapnav.entity;

import java.util.ArrayList;

/**
 * Created by Mengxiao Lin on 2015/12/3.
 */
public class ShortestPath {
    private ArrayList<PositionNode> nodes;
    private double length;
    public ShortestPath(){
        nodes=new ArrayList<>();
    }

    public ArrayList<PositionNode> getNodes() {
        return nodes;
    }

    public void addNode(PositionNode node) {
        this.nodes.add(node);
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
