package com.sweetdum.mapnav.entity;

/**
 * Created by Mengxiao Lin on 2015/12/3.
 */
public class RoadEdge {
    private PositionNode source,target;
    private double distance;
    private int vehicleCode;
    public final static int CAR=1;
    public final static int WALK=2;
    public final static int BUS=4;

    public RoadEdge(PositionNode source, PositionNode target, double distance, int vehicleCode) {
        this.source = source;
        this.target = target;
        this.distance = distance;
        this.vehicleCode = vehicleCode;
    }

    public boolean allowCar(){
        return (vehicleCode & CAR)!=0;
    }
    public boolean allowWalk(){
        return (vehicleCode & WALK)!=0;
    }
    public boolean allowBus(){
        return (vehicleCode & BUS)!=0;
    }
    public PositionNode getSource() {
        return source;
    }

    public PositionNode getTarget() {
        return target;
    }

    public double getDistance() {
        return distance;
    }

    public RoadEdge reverse(){
        RoadEdge ret=new RoadEdge(target,source,distance,vehicleCode);
        return ret;
    }
}
