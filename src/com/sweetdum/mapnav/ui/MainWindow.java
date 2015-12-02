package com.sweetdum.mapnav.ui;

import com.sweetdum.mapnav.dao.MapData;
import com.sweetdum.mapnav.entity.TagPosition;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Mengxiao Lin on 2015/12/1.
 */
public class MainWindow extends JFrame{
    private BorderLayout mainLayout;
    private JTextField startPointTextField;
    private JTextField endPointTextField;
    private MapView mapView;
    private MapData mapData;

    public JPanel buildRightPanel(){
        JPanel rightPanel=new JPanel(new BorderLayout());
        JPanel rightPanelTop=new JPanel(new GridLayout(2,1));
        {
            JLabel startPointTextFieldLabel=new JLabel("起点：");
            startPointTextField=new JTextField();
            JPanel panel=new JPanel(new FlowLayout());
            panel.add(startPointTextFieldLabel);
            panel.add(startPointTextField);
            startPointTextField.setColumns(10);
            rightPanelTop.add(panel);
        }
        {
            JLabel endPointTextFieldLabel=new JLabel("终点：");
            endPointTextField=new JTextField();
            endPointTextField.setColumns(10);
            JPanel panel=new JPanel(new FlowLayout());
            panel.add(endPointTextFieldLabel);
            panel.add(endPointTextField);
            rightPanelTop.add(panel);
        }
        rightPanel.add(rightPanelTop,BorderLayout.NORTH);
        return rightPanel;
    }
    public MainWindow(){
        mainLayout=new BorderLayout(2,2);

        mapView=new MapView();
        setLayout(mainLayout);
        add(mapView,BorderLayout.CENTER);
        mapView.setVisible(true);
        setTitle("MapNav");
        JPanel rightPanel=buildRightPanel();
        this.add(rightPanel,BorderLayout.EAST);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mapData=new MapData();
        mapView.addMapClickedListener((int x, int y)->{
            System.out.println("X="+x+", Y="+y);

            //get the text field own the focus
            JTextField focusOwner=null;
            if (startPointTextField.isFocusOwner()) focusOwner=startPointTextField;
            if (endPointTextField.isFocusOwner()) focusOwner=endPointTextField;
            if (focusOwner==null) return ;

            ArrayList<TagPosition> tagPositions=mapData.getTagPositions();
            TagPosition chosen=tagPositions.get(0);
            int dis=(x-chosen.getX())*(x-chosen.getX())+(y-chosen.getY())*(y-chosen.getY());
            for (TagPosition tg: tagPositions){
                int tmp=(x-tg.getX())*(x-tg.getX())+(y-tg.getY())*(y-tg.getY());
                if (tmp<dis){
                    dis=tmp;
                    chosen=tg;
                }
            }

            focusOwner.setText(chosen.getName());
        });
    }


    public static void main(String args[]){
        MainWindow mainWindow=new MainWindow();
        mainWindow.pack();
        mainWindow.setSize(900,600);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
    }
}
