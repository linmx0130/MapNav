package com.sweetdum.mapnav.ui;

import com.sweetdum.mapnav.business.FindShortestPath;
import com.sweetdum.mapnav.dao.MapData;
import com.sweetdum.mapnav.entity.ShortestPath;
import com.sweetdum.mapnav.entity.TagPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * 主界面
 * Created by Mengxiao Lin on 2015/12/1.
 */
public class MainWindow extends JFrame{
    private BorderLayout mainLayout;
    private JTextField startPointTextField;
    private JTextField endPointTextField;
    private MapView mapView;
    private MapData mapData;
    private TagPosition startPoint=null,endPoint=null;
    private JTextArea tipsLabel;
    private JMenuBar menuBar;

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

    private JPanel buildRightPanel(){
        JPanel rightPanel=new JPanel(new BorderLayout());
        JPanel rightPanelTop=new JPanel(new GridLayout(6,1));
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
                mapView.setShowPath(null,null);
                tipsLabel.setText("");
                startPointTextField.requestFocus();
            });
            rightPanelTop.add(resetButton);
        }
        {
            JButton walkButton = new JButton("走路");
            walkButton.addActionListener((event) -> {
                if (startPoint==null){
                    tipsLabel.setText("未设置起点！");
                    startPointTextField.requestFocus();
                    return ;
                }
                if (endPoint==null){
                    tipsLabel.setText("未设置终点！");
                    endPointTextField.requestFocus();
                    return ;
                }
                ShortestPath path=FindShortestPath.getShortestPathByWalk(mapData.getGraph(),startPoint.getMark(),endPoint.getMark());
                if (path.getEdges().size()==0){
                    tipsLabel.setText("无法走路到达目的地");
                    return ;
                }
                tipsLabel.setText("最短路径为图中绿色线所示，距离为"+String.format("%.2f",path.getLength())+"km。");
                mapView.setShowPath(path,mapData);
            });
            rightPanelTop.add(walkButton);
        }
        {
            JButton carButton = new JButton("开车");
            carButton.addActionListener((event) -> {
                if (startPoint==null){
                    tipsLabel.setText("未设置起点！");
                    startPointTextField.requestFocus();
                    return ;
                }
                if (endPoint==null){
                    tipsLabel.setText("未设置终点！");
                    endPointTextField.requestFocus();
                    return ;
                }
                ShortestPath path=FindShortestPath.getShortestPathByCar(mapData.getGraph(),startPoint.getMark(),endPoint.getMark());
                if (path.getEdges().size()==0){
                    tipsLabel.setText("无法通过开车到达目的地");
                    return;
                }
                tipsLabel.setText("最短路径为图中绿色线所示，距离为"+String.format("%.2f",path.getLength())+"km。");
                mapView.setShowPath(path,mapData);
            });
            rightPanelTop.add(carButton);
        }
        {
            JButton busButton = new JButton("公交");
            busButton.addActionListener((event) -> {
                if (startPoint==null){
                    tipsLabel.setText("未设置起点！");
                    startPointTextField.requestFocus();
                    return ;
                }
                if (endPoint==null){
                    tipsLabel.setText("未设置终点！");
                    endPointTextField.requestFocus();
                    return ;
                }
                ShortestPath path=FindShortestPath.getShortestPathByBus(mapData.getGraph(),startPoint.getMark(),endPoint.getMark());
                if (path.getEdges().size()==0){
                    tipsLabel.setText("无法通过公交到达目的地");
                    return;
                }
                tipsLabel.setText("最短路径为图中绿色和青色线所示，绿色为走路部分，青色为公交部分，用时为"+String.format("%.2f",path.getLength())+"min。");
                mapView.setShowPath(path,mapData);
            });
            rightPanelTop.add(busButton);
        }
        {
            tipsLabel=new JTextArea();
            tipsLabel.setEditable(false);
            tipsLabel.setBackground(this.getBackground());
            tipsLabel.setColumns(13);
            tipsLabel.setLineWrap(true);
            rightPanel.add(tipsLabel,BorderLayout.CENTER);

        }
        rightPanel.add(rightPanelTop,BorderLayout.NORTH);
        return rightPanel;
    }
    private void createMenuBar(){
        menuBar=new JMenuBar();
        JMenu toolsMenu=new JMenu("工具");
        JMenu helpMenu=new JMenu("帮助");
        {
            JMenuItem about = new JMenuItem("关于");
            about.addActionListener((evt) -> {
                JOptionPane.showConfirmDialog(MainWindow.this,
                        "MapNav\nCopyright(c), 2015 Mengxiao Lin\n All rights reserved.\n"+
                        "Released under the BSD 3-Clause License.",
                        "关于",
                        JOptionPane.CLOSED_OPTION, JOptionPane.PLAIN_MESSAGE
                );
            });
            helpMenu.add(about);
        }
        menuBar.add(toolsMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
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
        createMenuBar();
    }


    public static void main(String args[]){
        MainWindow mainWindow=new MainWindow();
        mainWindow.pack();
        mainWindow.setSize(900,600);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
    }
}
