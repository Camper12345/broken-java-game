/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 *
 * @author Camper2012
 */
public class ViewEngine extends GameObject {
    private static boolean DEBUG = false;
    
    public static ArrayList<Point[]> getObjectNodesObj(int x, int y, int z, int range){
        ArrayList<Point[]> points = new ArrayList();
        for(Object cho : Main.window.map.toArray()){
            Chunk ch = (Chunk) cho;
            if(ch.isOnScreen()){
            for(Object gobj : ch.contents.toArray()){
                GameObject cobj = (GameObject)gobj;
                Rectangle obj = cobj.getCollideShape();
                if(!cobj.isOpacity && cobj.getDistance(x,y)<=range && cobj.z == z){
                    Point[] pnts = new Point[4];
                    pnts[0] = (new Point(obj.x,obj.y));
                    pnts[1] = (new Point((obj.x+obj.width),obj.y));
                    pnts[2] = (new Point(obj.x,(obj.y+obj.height)));
                    pnts[3] = (new Point((obj.x+obj.width),(obj.y+obj.height)));
                    points.add(pnts);
                }
            }
            }
        }
        points.sort((Point[] o1, Point[] o2) -> {
                    Float d1 = (float)o1[0].distance(x, y);
                    Float d2 = (float)o2[0].distance(x, y);
                    return -d1.compareTo(d2);
                });
        return points;
    }
    public static ArrayList<Point> getObjectNodesFree(int x, int y, int z, int range){
        return getObjectNodesFree(x,y,z,range,0);
    }
    public static ArrayList<Point> getObjectNodesFree(int x, int y, int z, int range, int cut){
        ArrayList<Point> points = new ArrayList();
        for(Object cho : Main.window.map.toArray()){
            Chunk ch = (Chunk) cho;
            if(ch.isOnScreen()){
            for(Object gobj : ch.contents.toArray()){
            GameObject cobj = (GameObject)gobj;
            Rectangle obj = cobj.getCollideShape();
            if(!cobj.isOpacity && cobj.getDistance(x,y)<=range && cobj.z == z){
                ArrayList<Point> ptm = new ArrayList();
                ptm.add(new Point(obj.x,obj.y));
                ptm.add(new Point((obj.x+obj.width),obj.y));
                ptm.add(new Point(obj.x,(obj.y+obj.height)));
                ptm.add(new Point((obj.x+obj.width),(obj.y+obj.height)));
                ptm.sort((Point o1, Point o2) -> {
                    Float d1 = (float)o1.distance(x, y);
                    Float d2 = (float)o2.distance(x, y);
                    return d1.compareTo(d2);
                });
                if(cut==0){
                points.add(ptm.get(0));
                }else if(cut==1){
                ptm.remove(3);
                points.addAll(ptm);
                }else if(cut==2){
                    ptm.remove(0);
                    points.addAll(ptm); 
                }else if(cut==3){
                    ptm.remove(3);
                    ptm.remove(0);
                    points.addAll(ptm); 
                }else if(cut==4){
                    points.add(ptm.get(0)); 
                    points.add(ptm.get(3)); 
                }
            }
        }
            }
        }
        return points;
    }
    
    public static ArrayList<ArrayList> getObjectNodesSegm(int x, int y, int z, int range){
        ArrayList<ArrayList> points = new ArrayList();
        ArrayList<Point> pta0 = new ArrayList();
        ArrayList<Point> pta1 = new ArrayList();
        ArrayList<Point> pta2 = new ArrayList();
        ArrayList<Point> pta3 = new ArrayList();
        for(Object cho : Main.window.map.toArray()){
            Chunk ch = (Chunk) cho;
            if(ch.isOnScreen()){
            for(Object gobj : ch.contents.toArray()){
            GameObject cobj = (GameObject)gobj;
            Rectangle obj = cobj.getCollideShape();
            if(!cobj.isOpacity && cobj.getDistance(x,y)<=range && cobj.z == z){
                ArrayList<Point> ptm = new ArrayList();
                ptm.add(new Point(obj.x,obj.y));
                ptm.add(new Point((obj.x+obj.width),obj.y));
                ptm.add(new Point(obj.x,(obj.y+obj.height)));
                ptm.add(new Point((obj.x+obj.width),(obj.y+obj.height)));
                ptm.sort((Point o1, Point o2) -> {
                    Float d1 = (float)o1.distance(x, y);
                    Float d2 = (float)o2.distance(x, y);
                    return d1.compareTo(d2);
                });
                pta0.add(ptm.get(0));
                pta1.add(ptm.get(1));
                pta2.add(ptm.get(2));
                pta3.add(ptm.get(3));
            }
        }
            }
        }
        points.add(pta0);
        points.add(pta1);
        points.add(pta2);
        points.add(pta3);
        return points;
    }
    
    public static ArrayList getLightNodes(int x, int y, int z, int range,Graphics gr){
        ArrayList<Point[]> nodes = getObjectNodesObj(x,y,z,range);
        ArrayList<Point> nodesfree = getObjectNodesFree(x,y,z,range);
        ArrayList<Point> nodesfreefn = (ArrayList<Point>) nodesfree.clone();
        ArrayList<ArrayList> nodesfreenc = getObjectNodesSegm(x,y,z,range);
        ArrayList<Point> nodesfreencc = getObjectNodesFree(x,y,z,range,2);
        ArrayList<Point[]> nodesfinal = new ArrayList();

        ArrayList<Point> nf = (ArrayList<Point>) nodesfree.clone();
        ArrayList<Point> pta0 = nodesfreenc.get(0);
        ArrayList<Point> pta1 = nodesfreenc.get(1);
        ArrayList<Point> pta2 = nodesfreenc.get(2);
        ArrayList<Point> pta3 = nodesfreenc.get(3);
        for(Object opt : nf.toArray()){
            Point pts = (Point)opt;
            int pcl0 = 0;
            int pcl1 = 0;
            int pcl2 = 0;
            int pcld = 0;
            int pcl3 = 0;
            for(Object pto : pta0.toArray()){
                Point pt = (Point)pto;
                if(Main.in_range(pts.x, pts.y, pt.x-1, pt.y-1, pt.x+1, pt.y+1)){
                    if(!(pts.x==pt.x && pts.y==pt.y)){
                        pcl0++;
                        if(!(Main.in_range(pts.x, pts.y, pt.x, pt.y-1, pt.x, pt.y+1) 
                            || Main.in_range(pts.x, pts.y, pt.x-1, pt.y, pt.x+1, pt.y))){
                            pcld++;
                        }
                    }
                }
            }
            for(Object pto : pta1.toArray()){
                Point pt = (Point)pto;
                if(Main.in_range(pts.x, pts.y, pt.x-1, pt.y-1, pt.x+1, pt.y+1)){
                    if(!(pts.x==pt.x && pts.y==pt.y)){
                        pcl1++;
                        if(!(Main.in_range(pts.x, pts.y, pt.x, pt.y-1, pt.x, pt.y+1) 
                            || Main.in_range(pts.x, pts.y, pt.x-1, pt.y, pt.x+1, pt.y))){
                            pcld++;
                        }
                    }
                }
            }
            for(Object pto : pta2.toArray()){
                Point pt = (Point)pto;
                if(Main.in_range(pts.x, pts.y, pt.x-1, pt.y-1, pt.x+1, pt.y+1)){
                    if(!(pts.x==pt.x && pts.y==pt.y)){
                        pcl2++;
                        if(!(Main.in_range(pts.x, pts.y, pt.x, pt.y-1, pt.x, pt.y+1) 
                            || Main.in_range(pts.x, pts.y, pt.x-1, pt.y, pt.x+1, pt.y))){
                            pcld++;
                        }
                    }
                }
            }
            for(Object pto : pta3.toArray()){
                Point pt = (Point)pto;
                if(Main.in_range(pts.x, pts.y, pt.x-1, pt.y-1, pt.x+1, pt.y+1)){
                    if(!(pts.x==pt.x && pts.y==pt.y)){
                        pcl3++;
                    }
                }
            }
            if(pcl3>0 && ((pcl1>0 || pcl2>0) && !(pcl1+pcl2>1))){
                nodesfreefn.remove(pts);
            }
            if((pcl2>1)){
                nodesfreefn.remove(pts);
            }
            if((pcl1>1) && (pcld>0)){
               nodesfreefn.remove(pts); 
            }
            if(((pcl2>0) || (pcl1>0)) && (pcld>0)){
               nodesfreefn.remove(pts); 
            }
            if(((pcl1>0) && (pcl2>0)) && (pcld>0)){
               nodesfreefn.remove(pts); 
            }
            if((pcl1>0) && (pcl3>0)){
                nodesfreefn.remove(pts);
            }
        }
        if(DEBUG){
        gr.setColor(Color.orange);
        for(Object opt : nodesfree.toArray()){
            Point pt = (Point)opt;
            gr.drawOval(pt.x-8+Main.window.master.sX, pt.y-8+Main.window.master.sY, 16, 16);
        }
        gr.setColor(Color.blue);
        for(Object opt : nodesfree.toArray()){
            Point pt = (Point)opt;
            gr.drawOval(pt.x-6+Main.window.master.sX, pt.y-6+Main.window.master.sY, 12, 12);
        }
        }
        
        for(Object opt : nodes.toArray()){
            Point[] pts = (Point[])opt;
            ArrayList<Point> ptm = new ArrayList();
            ArrayList<Point> ptmc = new ArrayList();
            ArrayList<Point> ptmc1 = new ArrayList();
                ptm.add(pts[0]);
                ptm.add(pts[1]);
                ptm.add(pts[2]);
                ptm.add(pts[3]);
                ptm.sort((Point o1, Point o2) -> {
                    Float d1 = (float)o1.distance(x, y);
                    Float d2 = (float)o2.distance(x, y);
                    return -d2.compareTo(d1);
                });
                ptm.remove(0);
                ArrayList<Point> ptms = (ArrayList<Point>) ptm.clone();
                Point pt1 = (Point) ptms.get(0);
                Point pt2 = (Point) ptms.get(1);
                Point pt3 = (Point) ptms.get(2);
                boolean pt1g = false;
                boolean pt2g = false;
                Point ptc = null;
                for(Object pto : nodesfreefn.toArray()){
                    Point pt = (Point)pto;
                    if(Main.in_range(pt1.x, pt1.y, pt.x-1, pt.y-1, pt.x+1, pt.y+1)){
                        if(!(pt1.x==pt.x && pt1.y==pt.y)){
                            if(!pt1g){
                                pt1g = true;
                            }else{
                            }
                        }
                    }
                    if(Main.in_range(pt2.x, pt2.y, pt.x-1, pt.y-1, pt.x+1, pt.y+1)){
                        if(!(pt2.x==pt.x && pt2.y==pt.y)){
                            if(!pt2g){
                                pt2g = true;
                            }else{
                            }
                        }
                    }
                }
                if(pt1g){
                    ptm.remove(pt1);
                    ptc = pt2;
                }
                if(pt2g){
                    ptm.remove(pt2);
                    ptc = pt1;
                }
                if(pt2g && pt1g){
                    ptc = pt3;
                }
                if(!pt1g && !pt2g){
                    ptc = null;
                }
                if(ptc!=null){
                    for(Object pto : nodesfreencc.toArray()){
                    Point pt = (Point)pto;
                    if(Main.in_range(ptc.x, ptc.y, pt.x-1, pt.y-1, pt.x+1, pt.y+1) || 
                            (Main.in_range(ptc.x, ptc.y, pt.x, pt.y-1, pt.x, pt.y+1) && 
                            Main.in_range(ptc.x, ptc.y, pt.x-1, pt.y, pt.x+1, pt.y))){
                        if(!(ptc.x==pt.x && ptc.y==pt.y)){
                            ptmc.add(pt);
                            ptmc.add(ptc);
                        }
                    }
                    if(Main.in_range(pt3.x, pt3.y, pt.x-1, pt.y-1, pt.x+1, pt.y+1) || 
                            (Main.in_range(pt3.x, pt3.y, pt.x, pt.y-1, pt.x, pt.y+1) && 
                            Main.in_range(pt3.x, pt3.y, pt.x-1, pt.y, pt.x+1, pt.y))){
                        if(!(pt3.x==pt.x && pt3.y==pt.y)){
                            ptmc1.add(pt);
                            ptmc1.add(pt3);
                        }
                    }
                    }
                }else{
                    for(Object pto : nodesfreencc.toArray()){
                    Point pt = (Point)pto;
                    if(Main.in_range(pt1.x, pt1.y, pt.x-1, pt.y-1, pt.x+1, pt.y+1) || 
                            (Main.in_range(pt1.x, pt1.y, pt.x, pt.y-1, pt.x, pt.y+1) && 
                            Main.in_range(pt1.x, pt1.y, pt.x-1, pt.y, pt.x+1, pt.y))){
                        if(!(pt1.x==pt.x && pt1.y==pt.y)){
                            ptmc.add(pt);
                            ptmc.add(pt1);
                        }
                    }
                    if(Main.in_range(pt2.x, pt2.y, pt.x-1, pt.y-1, pt.x+1, pt.y+1) || 
                            (Main.in_range(pt2.x, pt2.y, pt.x, pt.y-1, pt.x, pt.y+1) && 
                            Main.in_range(pt2.x, pt2.y, pt.x-1, pt.y, pt.x+1, pt.y))){
                        if(!(pt2.x==pt.x && pt2.y==pt.y)){
                            ptmc1.add(pt);
                            ptmc1.add(pt2);
                        }
                    }
                }                    
                }
                
                
                if(DEBUG)gr.setColor(Color.red);
                Point[] ptsr = new Point[ptm.size()];
                for(int i = 0;i<=ptm.size()-1;i+=1){
                    ptsr[i] = ptm.get(i);
                    if(DEBUG)gr.drawRect(ptsr[i].x-5+Main.window.master.sX, ptsr[i].y-5+Main.window.master.sY, 10, 10);
                }
                nodesfinal.add(ptsr);
                if(DEBUG)gr.setColor(Color.cyan);
                Point[] ptsrc = new Point[ptmc.size()];
                for(int i = 0;i<=ptmc.size()-1;i+=1){
                    ptsrc[i] = ptmc.get(i);
                    if(DEBUG)gr.drawOval(ptsrc[i].x-5+Main.window.master.sX, ptsrc[i].y-5+Main.window.master.sY, 10, 10);
                }
                nodesfinal.add(ptsrc);
                Point[] ptsrc1 = new Point[ptmc1.size()];
                for(int i = 0;i<=ptmc1.size()-1;i+=1){
                    ptsrc1[i] = ptmc1.get(i);
                    if(DEBUG)gr.drawOval(ptsrc1[i].x-5+Main.window.master.sX, ptsrc1[i].y-5+Main.window.master.sY, 10, 10);
                }
                nodesfinal.add(ptsrc1);
                
            }
        for(Object opt : pta3.toArray()){
            Point pt = (Point)opt;
             for(Object pto : pta1.toArray()){
                Point pts = (Point)pto;
                if((Main.in_range(pts.x, pts.y, pt.x, pt.y-1, pt.x, pt.y+1) 
                            || Main.in_range(pts.x, pts.y, pt.x-1, pt.y, pt.x+1, pt.y))){
                    Point[] ptcs = new Point[2];
                    ptcs[0] = pt;
                    ptcs[1] = pts;
                    nodesfinal.add(ptcs);
                }
            }
            for(Object pto : pta2.toArray()){
                Point pts = (Point)pto;
                if((Main.in_range(pts.x, pts.y, pt.x, pt.y-1, pt.x, pt.y+1) 
                            || Main.in_range(pts.x, pts.y, pt.x-1, pt.y, pt.x+1, pt.y))){
                    Point[] ptcs = new Point[2];
                    ptcs[0] = pt;
                    ptcs[1] = pts;
                    nodesfinal.add(ptcs);
                    }
                }
            }        
        return nodesfinal;
    }
            
    public static ArrayList getLightPoly(int cx, int cy, int cz, int dx, int dy, int range, Graphics grp){
        if(Main.isNull(grp)){
            DEBUG = false;
        }
        Graphics gr = grp;
        ArrayList<Polygon> polys = new ArrayList();
        ArrayList<Point[]> nodes = getLightNodes(cx,cy,cz,range,gr);
        int x = cx-dx;
        int y = cy-dy;
        if(DEBUG){gr.setColor(Color.red);
        gr.fillOval(x-10, y-10, 20, 20);}
        for(Object opt : nodes.toArray()){
            Point[] pts = (Point[])opt;
            Polygon poly = new Polygon();
            if(pts.length == 2){
                Point pt1 = new Point(pts[0].x-dx,pts[0].y-dy);
                Point pt2 = new Point(pts[1].x-dx,pts[1].y-dy);
                poly.addPoint(pt1.x, pt1.y);
                poly.addPoint(pt2.x, pt2.y);
                poly.addPoint(movePoint(pt2,new Point(x,y)).x,movePoint(pt2,new Point(x,y)).y);
                poly.addPoint(movePoint(pt1,new Point(x,y)).x,movePoint(pt1,new Point(x,y)).y);
                polys.add(poly);
            }else if(pts.length == 3){
                Point pt1 = new Point(pts[0].x-dx,pts[0].y-dy);
                Point pt2 = new Point(pts[1].x-dx,pts[1].y-dy);
                Point pt3 = new Point(pts[2].x-dx,pts[2].y-dy);
                poly.addPoint(pt1.x, pt1.y);
                poly.addPoint(pt3.x, pt3.y);
                poly.addPoint(pt2.x, pt2.y);
                poly.addPoint(movePoint(pt2,new Point(x,y)).x,movePoint(pt2,new Point(x,y)).y);
                poly.addPoint(movePoint(pt1,new Point(x,y)).x,movePoint(pt1,new Point(x,y)).y);
                polys.add(poly);
            }
        }
        return polys;
    }

    public static ArrayList getLightPoly(int x, int y, int cz, int dx, int dy,Graphics gr){
        return getLightPoly(x,y,cz,dx,dy,Defines.WIDTH+Defines.HEIGHT,gr);
    }
    public static ArrayList getLightPoly(int cx, int cy,int cz, int dx, int dy, int range){
        return getLightPoly(cx, cy, cz, dx, dy, null);
    }
    public static ArrayList getLightPoly(int cx, int cy, int cz, int dx, int dy){
        return getLightPoly(cx, cy, cz, dx, dy, null);
    }
    
    public static Point movePoint(Point mpoint, Point rpoint){
        Point npoint = new Point();
        int mdist = (int)Math.abs((Math.max(Defines.WIDTH,Defines.HEIGHT)));
        npoint.x = mpoint.x+((mpoint.x-rpoint.x)*mdist);
        npoint.y = mpoint.y+((mpoint.y-rpoint.y)*mdist);
        return npoint;
    }
}
