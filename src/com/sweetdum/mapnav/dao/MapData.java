package com.sweetdum.mapnav.dao;

import com.sweetdum.mapnav.entity.TagPosition;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Mengxiao Lin on 2015/12/2.
 */
public class MapData {
    private ArrayList<TagPosition> tagPositions;
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
            }while (buf!=null);
            fin.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<TagPosition> getTagPositions() {
        return tagPositions;
    }

    public MapData(){
        readTagPositionsData();
    }
}
