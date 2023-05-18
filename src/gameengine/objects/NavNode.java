/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.Chunk;
import gameengine.Defines;
import gameengine.GameObject;
import gameengine.Main;
import gameengine.objects.mobs.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Camper2012
 */
public class NavNode extends Marker{
    public int zMove = 0;
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "marker\\navigation";
        SETTING_RANGE = true;
        DefRange = 0;
        saveable = false;
        isVisible = false;
        RangeSettingName = "Z-ПЕРЕМ.";
    }
    
    @Override
    public void setRangeSetting(int zch){
        zMove = zch;
    }
    
    public int getDistance(int nx, int ny, int nz){
        int res = (int)new Point(x+width/2,y+height/2).distance(nx, ny);
        if(nz != z){
            res += Math.abs(z-nz);
        }
        return res;
    }
    
    public boolean getSpecialCollides(GameObject obj, Mob mob){
        if(!Main.isNull(mob)){
            if(obj instanceof Door){
                Door dr = (Door) obj;
                return dr.canOpen(mob);
            }else if(obj instanceof Mob){
                Mob mb = (Mob) obj;
                return (mb.target && mob.target && mb.targetX == mob.targetX && mb.targetY == mob.targetY && mb.targetZ == mob.targetZ)
                        || (mb instanceof Player);
            }else if(obj instanceof AiBlocker){
                return false;
            }else{
                return !obj.isDensity;
            }
        }
        return false;
    }
    
    public boolean getNodeLine(int nx, int ny, int nz, GameObject trg, Mob self){
        boolean succ = false;
                
        if(nz != z){
            if(zMove == 0){
                return true; 
            }
        }
        
        if(Math.abs(nz-z)>1){
            return true;
        }
        
        Line2D ln0 = new Line2D.Float(x+width/2,y+height/2,nx,ny);
        for(Object cho : Main.window.map.toArray()){
            Chunk ch = (Chunk) cho;
            if(ch.getSquareDistance(x, y)<=getDistance(nx,ny)+Defines.CHUNK_SIZE){
            for(Object gobj : ch.contents.toArray()){
                GameObject obj = (GameObject)gobj;
                if(nz == z){
                    if((obj.z == z) && !getSpecialCollides(obj, self) && !obj.equals(self) && !obj.equals(trg)){
                        Rectangle c = obj.getCollideShape();
                        if(ln0.intersects(c)){
                            return true;
                        }
                    }
                }else{
                    if(nx == x+width/2 && ny == y+height/2 && zMove != 0){
                        if(zMove<=-1 && nz>z){
                            return true;
                        }
                        if(zMove>=1 && nz<z){
                            return true;
                        }
                    }else{
                        return true;
                    }
                }
            }
        }
        }
        return succ;
    }
    
    @Override
    public BufferedImage specialUpdateIconOver(BufferedImage img){
        BufferedImage nimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics gr = nimg.getGraphics();
        gr.setColor(Color.green);
        if(zMove>0){
            gr.drawString("UP /\\", 0, 10);
        }
        if(zMove<0){
            gr.drawString("DN \\/", 0, 10);
        }
        return nimg;
    }
    
    boolean getNodeLine(NavNode nn, Mob self){
        return getNodeLine(nn.x+nn.width/2,nn.y+nn.height/2,nn.z,null,self);
    }
    
    boolean getNodeLine(NavNode nn,GameObject trg, Mob self){
        return getNodeLine(nn.x+nn.width/2,nn.y+nn.height/2,nn.z,trg,self);
    }
    
    boolean getNodeLine(NavNode nn){
        return getNodeLine(nn.x+nn.width/2,nn.y+nn.height/2,nn.z,null,null);
    }
    
    boolean getNodeLine(int nx, int ny, int nz){
        return getNodeLine(nx,ny,nz,null,null);
    }
}
