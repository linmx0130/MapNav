package com.sweetdum.mapnav.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mengxiao Lin on 2015/12/1.
 */
public class MainWindow extends JFrame{
    private BorderLayout mainLayout;
    private MapView mapView;
    public MainWindow(){
        mainLayout=new BorderLayout();
        mapView=new MapView();
        setLayout(mainLayout);
        add(mapView,BorderLayout.CENTER);
        pack();
        mapView.setVisible(true);
        setTitle("MapNav");
        mapView.addMapClickedListener((int x, int y)->{
            System.out.println("X="+x+", Y="+y);
        });
    }

    public static void main(String args[]){
        MainWindow mainWindow=new MainWindow();
        mainWindow.pack();
        mainWindow.setSize(800,600);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
    }
}
