/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;

import gameengine.objects.Ammo;
import gameengine.objects.Ladder;
import gameengine.objects.Marker;
import gameengine.objects.Mob;
import gameengine.objects.Solid;
import gameengine.objects.light.SunLight;
import gameengine.objects.mobs.Player;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Camper2012
 */
public class GameObject {
    public Chunk chunk;
    public String type = "object";
    public transient BufferedImage icon;
    public transient BufferedImage baseIcon;
    public transient int animIndex = 0;
    public transient int lastAnimIndex = 0;
    public boolean cycleAnim = true;
    public String basicIconName = "obj\\object";
    public String iconName = "";
    public boolean iconCenter = false;
    public int x = 0;
    public int y = 0;
    public int z = 0;
    public transient int scrX = 0;
    public transient int scrY = 0;
    public int offX = 0;
    public int offY = 0;
    public int width = Defines.ICON_SIZE;
    public int height = Defines.ICON_SIZE;
    public float direction = 0;
    public boolean can_rotated = true;
    public float mass = 1;
    public float speed = 0;
    public float speed_dir = 0;
    public float move_dir = 0;
    public int move_len = 0;
    public transient DynamicObject container = null;
    public float layer = Defines.LAYER_OBJECT;
    public boolean isMovable = true;
    public boolean isOpacity = true;
    public boolean isVisible = true;
    public boolean isDensity = false;
    public boolean isInvs = false;
    public boolean fullHeight = true;
    public boolean inFullHeight = false;
    public byte collideType = 0; //0 - rect, 1 - half rect
    public boolean mouseCollideShape = true;
    public ArrayList<GameObject> collides = new ArrayList();
    public ArrayList<GameObject> collidesND = new ArrayList();
    public boolean isActive = false;
    public boolean staticSize = false;
    public boolean staticPos = false;
    public int flags = 0;
    public int pass_flags = 0;
    public transient int lighting = 0;
    public transient Color lightingColor = Color.black;
    public boolean can_lighting = true;
    public ArrayList<LightSource> cover_lamps = new ArrayList();
    public ArrayList<LightSource> cover_spots = new ArrayList();
    public ArrayList<LightSource> cover_sources = new ArrayList();
    public boolean removable = true;
    public boolean saveable = true;
    public boolean removed = false;
    public boolean drawLowerZ = true;
    public boolean isInproc = true;
    public ArrayList<Integer> timers = new ArrayList();
    public GameObject() { 
        //initVars();
    }

    public void preInit(int nx,int ny,int nz){
        z = nz;
        x = nx;
        y = ny;
        init(nx,ny);
        move(nx,ny);
        assignToChunk();
        isActive = true;
        update();
        iconName = basicIconName;
        updateIcon(true);
        collides = getCollideList(x, y, z, true, false, false);
        collidesND = getCollideList(x, y, z, true, true, false);
        onCreate();
        
    }
    
    public void update(){
        
    }

    public void assignToChunk(){
        if(!Main.isNull(chunk)){
            if(Defines.CHUNK_SIZE*(x/Defines.CHUNK_SIZE)+(x<0?-Defines.CHUNK_SIZE:0) == chunk.x && 
                    Defines.CHUNK_SIZE*(y/Defines.CHUNK_SIZE)+(y<0?-Defines.CHUNK_SIZE:0) == chunk.y){
                
                return;
            }
        }
        //System.out.println(this+" ESCAPE THEIR CHUNK");
        ArrayList<Chunk> mapc = new ArrayList();
        mapc.addAll(Main.window.map);
        for(Chunk ch : mapc){
            if(Defines.CHUNK_SIZE*(x/Defines.CHUNK_SIZE)+(x<0?-Defines.CHUNK_SIZE:0) == ch.x && 
                    Defines.CHUNK_SIZE*(y/Defines.CHUNK_SIZE)+(y<0?-Defines.CHUNK_SIZE:0) == ch.y){
                if(!Main.isNull(chunk)){
                    chunk.contents.remove(this);
                }
                
                ch.contents.add(this);
                chunk = ch;
                return;
            }
        }
        Chunk nch = new Chunk();
        nch.x = Defines.CHUNK_SIZE*(x/Defines.CHUNK_SIZE)+(x<0?-Defines.CHUNK_SIZE:0);
        nch.y = Defines.CHUNK_SIZE*(y/Defines.CHUNK_SIZE)+(y<0?-Defines.CHUNK_SIZE:0);
        Main.window.map.add(nch);
        if(!Main.isNull(chunk)){
        chunk.contents.remove(this);
        }
        nch.contents.add(this);
        chunk = nch;
    }
    
    public void initVars(){
        
    }
    
    public void init(int nx, int ny){

    }
       
    public void onCreate(){
        updateIcon(true);
    }
  
    public void onRemove(){
        
    }
    
    public void remove() {
        chunk.contents.remove(this);
        onRemove();
        cycleAnim = false;
        animIndex = -1;
        isActive = false;
        removed = true;
    } 
    
    public boolean canMove(int nx, int ny, boolean bump){
        boolean succ = false;
            if(isDensity){
            if(!checkCollide(nx,ny,bump))
            {
                succ = true;
            }
            }
            else {          
                succ = true;
            }
        return succ;
    }
    
    public boolean canMove(int nx, int ny){
        return canMove(nx,ny,false);
    }
    
    public void setAngle(int ndir){
        if(Main.in_range(ndir, 0, 359)){
            direction = ndir;}
        else if(ndir>360){
            direction = (int)Math.abs(Math.abs(ndir)-(360*Math.floor(ndir/360)));}
        else if(ndir<0){
            direction = 360-(int)Math.abs(Math.abs(ndir)-Math.abs(360*Math.floor(ndir/360)));}
        updateIcon(true);
    }
    //////////
    //EVENTS//
    public void onClick(){  
    }
    public void onPress(){  
    }
    public void onRightClick(){  
    }
    public void onRightPress(){  
    }
    public void onMiddleClick(){  
    }
    public void onMiddlePress(){  
    }
    public void onOver(){
    }
    public void onExit(){
    }
    public void onRelease(){
    }
    public void onMiddleRelease(){
    }
    public void onRightRelease(){
    }
    public void onUpdate(){
    }
    public void onCollide(GameObject obj){   
    }
    public void onAnimationCycle(){
    }
    public void onTimer(int id){
    }
    public void onShot(Ammo ammo){
    }
    /////////
    /////////
     
    public void collide(GameObject obj, boolean force){
        if(force && isMovable){
        applyForce(Main.get_dir(obj.x, obj.y, obj.x, obj.y), (int) (obj.speed/mass));
        }
        onCollide(obj);
    }
    public void collide(GameObject obj){
        collide(obj,false);
    }
    
    public boolean isCollide(GameObject obj){
        boolean ret = obj.isDensity && (obj.fullHeight || !inFullHeight);
        return ret;
    }
    
    public void move(int nx, int ny, boolean stopacc) {
        if(true){
            //if(stopacc){speed = 0;}
            if(x!=nx || y!=ny){
            move_dir = Main.get_dir(x, y, nx, ny);
            move_len = getDistance(nx, ny);
            x = nx;
            y = ny;
            assignToChunk();
            collides = getCollideList(x, y, z, true, false, false);
            collidesND = getCollideList(x, y, z, true, true, false);
            updateIcon(true);
            }
        }
    }
    public void move(int nx, int ny){
        move(nx,ny,true);        
    }
    
    public void moveZ(int nz){
        if(z != nz){
        z = nz;
        assignToChunk();
        collides = getCollideList(x, y, z, true, false, false);
        collidesND = getCollideList(x, y, z, true, true, false);
        updateIcon(true);
        }
    }
    
    public void move_accel(){
        if(speed>0){
            int nx = (int) (x+Main.ld_x(speed, speed_dir));
            int ny = (int) (y+Main.ld_y(speed, speed_dir));
            if(canMove(nx, ny)){
                move(nx,ny,false);
            }
            speed -= mass;
            if(speed<0){
                speed = 0;
            }
        }
    }
    
    public void applyForce(float dir, float str){
        speed_dir = dir;
        speed = (str);
    }
    
    public boolean checkCollide(int nx, int ny, boolean bump){
        boolean coll = false;
        if(!getCollideList(nx,ny,bump,false).isEmpty()){
            coll = true;
        }
        return coll;
    }
    public boolean checkCollide(int nx, int ny)
    {
        return checkCollide(nx,ny,false);
    }

    public boolean checkCollide(int nx, int ny, GameObject cobj){
        boolean coll = false;
        if(getCollideList(nx,ny).contains(cobj)){
            coll = true;
        }
        return coll;
    }
    
    public boolean checkMouseCollide(){
        boolean succ;
        Rectangle c = getCollideShape();
        int sx = Main.window.master.objShiftX;
        int sy = Main.window.master.objShiftY;
        if(staticPos){
        succ = Main.in_range(Main.window.master.mouseX, Main.window.master.mouseY, x,y,x+width-1,y+height-1);
        }else{
        succ = Main.in_range(Main.window.master.mouseX, Main.window.master.mouseY,c.x+sx,c.y+sy,c.x+c.width+sx,c.y+c.height+sy);
        }
        return succ;
    }
    
    public ArrayList getCollideList(int nx, int ny){
        return getCollideList(nx,ny,false,false);
    }
    
    public Rectangle getCollideShape(){
        return getCollideShape(x,y);
    }
    
    public Rectangle getCollideShape(int nx, int ny){
        Rectangle collsh = new Rectangle(nx,ny,width-1,height-1);
        if(collideType == 1){
            collsh = new Rectangle(nx+width/4,ny+height/4,(width/2)-1,(height/2)-1);
        }
        
        return collsh;
    }
    
    public ArrayList getCollideList(int nx, int ny, boolean bump, boolean notd){
        return getCollideList(nx,ny,z,bump,notd,false);
    }
    
    public boolean underTurf(int nx, int ny){
        return underTurf(nx,ny,z);
    }
    
    public boolean underTurf(int nx, int ny, int nz){
        boolean is = false;
        for(Object gobj : getCollideList(nx,ny,nz,true,true,false)){
            GameObject obj = (GameObject) gobj;
            if(obj instanceof Turf){
                is = true;
                break;
            }
        }
        return is;
    }
      
    public ArrayList getCollideList(int nx, int ny, int nz, boolean clear, boolean notd, boolean notst){
        ArrayList<GameObject> clist = new ArrayList();
        if(!Defines.COLLIDE_ENABLED){
            return clist;
        }
        if(nx == x && ny == y && nz == z && !clear){
            if(notd){
                return collidesND;
            }else{
                return collides;
            }
        }
        for(Object cho : Main.window.map.toArray()){
            Chunk ch = (Chunk) cho;
            if(ch.getSquareDistance(nx+width/2, ny+height/2)<=Defines.CHUNK_SIZE){
            for(Object gobj : ch.contents.toArray()){
                GameObject obj = (GameObject)gobj;
                if(!obj.removed){
                Rectangle r1 = getCollideShape(nx,ny);
                Rectangle r2 = obj.getCollideShape();
                if(r1.intersects(r2)){
                    if((isCollide(obj) || notd ) && obj.isActive && !this.equals(obj) && (nz == obj.z) && 
                        (notst || obj instanceof DynamicObject)){
                        clist.add(obj);
                        }
                }
            }
            }
        }
        }
        return clist;
    }
    
    public boolean getCollideLine(GameObject o){
        return getCollideLine(o.x+o.width/2,o.y+o.height/2,o.z, o, this);
    }
    public boolean getCollideLine(GameObject o, GameObject self){
        return getCollideLine(o.x+o.width/2,o.y+o.height/2,o.z, o, self);
    }
    public boolean getCollideLine(int nx, int ny, int nz){
        return getCollideLine(nx,ny,nz,this,this);
    }
    public boolean getCollideLine(int nx, int ny, int nz, GameObject trg, GameObject self){
        return !getCollideLineList(nx, ny, nz, trg, self).isEmpty();
    }
      
    public ArrayList getCollideLineList(int nx, int ny, int nz, GameObject trg, GameObject self){
        Rectangle cr = getCollideShape();
        ArrayList<GameObject> objs = new ArrayList();
        
        if(nz != z){
           return objs; 
        }
        Line2D ln0 = new Line2D.Float(x+width/2,y+height/2,nx,ny);
        for(Object cho : Main.window.map.toArray()){
            Chunk ch = (Chunk) cho;
            if(ch.getSquareDistance(x, y)<=getDistance(nx,ny)+Defines.CHUNK_SIZE/2){
            for(Object gobj : ch.contents.toArray()){
                GameObject obj = (GameObject)gobj;
                if(isCollide(obj) && obj.z == nz && !obj.equals(this) && (!obj.equals(trg)) && (!obj.equals(self))){
                    Rectangle c = obj.getCollideShape();
                    if(ln0.intersects(c)){
                        objs.add(obj);
                    }
                }
            }
        }
        }
        return objs;
    }
    
    public void bump(){
        bump(x,y,z);
    }
    
    public void bump(int nx, int ny, int nz){
        if(this instanceof DynamicObject){
        for(Object gobj : getCollideList(nx, ny, nz, false, true, false).toArray()){
            GameObject obj = (GameObject) gobj;
            obj.collide(this);
            collide(obj);
        }
        }
    }
    
    public int getDistance(GameObject obj){
        return (int)new Point(x+width/2,y+height/2).distance(obj.x+obj.width/2, obj.y+obj.height/2);
    }
    public int getDistance(int nx, int ny){
        return (int)new Point(x+width/2,y+height/2).distance(nx, ny);
    }
    /*public int getDistance(int nx, int ny, int nz){
        return (int)(Math.pow(new Point(x+width/2,y+height/2).distance(nx, ny),2)+Math.pow(Math.abs((z-nz)*Defines.ICON_SIZE),2));
    }*/
    
    public boolean addToContainer(DynamicObject obj){
        boolean succ = false;
        if(Defines.checkFlag(obj.flags, Defines.OBJECT_IS_CONTAINER) && !obj.equals(container)){
            container = obj;
            succ = true;
        }
        return succ;
    }
    
    public boolean removeFromContainer(){
        boolean succ = false;
        if(!Main.isNull(container)){
            container = null;
            succ = true;
        }
        return succ;
    }
    
    public boolean isDraw(){
        if((!(this instanceof StaticObject) && !(this instanceof Wall) &&
           isSightBlockedFull(-Main.window.master.objShiftX+Defines.WIDTH/2, -Main.window.master.objShiftY+Defines.HEIGHT/2, z))){
            if(true){
                return false;
            }
        }
        return true;
    }
    
    public boolean update_icon(boolean reload){
        boolean succ = false;
        /*if((!(this instanceof StaticObject) && !(this instanceof Wall) &&
           isSightBlockedFull(-Main.window.master.objShiftX+Defines.WIDTH/2, -Main.window.master.objShiftY+Defines.HEIGHT/2, z))){
            if(!reload){
                return false;
            }
        }*/
        long itime = System.nanoTime();
        if(animIndex != lastAnimIndex){
            reload = true;
        }
        if(isVisible && in_disp_zone() && (Main.window.master.drawInvs || !isInvs)){
        if(!iconName.equals("")){baseIcon = (BufferedImage) loadImage(iconName).getImage(animIndex);}
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);;
        if(!Main.isNull(baseIcon)){
            img = copyImage((BufferedImage) baseIcon);
            //img.getGraphics().drawImage(baseIcon, 0, 0, null);
        }
        
        icon = null;
        if(!Main.isNull(img)){
            if(!staticSize){
                width = img.getWidth(null);
                height = img.getHeight(null);}
            img.getGraphics().drawImage(specialUpdateIcon(img),0,0,null);
            BufferedImage und = specialUpdateIconUnder();
            und.getGraphics().drawImage(img,0,0,null);
            img = und;
            if(can_rotated && direction!=0){
            AffineTransform rotop = new AffineTransform();
            if(iconCenter){
                rotop.rotate(Math.toRadians(-direction),Math.ceil(((float)img.getWidth(null))/2f),Math.ceil(((float)img.getHeight(null))/2f));
            }else{
                rotop.rotate(Math.toRadians(-direction),Defines.ICON_SIZE/2,Defines.ICON_SIZE/2);
            }
            AffineTransformOp rot = new AffineTransformOp(rotop, Defines.ICON_TRANSFORM_TYPE);
            try{
            img = rot.filter((BufferedImage)img, null); /*baseIcon = copyImage(img);*/
            }catch(Exception ex){
                //System.out.println(this+" "+x+" "+y+" "+" CAUSED EXC "+ex);
            }
            }
            if(can_lighting && !staticPos){
                Graphics2D gr = (Graphics2D) img.getGraphics();
                gr.setComposite(AlphaComposite.SrcAtop);
                if(true){
                lightingColor = getLighting();
                lighting = lightingColor.getAlpha();
                }
                gr.setColor(lightingColor);
                gr.fillRect(0, 0, img.getWidth(null), img.getHeight(null));
                
                gr.setComposite(AlphaComposite.SrcOver);
            }
            if(z<Main.window.master.currentZ && drawLowerZ){
                Graphics2D gr = (Graphics2D) img.getGraphics();
                gr.setComposite(AlphaComposite.SrcAtop);
                for(int i=0;i<(Main.window.master.currentZ-z);i++){
                    for(int w = 0; w<=width; w+=Defines.ICON_SIZE){
                        for(int h = 0; h<=height; h+=Defines.ICON_SIZE){
                            gr.drawImage(copyImage((BufferedImage)loadImage("lowerzanim").getRandomImage()), w, h, null);
                        }
                    }
                }
                gr.setComposite(AlphaComposite.SrcOver);
            }
            img.getGraphics().drawImage(specialUpdateIconOver(img),0,0,null);
            if(Main.window.master.drawInvs){
            img.getGraphics().drawImage(specialUpdateIconDebug(img),0,0,null);
            if(Defines.SHOW_COLLIDERS && (isDensity || pass_flags != 0 || this instanceof Ammo) && z==Main.window.master.currentZ){
                Rectangle cr = getCollideShape();
                Graphics clgr = Main.window.master.over;
                clgr.setColor(Color.red);
                int sx = scrX+(cr.x-x);
                int sy = scrY+(cr.y-y);
                clgr.drawRect(sx, sy, cr.width, cr.height);
            }
            }
            icon = img;
            succ = true;}
        }else{
            icon = null;
        }
        //System.out.println(this+" ICON PROCESSED IN "+(System.nanoTime()-itime)+" ns");
        return succ;
    }   
    
    public BufferedImage specialUpdateIcon(BufferedImage img){
        BufferedImage nimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        return nimg;
    }
    
    public BufferedImage specialUpdateIconUnder(){
        BufferedImage nimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        return nimg;
    }
    
    public BufferedImage specialUpdateIconOver(BufferedImage img){
        BufferedImage nimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        return nimg;
    }
    
    public BufferedImage specialUpdateIconDebug(BufferedImage img){
        BufferedImage nimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        return nimg;
    }
    
    public Color getLighting(){
        Color lc = Defines.LIGHT_DARK_COLOR;
        if(!Defines.LIGHT_ENABLED){    
        lc = new Color(0,0,0,0);
        return lc;
        }
        ArrayList<LightSource> cll = new ArrayList();
        ArrayList<LightSource> cls = new ArrayList();
        ArrayList<LightSource> clso = new ArrayList();
        int ssa = 0;
        for(Object objs : Main.window.lights.toArray()){
            LightSource ls = (LightSource) objs;
                int lrng = ls.range;
                boolean sun = ls instanceof SunLight;
                float dist = getDistance(ls);
                float dd = Main.dir_diff(ls.direction, Main.get_dir(ls.x, ls.y, x, y));
                float edd = (dd<=ls.size&&dd>ls.size/3)?dd:0;
                if(dist<=32 && ls.size<180){
                    lrng = 32;
                }
                if(ls.size<180 && dd<=ls.size){
                    lrng = (int) ((float)lrng*(180f/(float)(ls.size))/2);
                }
                if(ls.z == z && ls.brightness>0 && (dist<=lrng || sun) && ((dist<=32)||(ls.size>=180 || dd<=ls.size) || sun) 
                        && !isSightBlocked(ls)){
                int dr = lc.getRed();
                int dg = (lc.getGreen());
                int db = (lc.getBlue());
                int da = (lc.getAlpha());
                
                Color lsc = ls.color;
                int sr = lsc.getRed();
                int sg = (lsc.getGreen());
                int sb = (lsc.getBlue());
                int sa = (int) (Main.bound(255-((getDistance(ls)*(ls.brightness))/lrng)-(255-ls.brightness),0,255));
                if((ls.size<180 && dd<=ls.size) && !(dist<=32)){
                sa = (int)(sa-Main.bound((edd/ls.size)*sa,0,sa));                
                }
                if(ls instanceof SunLight){
                    sa = ls.brightness;
                }
                
                if(ssa == 0){
                    ssa = sa;
                }else{
                    ssa = (ssa+sa)>>1;
                }
                
                int r = (int) Math.min(255f,(dr*(1-da/255f)+(sr*sa/255f+dr*(1-sa/255f))*da/255f));
                int g = (int) Math.min(255f,(dg*(1-da/255f)+(sg*sa/255f+dg*(1-sa/255f))*da/255f));
                int b = (int) Math.min(255f,(db*(1-da/255f)+(sb*sa/255f+db*(1-sa/255f))*da/255f));
                int ma = (int) (((float)sa/255f)+(float)da*(1f-(float)sa/255f));
                int aa = avg3(255-sr,255-sg,255-sb);
                int a = Main.bound(ma,Math.min(aa,da),255);
                lc = new Color(r,g,b,a);
                
                /*Graphics gr = Main.window.master.over;
                gr.setColor(Color.red);
                gr.drawString(""+a, scrX, scrY+10);
                gr.drawString(""+(da), scrX, scrY+20);*/
                
                //lc = new Color(a,a,a,255);
                if(ls.getClass().equals(new LightSource().getClass())){
                    clso.add(ls);
                }else{
                if(ls.size<180){
                    cls.add(ls);
                }else{
                    cll.add(ls);
                }
                }
                }
            }
        cover_lamps.clear();
        cover_lamps.addAll(cll);
        cover_spots.clear();
        cover_spots.addAll(cls);
        cover_sources.clear();
        cover_sources.addAll(clso);
        return lc;
    }
     
    int avg2(int a, int b){
        return ((a+b)>>1);
    }
    
    int avg3(int a, int b, int c){
        return (((a+b)>>1)+c)>>1;
    }
    
    public boolean isSightBlocked(GameObject obj){
        return isSightBlocked(obj.x+obj.width/2, obj.y+obj.height/2, obj.z);
    }
    
    public boolean isSightBlocked(int nx, int ny, int nz){
        return isSightBlocked(nx, ny, nz, false);
    }
    
    public boolean isSightBlocked(int nx, int ny, int nz, boolean end){
        boolean succ = false;
        ArrayList<Point> epts = new ArrayList();
        Rectangle cr = getCollideShape();
        epts.add(new Point(cr.x,cr.y));
        epts.add(new Point(cr.x+cr.width-1,cr.y));
        epts.add(new Point(cr.x,cr.y+cr.height-1));
        epts.add(new Point(cr.x+cr.width-1,cr.y+cr.height-1));
        epts.sort((Point o1, Point o2) -> {
                    Float d1 = (float)o1.distance(nx, ny);
                    Float d2 = (float)o2.distance(nx, ny);
                    return d1.compareTo(d2);
                });
        if(nz != z){
           return true; 
        }
        Point lp = new Point(epts.get(0).x,epts.get(0).y);
        if(end){
            lp = new Point(epts.get(3).x,epts.get(3).y);
        }
        Line2D ln0 = new Line2D.Float(lp.x,lp.y,nx,ny);
        for(Object cho : Main.window.map.toArray()){
            Chunk ch = (Chunk) cho;
            if(ch.getSquareDistance(x, y)<=getDistance(nx,ny)+Defines.CHUNK_SIZE){
            for(Object gobj : ch.contents.toArray()){
                GameObject obj = (GameObject)gobj;
                if(!obj.isOpacity && !obj.equals(this) && obj.z == z){
                    Rectangle c = obj.getCollideShape();
                    Point pt11 = new Point(c.x,c.y+c.height/2);
                    Point pt12 = new Point(c.x+width,c.y+c.height/2);
                    Point pt21 = new Point(c.x+c.width/2,c.y);
                    Point pt22 = new Point(c.x+c.width/2,c.y+c.height);
                    Line2D cl1 = new Line2D.Float(pt11,pt12);
                    Line2D cl2 = new Line2D.Float(pt21,pt22);
                    if((ln0.intersectsLine(cl1) || ln0.intersectsLine(cl2)) 
                            ){
                        succ = true;
                        break;
                    }
                }
            }
        }
        }
        return succ;
    }
    
    public boolean isSightBlockedFull(int nx, int ny, int nz){
        boolean succ = false;
        ArrayList<Point> epts = new ArrayList();
        Rectangle cr = getCollideShape();
        epts.add(new Point(cr.x,cr.y));
        epts.add(new Point(cr.x+cr.width-1,cr.y));
        epts.add(new Point(cr.x,cr.y+cr.height-1));
        epts.add(new Point(cr.x+cr.width-1,cr.y+cr.height-1));
        /*if(nz != z){
           return true; 
        }*/
        Line2D ln0 = new Line2D.Float(epts.get(0).x,epts.get(0).y,nx,ny);
        Line2D ln1 = new Line2D.Float(epts.get(1).x,epts.get(1).y,nx,ny);
        Line2D ln2 = new Line2D.Float(epts.get(2).x,epts.get(2).y,nx,ny);
        Line2D ln3 = new Line2D.Float(epts.get(3).x,epts.get(3).y,nx,ny);
        boolean l0i = false;
        boolean l1i = false;
        boolean l2i = false;
        boolean l3i = false;
        for(Object cho : Main.window.map.toArray()){
            Chunk ch = (Chunk) cho;
            if(ch.getSquareDistance(x, y)<=getDistance(nx,ny)+Defines.CHUNK_SIZE){
            for(Object gobj : ch.contents.toArray()){
                GameObject obj = (GameObject)gobj;
                if(obj instanceof Turf && !obj.isOpacity && !obj.equals(this) && obj.z == Main.window.master.currentZ){
                    Rectangle c = obj.getCollideShape();
                    Point pt11 = new Point(c.x,c.y+c.height/2);
                    Point pt12 = new Point(c.x+width-1,c.y+c.height/2);
                    Point pt21 = new Point(c.x+c.width/2,c.y);
                    Point pt22 = new Point(c.x+c.width/2,c.y+c.height-1);
                    Line2D cl1 = new Line2D.Float(pt11,pt12);
                    Line2D cl2 = new Line2D.Float(pt21,pt22);
                    if((ln0.intersectsLine(cl1) || ln0.intersectsLine(cl2))){
                        l0i = true;
                    }
                    if((ln1.intersectsLine(cl1) || ln1.intersectsLine(cl2))){
                        l1i = true;
                    }
                    if((ln2.intersectsLine(cl1) || ln2.intersectsLine(cl2))){
                        l2i = true;
                    }
                    if((ln3.intersectsLine(cl1) || ln3.intersectsLine(cl2))){
                        l3i = true;
                    }
                }
            }
        }
        }
        succ = l0i && l1i && l2i && l3i;
        return succ;
    }
    
    public boolean updateIcon(boolean reload){
        return this.update_icon(reload);
    }
    
    public boolean updateIcon(){
        return this.update_icon(false);
    }
    
    public ImageFile loadImage(String path){
        ImageFile img = Main.window.resource.getIcon(path);
        if(!Main.isNull(img))
        {
            return img;
        }
        return new ImageFile(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB));
    }
    
    public static BufferedImage copyImage(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    
    public void containerMove(){
        if(!Main.isNull(container)){
            move(container.x+(container.width-width)/2,container.y+(container.height-height)/2);
            moveZ(container.z);
            direction = container.direction;
            if(container.removed){
                remove();
            }
        }
    }
    
    public void updateTimers(){
        for(int i = 0;i<=timers.size()-1;i++){
            if(timers.get(i)==0){
                onTimer(i);
            }
            if(timers.get(i)>=0){
                timers.set(i, timers.get(i)-1);
            }
        }
    }
    
    public void process(){
        move_dir = -1;
        move_len = -1;
        move_accel();
        scrX = x+Main.window.master.sX;
        scrY = y+Main.window.master.sY;
        if(staticPos){
            scrX = x;
            scrY = y;
        }
        updateMouse();
        containerMove();
        updateTimers();
        onUpdate();
        //updateIcon();
        lastAnimIndex = animIndex;
        if(cycleAnim){
            if(animIndex>=loadImage(iconName).getLength()-1 || animIndex<0){
                animIndex = 0;
                onAnimationCycle();
            }else{
                animIndex++;
            }
        }
        bump();
    }
    
    public void updateMouse(){
        Rectangle r = new Rectangle(x,y,width,height);
        if(!(this instanceof StaticObject)){
            r = getCollideShape();
            int sx = Main.window.master.objShiftX;
            int sy = Main.window.master.objShiftY;
            if(mouseCollideShape){
            r = new Rectangle(r.x+sx,r.y+sy,r.width,r.height);
            }else{
            r = new Rectangle(x+sx,y+sy,Defines.ICON_SIZE,Defines.ICON_SIZE);    
            }
        }
        if(isVisible && (z==Main.window.master.currentZ || this instanceof StaticObject)){
        if(Main.in_range(Main.window.master.mouseX,Main.window.master.mouseY, r)){
            onOver();
            
            if(checkMouse(MouseEvent.BUTTON1)){
                if(checkMouseClick(MouseEvent.BUTTON1)){
                    onClick();
                }else{
                    onPress();
                }
            }else{
                if(checkMouseRelease(MouseEvent.BUTTON1)){
                onRelease();     
                }
            }
                
            if(checkMouse(MouseEvent.BUTTON2)){
                if(checkMouseClick(MouseEvent.BUTTON2)){
                    onMiddleClick();
                }else{
                    onMiddlePress();
                }
            }else{
                if(checkMouseRelease(MouseEvent.BUTTON2)){
                onMiddleRelease();     
                }
            }
            
            if(checkMouse(MouseEvent.BUTTON3)){
                if(checkMouseClick(MouseEvent.BUTTON3)){
                    onRightClick();
                }else{
                    onRightPress();
                }
            }else{
                if(checkMouseRelease(MouseEvent.BUTTON3)){
                onRightRelease();     
                }
            }
        }else{
           if(Main.in_range(Main.window.master.lastmouseX,Main.window.master.lastmouseY, r))
           onExit();         
        }
        }
    }
    
    public boolean in_disp_zone(){
        boolean in = false;
        if(!Main.isNull(chunk)){
        in = (Main.in_range(x+Main.window.master.sX,y+Main.window.master.sY,-width-64,-height-64,Defines.WIDTH+64,Defines.HEIGHT+64) && 
                (z<=Main.window.master.currentZ && z>=Main.window.master.currentZ-(Defines.Z_SIGHT_DIST)));
        }
        return in;
    }
    
    public boolean in_process_zone(){
        boolean in = false;
        in = (!(this instanceof Turf) && !(this instanceof Solid) && !(this instanceof Marker)) || (in_disp_zone());
        return in;
    }
    
    public int getSoundVolume(){
        return Defines.getSoundVolume(getDistance(-(Main.window.master.objShiftX)+Defines.WIDTH/2, -(Main.window.master.objShiftY)+Defines.HEIGHT/2));
    }
    
    public GameObject createObject(GameObject obj,int nx, int ny, int nz){
        return Main.window.createObject(obj,nx,ny,nz);
    }
    public GameObject createObject(GameObject obj,int nx, int ny, int nz, boolean init){
        return Main.window.createObject(obj,nx,ny,nz,init);
    }
    
    public boolean checkKey(int kc){
        return Main.window.master.keys.containsKey(kc)?Main.window.master.keys.get(kc):false;    
    }
    
    public boolean checkKeyClick(int kc){
        boolean k = Main.window.master.keys.containsKey(kc)?Main.window.master.keys.get(kc):false;
        boolean lk = Main.window.master.lastkeys.containsKey(kc)?Main.window.master.lastkeys.get(kc):false;   
        return k && !lk;   
    }
    
    public boolean checkMouse(int kc){
        return Main.window.master.mouse.containsKey(kc)?Main.window.master.mouse.get(kc):false;    
    }
    
    public boolean checkMouseClick(int kc){
        boolean k = Main.window.master.mouse.containsKey(kc)?Main.window.master.mouse.get(kc):false;
        boolean lk = Main.window.master.lastmouse.containsKey(kc)?Main.window.master.lastmouse.get(kc):false;   
        return k && !lk;   
    }
    
    public boolean checkMouseRelease(int kc){
        boolean k = Main.window.master.mouse.containsKey(kc)?Main.window.master.mouse.get(kc):false;
        boolean lk = Main.window.master.lastmouse.containsKey(kc)?Main.window.master.lastmouse.get(kc):false;   
        return !k && lk;   
    }
    
    public boolean isMouseOnGui(){
        for(GUI gui : Main.window.guis){
            if(gui.isMouseOnElement()){
                return true;
            }
        }
        return false;
    }
    
    public void setTimer(int id, int time){
        if(timers.size()-1<id){
            for(int i = timers.size()-1;i<id;){
                timers.add(-1);
                i = timers.size()-1;
            }
        }
            timers.set(id, time);
    }
    
    public void resetTimer(int id){
        if(timers.size()-1>=id){
            timers.set(id,-1);
        }
    }
    
    public boolean isTimer(int id){
        return timers.size()-1>=id?timers.get(id)>=0:false;
    }
    
    public int getTimerTime(int id){
        return (timers.size()<=id&&id>=0)?timers.get(id):-1;
    }
    
    public boolean getTimer(int id){
        return (timers.size()-1>=id&&id>=0)?timers.get(id)==0:false;
    }
    
    public int getMWheel(){
        return Main.window.master.mWheelRotation-Main.window.master.lastMWheelRotation;
    }
    
    public SoundSystem playSound(String snd, int volume){
        return SoundSystem.playSound(snd,volume);
    }
    
    public SoundSystem playSound(String snd){
        return playSound(snd,getSoundVolume());
    }
    
    //MAPEDITOR SETTINGS
    public boolean SETTING_COLOR = false;
    public boolean SETTING_RANGE = false;
    public boolean SETTING_STRENGTH = false;
    public boolean SETTING_STATUS = false;
    public String ColorSettingName = "ЦВЕТ";
    public String RangeSettingName = "РАДИУС";
    public String StrengthSettingName = "СИЛА";
    public String StatusSettingName = "";
    public Color DefColor = Color.black;
    public int DefRange = 0;
    public int RangeStep = 1;
    public int StrengthStep = 1;
    public int DefStrength = 0;
    public boolean DefStatus = false;
    
    public boolean hasSettings(){
        return SETTING_COLOR || SETTING_RANGE || SETTING_STRENGTH || SETTING_STATUS;
    }
    
    public void setColorSetting(Color col){
    }
    
    public void setRangeSetting(int ran){
    }
    
    public void setStrengthSetting(int str){
    }
    
    public void setStatusSetting(boolean str){
    }
}
