package com.sweetdum.mapnav.business;

import com.sweetdum.mapnav.entity.PositionNode;
import com.sweetdum.mapnav.entity.RoadEdge;
import com.sweetdum.mapnav.entity.RoadGraph;
import com.sweetdum.mapnav.entity.ShortestPath;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 包含所有用到的最短路算法的实现。
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
        HashMap<PositionNode, RoadEdge> previousEdge = new HashMap<>();
        previousEdge.put(source, null);
        dis.put(source,0.0);

        while (!queue.isEmpty()) {
            PositionNode now = queue.remove();
            inQueue.remove(now);
            double nowDis = dis.get(now);
            for (RoadEdge e : now.getEdges()) {
                if (!e.allowWalk()) continue;
                PositionNode next = e.getTarget();
                if (!dis.containsKey(next)) {
                    dis.put(next, Double.MAX_VALUE);
                }
                if (dis.get(next) > nowDis + e.getDistance()) {
                    dis.put(next, nowDis + e.getDistance());
                    previousEdge.put(next, e);
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
        while (previousEdge.get(ptr)!=null){
            ret.addEdge(previousEdge.get(ptr));
            ptr=previousEdge.get(ptr).getSource();
        }
        return ret;
    }
    public static ShortestPath getShortestPathByCar(RoadGraph graph, String sourceMark, String targetMark) {
        PositionNode source = graph.getNode(sourceMark);
        PositionNode target = graph.getNode(targetMark);
        HashMap<PositionNode, Double> dis = new HashMap<>();
        Queue<PositionNode> queue = new LinkedBlockingQueue<>();
        HashSet<PositionNode> inQueue = new HashSet<>();
        queue.add(source);
        inQueue.add(source);
        HashMap<PositionNode, RoadEdge> previousEdge = new HashMap<>();
        previousEdge.put(source, null);
        dis.put(source,0.0);

        while (!queue.isEmpty()) {
            PositionNode now = queue.remove();
            inQueue.remove(now);
            double nowDis = dis.get(now);
            for (RoadEdge e : now.getEdges()) {
                if (!e.allowCar()) continue;
                PositionNode next = e.getTarget();
                if (!dis.containsKey(next)) {
                    dis.put(next, Double.MAX_VALUE);
                }
                if (dis.get(next) > nowDis + e.getDistance()) {
                    dis.put(next, nowDis + e.getDistance());
                    previousEdge.put(next, e);
                    if (!inQueue.contains(next)) {
                        queue.add(next);
                        inQueue.add(next);
                    }
                }
            }
        }
        ShortestPath ret = new ShortestPath();
        PositionNode ptr=target;
        if (!dis.containsKey(target)){
            return ret;
        }
        ret.setLength(dis.get(target));
        while (previousEdge.get(ptr)!=null){
            ret.addEdge(previousEdge.get(ptr));
            ptr=previousEdge.get(ptr).getSource();
        }
        return ret;
    }
    public static ShortestPath getShortestPathByBus(RoadGraph graph, String sourceMark, String targetMark) {
        PositionNode source = graph.getNode(sourceMark);
        PositionNode target = graph.getNode(targetMark);
        HashMap<PositionNode, Double> dis = new HashMap<>();
        Queue<PositionNode> queue = new LinkedBlockingQueue<>();
        HashSet<PositionNode> inQueue = new HashSet<>();
        queue.add(source);
        inQueue.add(source);
        HashMap<PositionNode, RoadEdge> previousEdge = new HashMap<>();
        previousEdge.put(source, null);
        dis.put(source,0.0);

        while (!queue.isEmpty()) {
            PositionNode now = queue.remove();
            inQueue.remove(now);
            double nowDis = dis.get(now);
            for (RoadEdge e : now.getEdges()) {
                if (e.allowWalk()) {
                    PositionNode next = e.getTarget();
                    if (!dis.containsKey(next)) {
                        dis.put(next, Double.MAX_VALUE);
                    }
                    double nowEdgeDis=e.getDistance()/0.07;
                    if (dis.get(next) > nowDis + nowEdgeDis) {
                        dis.put(next, nowDis + nowEdgeDis);
                        previousEdge.put(next, e);
                        if (!inQueue.contains(next)) {
                            queue.add(next);
                            inQueue.add(next);
                        }
                    }
                }
                if (e.allowBus()){
                    PositionNode next = e.getTarget();
                    if (!dis.containsKey(next)) {
                        dis.put(next, Double.MAX_VALUE);
                    }
                    if (dis.get(next) > nowDis + e.getDistance()) {
                        dis.put(next, nowDis + e.getDistance());
                        previousEdge.put(next, e);
                        if (!inQueue.contains(next)) {
                            queue.add(next);
                            inQueue.add(next);
                        }
                    }
                }
            }
        }
        ShortestPath ret = new ShortestPath();
        PositionNode ptr=target;
        if (!dis.containsKey(target)){
            return ret;
        }
        ret.setLength(dis.get(target));
        while (previousEdge.get(ptr)!=null){
            ret.addEdge(previousEdge.get(ptr));
            ptr=previousEdge.get(ptr).getSource();
        }
        return ret;
    }
    public static HashMap<String,Double> getShortestPathToOnePlaceByWalk(RoadGraph graphAtFirst, String sourceMark) {
        RoadGraph graph=graphAtFirst.reverse();
        PositionNode source = graph.getNode(sourceMark);
        HashMap<PositionNode, Double> dis = new HashMap<>();
        Queue<PositionNode> queue = new LinkedBlockingQueue<>();
        HashSet<PositionNode> inQueue = new HashSet<>();
        queue.add(source);
        inQueue.add(source);
        HashMap<PositionNode, RoadEdge> previousEdge = new HashMap<>();
        previousEdge.put(source, null);
        dis.put(source,0.0);

        while (!queue.isEmpty()) {
            PositionNode now = queue.remove();
            inQueue.remove(now);
            double nowDis = dis.get(now);
            for (RoadEdge e : now.getEdges()) {
                if (!e.allowWalk()) continue;
                PositionNode next = e.getTarget();
                if (!dis.containsKey(next)) {
                    dis.put(next, Double.MAX_VALUE);
                }
                if (dis.get(next) > nowDis + e.getDistance()) {
                    dis.put(next, nowDis + e.getDistance());
                    previousEdge.put(next, e);
                    if (!inQueue.contains(next)) {
                        queue.add(next);
                        inQueue.add(next);
                    }
                }
            }
        }

        HashMap<String, Double> ret=new HashMap<>();
        for (PositionNode node:dis.keySet()){
            ret.put(node.getMark(),dis.get(node));
        }
        return ret;
    }
    public static HashMap<String,Double> getShortestPathToOnePlaceByCar(RoadGraph graphAtFirst, String sourceMark) {
        RoadGraph graph=graphAtFirst.reverse();
        PositionNode source = graph.getNode(sourceMark);
        HashMap<PositionNode, Double> dis = new HashMap<>();
        Queue<PositionNode> queue = new LinkedBlockingQueue<>();
        HashSet<PositionNode> inQueue = new HashSet<>();
        queue.add(source);
        inQueue.add(source);
        HashMap<PositionNode, RoadEdge> previousEdge = new HashMap<>();
        previousEdge.put(source, null);
        dis.put(source,0.0);

        while (!queue.isEmpty()) {
            PositionNode now = queue.remove();
            inQueue.remove(now);
            double nowDis = dis.get(now);
            for (RoadEdge e : now.getEdges()) {
                if (!e.allowCar()) continue;
                PositionNode next = e.getTarget();
                if (!dis.containsKey(next)) {
                    dis.put(next, Double.MAX_VALUE);
                }
                if (dis.get(next) > nowDis + e.getDistance()) {
                    dis.put(next, nowDis + e.getDistance());
                    previousEdge.put(next, e);
                    if (!inQueue.contains(next)) {
                        queue.add(next);
                        inQueue.add(next);
                    }
                }
            }
        }

        HashMap<String, Double> ret=new HashMap<>();
        for (PositionNode node:dis.keySet()){
            ret.put(node.getMark(),dis.get(node));
        }
        return ret;
    }
    public static HashMap<String,Double> getShortestPathToOnePlaceByBus(RoadGraph graphAtFirst, String sourceMark) {
        RoadGraph graph=graphAtFirst.reverse();
        PositionNode source = graph.getNode(sourceMark);
        HashMap<PositionNode, Double> dis = new HashMap<>();
        Queue<PositionNode> queue = new LinkedBlockingQueue<>();
        HashSet<PositionNode> inQueue = new HashSet<>();
        queue.add(source);
        inQueue.add(source);
        HashMap<PositionNode, RoadEdge> previousEdge = new HashMap<>();
        previousEdge.put(source, null);
        dis.put(source,0.0);

        while (!queue.isEmpty()) {
            PositionNode now = queue.remove();
            inQueue.remove(now);
            double nowDis = dis.get(now);
            for (RoadEdge e : now.getEdges()) {
                if (e.allowWalk()) {
                    PositionNode next = e.getTarget();
                    if (!dis.containsKey(next)) {
                        dis.put(next, Double.MAX_VALUE);
                    }
                    double nowEdgeDis=e.getDistance()/0.07;
                    if (dis.get(next) > nowDis + nowEdgeDis) {
                        dis.put(next, nowDis + nowEdgeDis);
                        previousEdge.put(next, e);
                        if (!inQueue.contains(next)) {
                            queue.add(next);
                            inQueue.add(next);
                        }
                    }
                }
                if (e.allowBus()){
                    PositionNode next = e.getTarget();
                    if (!dis.containsKey(next)) {
                        dis.put(next, Double.MAX_VALUE);
                    }
                    if (dis.get(next) > nowDis + e.getDistance()) {
                        dis.put(next, nowDis + e.getDistance());
                        previousEdge.put(next, e);
                        if (!inQueue.contains(next)) {
                            queue.add(next);
                            inQueue.add(next);
                        }
                    }
                }
            }
        }

        HashMap<String, Double> ret=new HashMap<>();
        for (PositionNode node:dis.keySet()){
            ret.put(node.getMark(),dis.get(node));
        }
        return ret;
    }
    private interface EdgeFilter{
        boolean test(RoadEdge e);
    }
    private static double[][] buildAdjacentMatrixByFilter(RoadGraph graph,  HashMap<String, Integer> markIdMap,EdgeFilter filter){
        ArrayList<String> nodesName=graph.getNodesMark();
        for (int i=0;i<nodesName.size();++i){
            markIdMap.put(nodesName.get(i),i);
        }
        double[][] ret=new double[nodesName.size()][nodesName.size()];
        for (int i=0;i<ret.length;++i){
            for (int j=0;j<ret[i].length;++j){
                ret[i][j]=Double.MAX_VALUE;
            }
            ret[i][i]=0;
        }
        for (int i=0;i<nodesName.size();++i){
            String mark=nodesName.get(i);
            PositionNode n=graph.getNode(mark);
            for (RoadEdge e:n.getEdges()){
                if (!filter.test(e)) continue;
                int tId=markIdMap.get(e.getTarget().getMark());
                ret[i][tId]=Math.min(ret[i][tId],e.getDistance());
            }
        }
        return ret;
    }
    public static double[][] buildAdjacentMatrixByWalk(RoadGraph graph,  HashMap<String, Integer> markIdMap){
        return buildAdjacentMatrixByFilter(graph,markIdMap,(e)-> e.allowWalk());
    }
    public static double[][] buildAdjacentMatrixByCar(RoadGraph graph,  HashMap<String, Integer> markIdMap){
        return buildAdjacentMatrixByFilter(graph,markIdMap,(e)-> e.allowCar());
    }
    public static double[][] buildAdjacentMatrixByBus(RoadGraph graph,  HashMap<String, Integer> markIdMap){
        ArrayList<String> nodesName=graph.getNodesMark();
        for (int i=0;i<nodesName.size();++i){
            markIdMap.put(nodesName.get(i),i);
        }
        double[][] ret=new double[nodesName.size()][nodesName.size()];
        for (int i=0;i<ret.length;++i){
            for (int j=0;j<ret[i].length;++j){
                ret[i][j]=Double.MAX_VALUE;
            }
            ret[i][i]=0;
        }
        for (int i=0;i<nodesName.size();++i){
            String mark=nodesName.get(i);
            PositionNode n=graph.getNode(mark);
            for (RoadEdge e:n.getEdges()){
                if (e.allowBus()) {
                    int tId = markIdMap.get(e.getTarget().getMark());
                    ret[i][tId] = Math.min(ret[i][tId],e.getDistance());
                }
                if (e.allowWalk()){
                    int tId = markIdMap.get(e.getTarget().getMark());
                    ret[i][tId] = Math.min(ret[i][tId],e.getDistance()/0.07);
                }
            }
        }
        return ret;
    }
    private static void floydAlgorithm(double[][] ret){
        int n=ret.length;
        for (int k=0;k<n;++k){
            for (int i=0;i<n;++i){
                for (int j=0;j<n;++j){
                    ret[i][j]=Math.min(ret[i][j],ret[i][k]+ret[k][j]);
                }
            }
        }
    }
    public static double[][] allShortestPathByWalk(RoadGraph graph, HashMap<String, Integer> markIdMap){
        double[][] ret=buildAdjacentMatrixByWalk(graph,markIdMap);
        floydAlgorithm(ret);
        return ret;
    }
    public static double[][] allShortestPathByCar(RoadGraph graph, HashMap<String, Integer> markIdMap){
        double[][] ret=buildAdjacentMatrixByCar(graph,markIdMap);
        floydAlgorithm(ret);
        return ret;
    }
    public static double[][] allShortestPathByBus(RoadGraph graph, HashMap<String, Integer> markIdMap){
        double[][] ret=buildAdjacentMatrixByBus(graph,markIdMap);
        floydAlgorithm(ret);
        return ret;
    }
}
