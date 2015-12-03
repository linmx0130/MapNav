package com.sweetdum.mapnav.entity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mengxiao Lin on 2015/12/3.
 */
public class RoadGraph {
    private HashMap<String,PositionNode> nodes;

    public RoadGraph() {
        nodes=new HashMap<>();
    }
    public void addNode(String mark){
        PositionNode node=new PositionNode(mark);
        nodes.put(mark,node);
    }

    public PositionNode getNode(String mark){
        return nodes.get(mark);
    }

}
