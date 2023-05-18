/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.mobs;

import gameengine.*;
import gameengine.objects.*;
import gameengine.objects.gui.PlayerGui;
import gameengine.objects.weapon.Fists;
import gameengine.objects.weapon.PoliceGun;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author Camper2012
 */
public class Player extends Mob{
    public boolean loadMapOnDeath = false;
    public String deathMap = "";
    public PlayerGui playerGui;
    public int scenarioFlags = 0;
    public static int SFL0_HAS_PRISON_KEY = 1;
    @Override
    public void initVars(){
        super.initVars();
        iconsDir = "mob\\player";
        basicIconName = iconsDir+"\\body";
        maxhp = 500;
        hp = 500;
        stMovespeed = 6;
        stSprint = 3;
        SETTING_RANGE = false;
        //saveable = false;
        healing_time = 1;
        weapon = new Fists();
    }
        
    @Override
    public void init(int nx, int ny){
        super.init(nx, ny);
        if(!Main.isNull(Main.window.master.playerObj)){
            Main.window.master.playerObj.remove();
        }
        playerGui = new PlayerGui();
        createObject(playerGui, 0, 0, 0);
        playerGui.player = this;
    }
        
    @Override
    public boolean isCollide(GameObject obj){
        boolean ret = obj.isDensity && (obj.fullHeight || !inFullHeight);
        return ret;
    }
    
    @Override
    public void special(){
        if(checkKeyClick(KeyEvent.VK_F)){
            if(getFlashlight()){
                disableFlashlight();
            }else{
                enableFlashlight();
            }
        }
        
        if(checkKey(KeyEvent.VK_SHIFT)){
                sprint = true;
            }else{
                sprint = false;
            }
        
        if(checkKey(KeyEvent.VK_CONTROL)){
                crawl = true;
            }else{
                crawl = false;
            }

        if((weapon.automatic && checkMouse(MouseEvent.BUTTON1))||(!weapon.automatic && checkMouseClick(MouseEvent.BUTTON1))){
            if((!isMouseOnGui() || playerGui.isMouseOnElement()) && weapon.readyToShot()){
                weapon.shot();
                shooting = true;
            }
        }
        
        if(checkKeyClick(KeyEvent.VK_G)){
            removeWeapon();
        }
        
        if(checkKeyClick(KeyEvent.VK_R)){
            weapon.reload();
        }
    }
    
    @Override
    public void damage(int dmg){
        super.damage(dmg);
        setTimer(HEALING_TIMER, 5);
    }
    
    @Override
    public void moving(){
        Main.window.master.playerObj = this;
            if(alive && can_move){
            int len = 0;
            int slen = 0;
            int sx = 0;
            int sy = 0;
            
            if(checkKey(KeyEvent.VK_S)){
                len = -movespeed;
            }
            if(checkKey(KeyEvent.VK_W)){
                len = movespeed;
            }
            if(checkKey(KeyEvent.VK_A)){
                slen = movespeed;
            }
            if(checkKey(KeyEvent.VK_D)){
                slen = -movespeed;
            }
            direction = Main.get_dir(x+width/2 ,y+height/2, Main.window.master.mouseX-Main.window.master.objShiftX, Main.window.master.mouseY-Main.window.master.objShiftY);
            sx = (int) (((Main.ld_x(len, direction)))+((Main.ld_x(slen, direction+90))));
            sy = (int) (((Main.ld_y(len, direction)))+((Main.ld_y(slen, direction+90))));
            bump(x+sx,y+sy,z);
            if(!canMove(x+sx,y)){sx = 0;}
            if(!canMove(x,y+sy)){sy = 0;}
            if(!falling && !Defines.checkFlag(flags, Defines.PLAYER_CONTROLS_OVERRIDE) && !jump){
                move(x+sx,y+sy);
            }
            Main.window.master.objShiftX = -(x-Defines.WIDTH/2);
            Main.window.master.objShiftY = -(y-Defines.HEIGHT/2); 
            }
    }
    
    @Override
    public void jumping(){
        if(alive && can_move){
            if(checkKeyClick(KeyEvent.VK_SPACE)){
                if(!jump && canJump()){
                    jump = true;
                    jumplen = stJumplen;
                    jump_dir = move_dir;
                    inFullHeight = true;
                    if(jump_dir == -1){
                        jump_dir = direction;
                    }
                }
            }
            if(!falling && !Defines.checkFlag(flags, Defines.PLAYER_CONTROLS_OVERRIDE) && jump){
            int jx = (int)Main.ld_x(movespeed,jump_dir);
            int jy = (int)Main.ld_y(movespeed,jump_dir);
            bump(x+jx,y+jy,z);
            if(!canMove(x+jx,y)){jx = 0;}
            if(!canMove(x,y+jy)){jy = 0;}
            if(jx==0 && jy==0){
                jump = false;
                inFullHeight = false;
                jump_dir = -1;
            }
            if(jump_dir!=-1){
                move(x+jx,y+jy);
            }
            jumplen-=1;
            if(jumplen<=0){
                jump = false;
                inFullHeight = false;
                jump_dir = -1;
            }
            }
            else{
                jump = false;
                inFullHeight = false;
                jump_dir = -1;
            }
        }
    }
    
    @Override
    public void ai(){
        return;
    }
    
    @Override
    public void post(){
        if(!alive && !Main.window.game.levelRestart){
            if(!loadMapOnDeath){
                Main.window.game.levelRestart = true;
            }else{
                hp = 10;
                Main.window.savePlayer(deathMap+".map");
                Main.window.game.currentMap = new File(Main.window.mapDir+deathMap+Main.window.mapFormat);
                Main.window.game.levelRestart = true;
            }
            playerGui.remove();
        }
    }
    
    @Override
    public void doorCollide(Door obj){
        if(obj.canOpen(this) && sprint){
            obj.open();
        }
    }
    
    @Override
    public void onRemove(){
        Main.window.master.playerObj = null;
        playerGui.remove();
    }
}
