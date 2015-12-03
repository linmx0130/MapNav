package com.sweetdum.mapnav.dao;

import com.sweetdum.mapnav.entity.PositionNode;
import com.sweetdum.mapnav.entity.RoadEdge;
import com.sweetdum.mapnav.entity.RoadGraph;
import com.sweetdum.mapnav.entity.TagPosition;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2015/12/2.
 */
public class MapData {
    private ArrayList<TagPosition> tagPositions;
    private RoadGraph graph;
    private void readTagPositionsData(){
        File f=new File("res/tag.txt");
        tagPositions=new ArrayList<>();
        try {
            BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8"));
            String buf=null;
            do{
                buf=fin.readLine();
                if (buf==null) break;
                String mark=buf;
                buf=fin.readLine();
                if (buf==null) break;
                String name=buf;
                buf=fin.readLine();
                if (buf==null) break;
                Scanner scanner=new Scanner(buf);
                int x=scanner.nextInt(),y=scanner.nextInt();
                tagPositions.add(new TagPosition(mark,name,x,y));
                graph.addNode(mark);
            }while (buf!=null);
            fin.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void readRoadDistanceData(){
        File f=new File("res/mapdis.txt");
        try {
            BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8"));
            String buf=null;
            do{
                buf=fin.readLine();
                if (buf == null) break;
                if (buf.length()==0 || buf.charAt(0)=='#') continue;

                Scanner scanner=new Scanner(buf);
                PositionNode startNode =graph.getNode(scanner.next());
                PositionNode endNode= graph.getNode(scanner.next());
                double distance=scanner.nextDouble();
                int direction=scanner.nextInt();
                int vehicleCode=scanner.nextInt();
                RoadEdge edge=new RoadEdge(startNode,endNode,distance,vehicleCode);
                startNode.addEdge(edge);
                if (direction == 1){
                    RoadEdge e2=new RoadEdge(endNode,startNode,distance,vehicleCode);
                    endNode.addEdge(e2);
                }
            }while (buf!=null);
            fin.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ArrayList<TagPosition> getTagPositions() {
        return tagPositions;
    }

    public MapData(){
        graph=new RoadGraph();
        readTagPositionsData();
        readRoadDistanceData();
    }
}
