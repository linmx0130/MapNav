package com.sweetdum.mapnav.entity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 地图抽象图类，实现了邻接链表，最短路算法在该数据结构上执行
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
    public ArrayList<String> getNodesMark(){
        ArrayList<String> ret=new ArrayList<>();
        for (String s:nodes.keySet()) ret.add(s);
        return ret;
    }
    public PositionNode getNode(String mark){
        return nodes.get(mark);
    }
    public RoadGraph reverse(){
        RoadGraph ret=new RoadGraph();
        for (String mark:nodes.keySet()){
            ret.addNode(mark);
        }
        for (String mark:nodes.keySet()){
            PositionNode node=nodes.get(mark);
            for (RoadEdge e:node.getEdges()){
                RoadEdge ne=e.reverse();
                ret.getNode(ne.getSource().getMark()).addEdge(ne);
            }
        }
        return ret;
    }
}
