package com.sweetdum.mapnav.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mengxiao Lin on 2015/12/1.
 */
public class MainWindow extends JFrame{
    private BorderLayout mainLayout;
    private JTextField startPointTextField;
    private JTextField endPointTextField;
    private MapView mapView;

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
        mainLayout=new BorderLayout();

        mapView=new MapView();
        setLayout(mainLayout);
        add(mapView,BorderLayout.CENTER);
        mapView.setVisible(true);
        setTitle("MapNav");
        mapView.addMapClickedListener((int x, int y)->{
            System.out.println("X="+x+", Y="+y);
        });
        JPanel rightPanel=buildRightPanel();
        this.add(rightPanel,BorderLayout.EAST);
        pack();
    }

    public static void main(String args[]){
        MainWindow mainWindow=new MainWindow();
        mainWindow.pack();
        mainWindow.setSize(800,600);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
    }
}
