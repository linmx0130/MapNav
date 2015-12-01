package com.sweetdum.mapnav.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Mengxiao Lin on 2015/12/1.
 */
public class MapView extends JComponent {
    private BufferedImage mapImageFile;
    private double scale=1;
    private double centerX=0,centerY=0;
    private ArrayList<MapClickedListener> clickedListeners;

    private int centerPixelX,centerPixelY,halfWidth,halfHeight;
    private int startPixelX,startPixelY,endPixelX,endPixelY;

    /**
     * rebuild all parameters to present the image
     */
    private void buildShowFrame(){
        centerPixelX=(int)((centerX+1)/2*mapImageFile.getWidth());
        centerPixelY=(int)((centerY+1)/2*mapImageFile.getHeight());
        halfWidth=(int)(mapImageFile.getWidth()/2*scale);
        halfHeight=(int)(mapImageFile.getHeight()/2*scale);
        startPixelX=centerPixelX-halfWidth;
        startPixelY=centerPixelY-halfHeight;
        endPixelX=centerPixelX+halfWidth;
        endPixelY=centerPixelY+halfHeight;
    }
    public MapView(){
        //read map image
        try {
            mapImageFile= ImageIO.read(new File("map.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        buildShowFrame();
        setMinimumSize(new Dimension(600,400));
        clickedListeners=new ArrayList<>();

        //response to click events
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton()==MouseEvent.BUTTON1){
                    int x=e.getX();
                    int y=e.getY();
                    double xPosRate=(double)x/getWidth();
                    double yPosRate=(double)y/getHeight();
                    int factX=(int)(xPosRate*halfWidth*2+startPixelX);
                    int factY=(int)(yPosRate*halfHeight*2+startPixelY);
                    for (MapClickedListener clickedListener:clickedListeners){
                        clickedListener.clicked(factX, factY);
                    }
                }
            }
        });
    }

    /**
     * Add a listener to listen the clicked event on map view
     * @param listener
     */
    public void addMapClickedListener(MapClickedListener listener){
        this.clickedListeners.add(listener);
    }

    /**
     * Set scale of the showing map
     * @param scale
     */
    public void setScale(double scale) {
        if (scale <=0 || scale>1 ) return ;
        this.scale = scale;
        buildShowFrame();
        repaint();
    }

    /**
     * Set the X value of showing center
     * @param centerX
     */
    public void setCenterX(double centerX) {
        if (centerX<-1 || centerX>1) return ;
        this.centerX = centerX;
        buildShowFrame();
        repaint();
    }

    /**
     * Set the Y value of showing center
     * @param centerY
     */
    public void setCenterY(double centerY) {
        if (centerY<-1 || centerY>1) return ;
        this.centerY = centerY;
        buildShowFrame();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(mapImageFile,0,0,getWidth(),getHeight(),
                startPixelX,startPixelY,endPixelX,endPixelY,null);
    }

    public double getScale() {
        return scale;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public int getCenterPixelX() {
        return centerPixelX;
    }

    public int getCenterPixelY() {
        return centerPixelY;
    }

    public int getHalfWidth() {
        return halfWidth;
    }

    public int getHalfHeight() {
        return halfHeight;
    }

    public int getStartPixelX() {
        return startPixelX;
    }

    public int getStartPixelY() {
        return startPixelY;
    }

    public int getEndPixelX() {
        return endPixelX;
    }

    public int getEndPixelY() {
        return endPixelY;
    }
}
