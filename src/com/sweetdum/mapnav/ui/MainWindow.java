package com.sweetdum.mapnav.ui;

import com.sweetdum.mapnav.business.FindShortestPath;
import com.sweetdum.mapnav.dao.MapData;
import com.sweetdum.mapnav.entity.PositionNode;
import com.sweetdum.mapnav.entity.ShortestPath;
import com.sweetdum.mapnav.entity.TagPosition;

import javax.swing.*;
import javax.swing.text.Keymap;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private TagPosition startPoint=null,endPoint=null;

    public void setStartPoint(TagPosition startPoint) {
        this.startPoint = startPoint;
        if (startPoint!=null) {
            mapView.setSourcePoint(new Point(startPoint.getX(), startPoint.getY()));
        }else {
            mapView.setSourcePoint(null);
        }
    }

    public void setEndPoint(TagPosition endPoint) {
        this.endPoint = endPoint;
        if (endPoint!=null) {
            mapView.setTargetPoint(new Point(endPoint.getX(), endPoint.getY()));
        }else{
            mapView.setTargetPoint(null);
        }
    }

    public JPanel buildRightPanel(){
        JPanel rightPanel=new JPanel(new BorderLayout());
        JPanel rightPanelTop=new JPanel(new GridLayout(4,1));
        {
            JLabel startPointTextFieldLabel=new JLabel("起点：");
            startPointTextField=new JTextField();
            JPanel panel=new JPanel(new FlowLayout());
            panel.add(startPointTextFieldLabel);
            panel.add(startPointTextField);
            startPointTextField.setColumns(10);
            startPointTextField.addKeyListener(new KeyAdapter() {

                @Override
                public void keyTyped(KeyEvent e) {
                    super.keyTyped(e);
                    setStartPoint(null);
                }
            });
            rightPanelTop.add(panel);
        }
        {
            JLabel endPointTextFieldLabel=new JLabel("终点：");
            endPointTextField=new JTextField();
            endPointTextField.setColumns(10);
            JPanel panel=new JPanel(new FlowLayout());
            panel.add(endPointTextFieldLabel);
            panel.add(endPointTextField);
            endPointTextField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    super.keyPressed(e);
                    setEndPoint(null);
                }
            });
            rightPanelTop.add(panel);
        }
        {
            JButton resetButton = new JButton("重置");
            resetButton.addActionListener((event) -> {
                startPointTextField.setText("");
                endPointTextField.setText("");
                mapView.setTargetPoint(null);
                mapView.setSourcePoint(null);
                startPointTextField.requestFocus();
            });
            rightPanelTop.add(resetButton);
        }
        {
            JButton walkButton = new JButton("走路");
            walkButton.addActionListener((event) -> {
                if (startPoint==null || endPoint==null){
                    return ;
                }
                ShortestPath path=FindShortestPath.getShortestPathByWalk(mapData.getGraph(),startPoint.getMark(),endPoint.getMark());
                //TODO present the result
                System.out.println(path.getLength());
                for (PositionNode node:path.getNodes()){
                    System.out.println(node.getMark());
                }
            });
            rightPanelTop.add(walkButton);
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
            //System.out.println("X="+x+", Y="+y);

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
            if (focusOwner==startPointTextField){
                setStartPoint(chosen);
                endPointTextField.requestFocus();
            }
            if (focusOwner==endPointTextField){
                setEndPoint(chosen);
            }
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
