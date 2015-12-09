package com.sweetdum.mapnav.ui;

import com.sweetdum.mapnav.dao.MapData;
import com.sweetdum.mapnav.entity.RoadEdge;
import com.sweetdum.mapnav.entity.ShortestPath;
import com.sweetdum.mapnav.entity.TagPosition;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 地图展示类
 * Created by Mengxiao Lin on 2015/12/1.
 */
public class MapView extends JComponent {
    private BufferedImage mapImageFile;
    private BufferedImage sourceMark,targetMark;
    private double scale=1;
    private double centerX=0,centerY=0;
    private ArrayList<MapClickedListener> clickedListeners;
    private ShortestPath showPath;
    private int centerPixelX,centerPixelY,halfWidth,halfHeight;
    private int startPixelX,startPixelY,endPixelX,endPixelY;
    private MapData mapData;
    private Point sourcePoint,targetPoint;
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
            mapImageFile= ImageIO.read(getClass().getResourceAsStream("/map.png"));
            sourceMark= ImageIO.read(getClass().getResourceAsStream("/sourceMark.png"));
            targetMark= ImageIO.read(getClass().getResourceAsStream("/targetMark.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        buildShowFrame();
        setMinimumSize(new Dimension(600,400));
        clickedListeners=new ArrayList<>();
        sourcePoint=null;
        MouseAdapter mouseAdapter=new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton()==MouseEvent.BUTTON1 && e.getClickCount()==2 ){
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
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
                double change=e.getWheelRotation()*0.05;
                setScale(getScale()+change);
            }

            private Point lastDraggedPoint;
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                lastDraggedPoint=null;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (lastDraggedPoint==null) {
                    lastDraggedPoint=new Point(e.getX(),e.getY());
                }else{
                    double deltaX=(e.getX()-lastDraggedPoint.getX())*scale/halfWidth;
                    double deltaY=(e.getY()-lastDraggedPoint.getY())*scale/halfHeight;
                    lastDraggedPoint=new Point(e.getX(),e.getY());
                    setCenterX(getCenterX()-deltaX);
                    setCenterY(getCenterY()-deltaY);

                }

            }
        };
        //response to click events
        this.addMouseListener(mouseAdapter);
        //response to wheel event
        this.addMouseWheelListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
        this.setBorder(BorderFactory.createLoweredBevelBorder());
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

    public void setSourcePoint(Point sourcePoint) {
        if (this.sourcePoint!=sourcePoint) {
            showPath=null;
        }
        this.sourcePoint = sourcePoint;

        repaint();
    }

    public void setTargetPoint(Point targetPoint) {
        if (this.targetPoint!=targetPoint) {
            showPath=null;
        }
        this.targetPoint = targetPoint;
        repaint();
    }
    private int mapXtoScreenX(double mapX){
        return (int)((mapX-startPixelX)/mapImageFile.getWidth()*getWidth()/scale);
    }
    private int mapYtoScreenY(double mapY){
        return (int)((mapY-startPixelY)/mapImageFile.getHeight()*getHeight()/scale);
    }
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g=(Graphics2D)graphics;
        g.drawImage(mapImageFile,0,0,getWidth(),getHeight(),
                startPixelX,startPixelY,endPixelX,endPixelY,null);
        if (sourcePoint==null || targetPoint==null) showPath=null;
        if (showPath!=null) {
            g.setColor(Color.GREEN);
            g.setStroke(new BasicStroke(6));
            ArrayList<RoadEdge> edges=showPath.getEdges();
            for (RoadEdge e:edges){
                TagPosition source=mapData.getTagPositionByMark(e.getSource().getMark());
                TagPosition target=mapData.getTagPositionByMark(e.getTarget().getMark());
                int sX=mapXtoScreenX(source.getX());
                int sY=mapYtoScreenY(source.getY());
                int eX=mapXtoScreenX(target.getX());
                int eY=mapYtoScreenY(target.getY());
                if (e.allowBus()){
                    g.setColor(Color.CYAN);
                }
                g.drawLine(sX,sY,eX,eY);
                if (e.allowBus()){
                    g.setColor(Color.GREEN);
                }
            }
        }
        if (sourcePoint!=null) {
            int realSourcePointX=mapXtoScreenX(sourcePoint.x);
            int realSourcePointY=mapYtoScreenY(sourcePoint.y);

            g.setColor(Color.GREEN);
            g.setBackground(Color.GREEN);
            g.drawImage(sourceMark,(int)(realSourcePointX-10),(int)(realSourcePointY-10),20,20,null);
        }
        if (targetPoint!=null) {
            int realSourcePointX=mapXtoScreenX(targetPoint.getX());
            int realSourcePointY=mapYtoScreenY(targetPoint.getY());

            g.setColor(Color.GREEN);
            g.setBackground(Color.GREEN);
            g.drawImage(targetMark,(realSourcePointX-10),(realSourcePointY-10),20,20,null);
        }
    }

    public void setShowPath(ShortestPath showPath,MapData mapData) {
        this.showPath = showPath;
        this.mapData=mapData;
        repaint();
    }

    public double getScale() {
        return scale;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY()  {
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
