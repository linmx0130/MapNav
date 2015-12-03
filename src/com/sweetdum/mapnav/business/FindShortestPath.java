package com.sweetdum.mapnav.business;

import com.sweetdum.mapnav.entity.PositionNode;
import com.sweetdum.mapnav.entity.RoadEdge;
import com.sweetdum.mapnav.entity.RoadGraph;
import com.sweetdum.mapnav.entity.ShortestPath;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Mengxiao Lin on 2015/12/3.
 */
public class FindShortestPath {
    public static ShortestPath getShortestPathByWalk(RoadGraph graph, String sourceMark, String targetMark) {
        PositionNode source = graph.getNode(sourceMark);
        PositionNode target = graph.getNode(targetMark);
        HashMap<PositionNode, Double> dis = new HashMap<>();
        Queue<PositionNode> queue = new LinkedBlockingQueue<>();
        HashSet<PositionNode> inQueue = new HashSet<>();
        queue.add(source);
        inQueue.add(source);
        HashMap<PositionNode, PositionNode> previousNode = new HashMap<>();
        previousNode.put(source, null);
        dis.put(source,0.0);

        while (!queue.isEmpty()) {
            PositionNode now = queue.remove();
            inQueue.remove(now);
            double nowDis = dis.get(now);
            for (RoadEdge e : now.getEdges()) {
                if (!e.allowWalk()) continue;
                PositionNode next = e.getTarget();
                if (!dis.containsKey(next)) {
                    dis.put(next, 2147483647.0);
                }
                if (dis.get(next) > nowDis + e.getDistance()) {
                    dis.put(next, nowDis + e.getDistance());
                    previousNode.put(next, now);
                    if (!inQueue.contains(next)) {
                        queue.add(next);
                        inQueue.add(next);
                    }
                }
            }
        }
        ShortestPath ret = new ShortestPath();
        PositionNode ptr=target;
        ret.setLength(dis.get(target));
        while (ptr!=null){
            ret.addNode(ptr);
            ptr=previousNode.get(ptr);
        }
        return ret;
    }
}
