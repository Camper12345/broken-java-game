/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;


import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;
import static java.util.logging.Level.*;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.swing.JOptionPane;



/**
 *
 * @author Camper2012
 */
public class Main {
    public static Frame window;
    private static FileHandler log;
    private static VerySimpleFormatter logFormatter = new VerySimpleFormatter();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            log = new FileHandler(System.getProperty("user.dir")+"/log%g.log",false);
            log.setFormatter(logFormatter);
        } catch (SecurityException | IOException ex) {
            System.out.println("ERROR: LOG FILE ACCESS ERROR");
        }
        info("-----===== GAME INITIALIZATION STARTED =====-----");
        window = new Frame();
        window.setVisible(true);
    }
   
    public final static void info(String ms){
            LogRecord lr = new LogRecord(INFO,ms); 
            log.publish(lr);
            System.out.println(logFormatter.format(lr));
    }
    public final static void error(String ms){
            LogRecord lr = new LogRecord(WARNING,ms);
            log.publish(lr);
            System.out.println(logFormatter.format(lr));
    }
    public final static void critical(String ms){
            LogRecord lr = new LogRecord(SEVERE,"!!! "+ms);
            //JOptionPane.showMessageDialog(window,ms,"ERROR",JOptionPane.ERROR_MESSAGE);
            log.publish(lr);
            System.out.println(logFormatter.format(lr));
            //System.exit(-1);
    }
    public static boolean isNull(Object obj){
        boolean nl = false;
        try{obj.equals(new Object());}
        catch(NullPointerException ex){
            nl = true;
        }
        return nl;
    }
    
    public static String pickStringVariant(String str, int max){
        String[] res = new String[8];
        for(int i = 0; i<=max && i<=7; i++){
            res[i] = str+i;
        }
        return (String) pick(res[0],res[1],res[2],res[3],res[4],res[5],res[6],res[7]);
    }
    
    public static boolean prob(float chance){
        return (Math.random()*99)<chance;
    }
    
    public static boolean in_range(int val, int min, int max) {
        return val>=min && val<=max;
    }
    
    public static boolean in_range(int vx, int vy, int x1, int y1, int x2, int y2) {
        return in_range(vx,x1,x2) && in_range(vy,y1,y2);        
    }
    
    public static boolean in_range(int vx,int vy,GameObject obj){
        return in_range(vx,vy,(int)obj.x,(int)obj.y,(int)obj.x+obj.width-1,(int)obj.y+obj.height-1);
    }
    
    public static boolean in_range(int vx,int vy,Rectangle obj){
        return in_range(vx,vy,(int)obj.x,(int)obj.y,(int)obj.x+obj.width-1,(int)obj.y+obj.height-1);
    }
    
    public static int ld_x(float len, float dir){
        return (int) (-Math.cos(Math.toRadians(dir))*-len);
    }
    
    public static int ld_y(float len, float dir){
        return (int) (Math.sin(Math.toRadians(dir))*-len);
    }
    
    public static int bound(int val, int min, int max){
        return Math.max(min, Math.min(max, val));
    }
    
    public static float bound(float val, float min, float max){
        return Math.max(min, Math.min(max, val));
    }
    
    public static String crypt(String input, String key){
        char[] keyarr = key.toCharArray();
        char[] inparr = input.toCharArray();
        char[] outarr = new char[inparr.length];
        int ki = 0;
        for(int i = 0; i<=inparr.length-1;i++){
            outarr[i] = (char) (inparr[i]^keyarr[ki]);
            ki++;
            if(ki>keyarr.length-1){
                ki = 0;
            }
        }
        return outarr.toString();        
    }
    
    public static Point line_intersect(Line2D l1, Line2D l2){
        return line_intersect(new Point((int)l1.getP1().getX(),(int)l1.getP1().getY()), 
                new Point((int)l1.getP2().getX(),(int)l1.getP2().getY()), 
                new Point((int)l2.getP1().getX(),(int)l2.getP1().getY()), 
                new Point((int)l2.getP2().getX(),(int)l2.getP2().getY()));
    }
    
    public static Point line_intersect(Point p11, Point p12, Point p21, Point p22){
        int a1 = p11.y-p12.y;
        int a2 = p21.y-p22.y;
        int b1 = p12.x-p11.x;
        int b2 = p22.x-p21.x;
        
        int d = a1*b2-a2*b1;
        if(d!=0){
            int c1 = p12.y-p11.x-p12.x*p11.y;
            int c2 = p22.y-p21.x-p22.x*p21.y;
            
            int xi = (b1 * c2 - b2 * c1) / d;
            int yi = (a2 * c1 - a1 * c2) / d; 
            
            return new Point(xi,yi);
        }
        
        return null;
    }
    
    public static float get_dir(float x1, float y1, float x2, float y2){
        return (float) (Math.toDegrees(Math.atan2(y2-y1,x1-x2))+180.0f);
    }
    
   /* public static float get_dir(float x1, float y1, float x2, float y2){
        double len = Math.sqrt(Math.abs(x2-x1)+Math.abs(y2-y1));
        double sin = (x2-x1)/len;
        
        double deg = Math.asin(sin);
        
        
        
        return 0;
    }*/
    
    public static float bound_dir(float d){
        float rd = 0;
        if(Main.in_range((int) d, 0, 360)){
            rd = d;}
        else if(d<0){
            rd = 360-d%360;}
        else if(d>360){
            rd = d%360;}
        if(rd == 360){
            rd = 0;
        }
        return rd;
    }
    
    public static float dir_diff(float d1, float d2){
        float dir1 = 0;
        float dir2 = 0;
        dir1 = bound_dir(d1);
        dir2 = bound_dir(d2);
                
        float sum = Math.max(dir1-180,dir2-180)-Math.min(dir1-180,dir2-180); 
        float diff = 0;
        diff = bound_dir(sum);
        return 180-Math.abs((diff)-180);
    } 
    
    public static Object pick(Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8){
        Object robj = "";
        ArrayList<Object> list = new ArrayList();
        if(obj1 != null){
            list.add(obj1);
        }
        if(obj2 != null){
            list.add(obj2);
        }
        if(obj3 != null){
            list.add(obj3);
        }
        if(obj4 != null){
            list.add(obj4);
        }
        if(obj5 != null){
            list.add(obj5);
        }
        if(obj6 != null){
            list.add(obj6);
        }
        if(obj7 != null){
            list.add(obj7);
        }
        if(obj8 != null){
            list.add(obj8);
        }
        if(list.size()>=1){
        robj = list.get((int)Math.round(Math.random()*(list.size()-1)));}
        
        return robj;
    }
    public static Object pick(Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7){
        return pick(obj1,obj2,obj3,obj4,obj5,obj6,obj7,null);
    }
    public static Object pick(Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6){
        return pick(obj1,obj2,obj3,obj4,obj5,obj6,null,null);
    }
    public static Object pick(Object obj1, Object obj2, Object obj3, Object obj4, Object obj5){
        return pick(obj1,obj2,obj3,obj4,obj5,null,null,null);
    }
    public static Object pick(Object obj1, Object obj2, Object obj3, Object obj4){
        return pick(obj1,obj2,obj3,obj4,null,null,null,null);
    }
    public static Object pick(Object obj1, Object obj2, Object obj3){
        return pick(obj1,obj2,obj3,null,null,null,null,null);
    }
    public static Object pick(Object obj1, Object obj2){
        return pick(obj1,obj2,null,null,null,null,null,null);
    }
}   
