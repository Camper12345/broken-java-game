/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.*;
import gameengine.objects.items.LyingWeapon;
import gameengine.objects.mobs.Player;
import gameengine.objects.weapon.Fists;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Camper2012
 */
public class Mob extends DynamicObject{
    public String iconsDir = "mob\\mob";
    
    public int id = -1;
    
    public int hp = 100;
    public int maxhp = 100;
    public boolean can_move = true;
    public boolean alive = true;
    public boolean thinking = true;
    public boolean patrolling = true;
    
    public int stMovespeed = 5;
    public int stSprint = 3;
    public int stCrawl = -2;
    public int movespeed = 5;
    public boolean sprint = false;
    public boolean jump = false;
    public int stJumplen = 8;
    public int jumplen = 0;
    public float jump_dir = -1;
    public boolean crawl = false;
    
    public boolean canKnockout = true;
    public boolean hasStepSound = true;
    
    public boolean shooting = false;
    public boolean falling = false;
    
    public boolean hasStandPoint = true;
    public int standPointX = 0;
    public int standPointY = 0;
    public int standPointZ = 0;
    public float standDir = 0;
       
    public int sight_angle = 80;
    public int sight_dist = 300;
    public int sight_back_dist = 20;
    public int sight_dark_dist = 30;
    public int last_alert_level = 0;
    public int alert_level = 0;
    public int attack_chase_dist = 100;
    public int look_dist = 50;
    public Weapon weapon;
    public Flashlight flashlight;
    public boolean hasFlashLight = true;
    public DynamicObject aiTarget;
    public int patrolPathIndex = -1;
    
    public static int ALERT_ALEVEL = 200;
    public static int ATTACK_ALEVEL = 500;
    public static int ALEVEL_RESET_TIME = 400;
    
    public final static int CHASE_DELAY_TIMER = 0;
    
    public final static int ALERT_LEVEL_ALERT_RESET = 1;
    public final static int STEP_SOUND_PLAYER = 2;
    public final static int LEGS_ANIM = 3;
    public final static int TARGET_THINKING_TIMER = 4;
    public final static int TARGET_LOOKING_TIMER = 5;
    public final static int HEALING_TIMER = 6;
    public final static int PATH_TRY_TIMER = 7;
    public final static int PATH_RESET_TIMER = 8;
    public int healing_time = 10;
    
    @Override
    public void initVars(){
        super.initVars();
        stMovespeed = 5;
        iconsDir = "mob\\mob";
        basicIconName = iconsDir+"\\body";
        layer = Defines.LAYER_MOB;
        stSprint = 3;
        isDensity = true;
        collideType = 1;
        weapon = new Fists();
        weapon = new Weapon();
        //flags |= Defines.MOB_NOFALL;
        SETTING_RANGE = true;
        RangeSettingName = "ПУТЬ";
        DefRange = -1;
        patrolNodeNum = -1;
        SETTING_STRENGTH = true;
        StrengthSettingName = "ID";
        DefStrength = -1;
        StrengthStep = 1;
        iconName = iconsDir+"\\body";
    }
    
    @Override
    public void init(int nx, int ny){
        Main.window.mobs.add(this);
        assignStandPoint(nx,ny,z);
        flashlight = new Flashlight();
        flashlight.initVars();
        disableFlashlight();
        createObject(flashlight, x+width/2, y+height/2, z);
        flashlight.addToContainer(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createObject(weapon,x,y,z);
        weapon.addToContainer(this);
    }
    
    
    
    @Override
    public boolean isCollide(GameObject obj){
        boolean ret = obj.isDensity && (obj.fullHeight || !inFullHeight);
        return ret || obj instanceof AiBlocker;
    }
    
    public void assignStandPoint(int nx, int ny, int nz){
        standPointX = nx+width/2;
        standPointY = ny+height/2;
        standPointZ = nz;
        standDir = direction;
    }
 
    public void onDamage(GameObject obj){
    }
    public void onDeath(){
        isDensity = false;
        layer = Defines.LAYER_OBJECT-0.1f;
        basicIconName = iconsDir+"\\dead";
        iconName = iconsDir+"\\dead";
        disableFlashlight();
        removeWeapon();
        specialDrop();
    }
    
    public void specialDrop(){
        
    }
    
    public GameObject drop(GameObject obj){
        int rnd = (int) ((Math.random()*Defines.ICON_SIZE)-Defines.ICON_SIZE/2);
        return createObject(obj, x+rnd, y+rnd, z);
    }
    
    @Override
    public void onCollide(GameObject obj){
        if(obj instanceof Ladder){
            ladderCollide((Ladder)obj);
        }
        if(obj instanceof Door){
            doorCollide((Door)obj);
        }
    }
       
    @Override
    public void onUpdate(){
        shooting = false;
        if(alive){
            if(getTimer(HEALING_TIMER) || !isTimer(HEALING_TIMER)){
            setTimer(HEALING_TIMER,healing_time);
            if(/*alert_level<ALERT_ALEVEL*/true){
                damage(-1);
            }
        }
        }
        if(alive && thinking){
        if(crawl){
            movespeed = stMovespeed+stCrawl;
        }else if(sprint){
            movespeed = stMovespeed+stSprint;
        }else{
            movespeed = stMovespeed;
        }
        nodemove_dir = true;
        ai();
        moving();
        jumping();
        special();
        }
        if(hasStepSound && z==Main.window.master.currentZ){
        stepsound();
        }
        bump();
        falling();
        post();
    }
    
    public void getWeapon(Weapon wp){
        if(!(weapon instanceof Fists)){
            if(wp.getClass().equals(weapon.getClass())){
                weapon.holdammo += wp.holdammo;
                if(weapon.holdammo > weapon.maxholdammo){
                    weapon.holdammo = weapon.maxholdammo;
                }
            }
        }else{
            weapon.remove();
            wp.removeFromContainer();
            wp.addToContainer(this);
            weapon = wp;
            
        }
    }
    
    public void removeWeapon(){
        if(!(weapon instanceof Fists)){
            LyingWeapon lw = (LyingWeapon) drop(new LyingWeapon());
            weapon.removeFromContainer();
            lw.weapon = weapon;
            lw.weapon.addToContainer(lw);
            weapon = (Weapon) createObject(new Fists(), x, y, z);
            weapon.addToContainer(this);
        }
    }
    
    boolean ljump = false;
    int groundhit = 0;
    public void stepsound(){
        Turf ut = getUnderTurf();
        String nvsnd = "";
        if(!Main.isNull(ut)){
        nvsnd = ut.stepSound;
        }
        String snd = Main.pickStringVariant(nvsnd, 3);
        if(move_dir != -1){
            if(!jump){
                if(getTimer(STEP_SOUND_PLAYER)){
                    if(crawl){
                        playSound(snd,getSoundVolume()-25);
                    }else if(sprint){
                        playSound(snd,getSoundVolume()-10);
                        alertNearMobs(getUnderTurf(), false, true, 70, 0);
                    }else{
                        playSound(snd,getSoundVolume()-15);
                    }
                }
                if(!isTimer(STEP_SOUND_PLAYER)){
                    setTimer(STEP_SOUND_PLAYER, 4);
                    if(crawl){
                        setTimer(STEP_SOUND_PLAYER, 8);
                    }else if(sprint){
                        setTimer(STEP_SOUND_PLAYER, 2);
                    }
                }
            }
        }
        if(ljump && !jump){
            playSound(snd,getSoundVolume()-5);
            Turf jut = getUnderTurf();
            if(!Main.isNull(jut)){
            alertNearMobs(jut, false, true, 80, 0);
            }
        }
        if(!ljump && jump){
            playSound(snd,getSoundVolume()-15);
        }
        if(groundhit>0){
            for(int i = 0;i<groundhit && i<10; i++){
            playSound(Main.pickStringVariant(nvsnd, 3),getSoundVolume()+i*5);
            }
            alertNearMobs(this, false, false, 120, 0);
            groundhit = 0;
        }
        ljump = jump;
    }
    
    public void post(){
        
    }
    
    public void special(){
        
    }
    
    int fallz = 0;
    public void falling(){
        if(!Defines.checkFlag(flags, Defines.MOB_NOFALL) && !underTurf(x,y) && z>Defines.Z_MIN){
            if(!jump){
            falling = true;
            moveZ(z-1);
            updateIcon(true);
            fallz++;
            }
        }else{
            if(fallz > 0){
                groundhit = fallz;
            }
            if(fallz >= 2){
                if(fallz <= 2){
                    damage(20);
                }else if(fallz <= 3){
                    damage(60);
                }else{
                    damage(maxhp);
                }
            }
            fallz = 0;
            falling = false;
        }
    }
    
    String legs = iconsDir="\\legs";
    int legsIndex = 0;
    String hands = iconsDir="\\hands";
    @Override
    public BufferedImage specialUpdateIconUnder(){
        BufferedImage nimg = new BufferedImage(Defines.ICON_SIZE,Defines.ICON_SIZE,BufferedImage.TYPE_INT_ARGB);
        Graphics gr = nimg.getGraphics();
        if(alive){
        if(!isTimer(LEGS_ANIM) || getTimer(LEGS_ANIM)){
            if(move_dir != -1){
                setTimer(LEGS_ANIM,4);
                legsIndex++;
                if(legsIndex>2){
                    legsIndex = 1;
                }
                legs = iconsDir+"\\legs";
                if(crawl){
                    setTimer(LEGS_ANIM,8);
                    legs = iconsDir+"\\legs_crawl";
                }else if(sprint){
                    setTimer(LEGS_ANIM,2);
                    legs = iconsDir+"\\legs_sprint";
                }
            }else{
                setTimer(LEGS_ANIM, -1);
                legsIndex = 0;
                legs = iconsDir+"\\legs";
                if(crawl){
                    legs = iconsDir+"\\legs_crawl";
                }else if(sprint){
                    legs = iconsDir+"\\legs_sprint";
                }
            }
        }
        
        gr.drawImage(Main.window.resource.getIcon(legs).getImage(legsIndex), 0, 0, null);
        
        if(!Main.isNull(weapon) && !"".equals(weapon.handing)){
            if(!weapon.isMelee()){
                hands = iconsDir+"\\hands_"+weapon.handing;
            }else{
                if(weapon.shot){
                    hands = iconsDir+"\\hands_mw";
                }else{
                    hands = iconsDir+"\\hands";
                }
            }
        }else{
            hands = iconsDir+"\\hands";
        }
        if(crawl){
            iconName = iconsDir+"\\body_crawl";
        }else{
            iconName = iconsDir+"\\body";
        }
        gr.drawImage(Main.window.resource.getIcon(hands).getImage(), 0, 0, null);
        
        if(!Main.isNull(weapon)){
            gr.drawImage(Main.window.resource.getIcon(weapon.wpIcon+"_hold").getImage(), 0, 0, null);
        }
        
        }else{
            
        }
        return nimg;
    }
        
    @Override
    public Rectangle getCollideShape(int nx, int ny){
        Rectangle collsh = new Rectangle(nx+width/4,ny+height/4,(width/2)-1,(height/2)-1);       
        return collsh;
    }
    
    public void moving(){
        pathfinding();
    }
    
    public void jumping(){
        
    }
    
    public boolean canJump(){
        return Defines.checkFlag(flags, Defines.MOB_NOFALL) || underTurf(x,y);
    }
    
    public boolean isInSight(DynamicObject obj){
        float dist = getDistance(obj);
        float dir = Main.get_dir(x, y, obj.x, obj.y);
        return obj.z == z && (dist <= sight_back_dist || Main.dir_diff(direction, dir)<=sight_angle) && (dist <= sight_dark_dist || obj.lighting<=252) 
                && dist <= sight_dist && !isSightBlocked(obj);
    }
    
    public boolean getFlashlight(){
        return flashlight.brightness>0;
    }
    
    public void enableFlashlight(){
        if(hasFlashLight){
            flashlight.brightness = 210;
        }else{
            flashlight.brightness = 0;
        }
    }
    
    public void disableFlashlight(){
        flashlight.brightness = 0;
    }
    
    @Override
    public void onShot(Ammo ammo){
        damage((int) (ammo.damage+((Math.random()*(ammo.damage))-ammo.damage/2)));
        if(alert_level < ALERT_ALEVEL && ammo instanceof MeleeAmmo && !(this instanceof Player) && canKnockout && !isInSight(ammo.ignoring)){
            damage(maxhp);
        }else{
        if(alert_level < ALERT_ALEVEL){
            alert_level = ALERT_ALEVEL;
        }
        if(alert_level < ATTACK_ALEVEL){
        if(isInSight(ammo.ignoring) || ammo instanceof MeleeAmmo){
            aiTarget = ammo.ignoring;
        }
        direction = Main.get_dir(x, y, ammo.ignoring.x, ammo.ignoring.y);
        }
        }
    }   
    
    @Override
    public void setStrengthSetting(int str){
        id = str;
        DefStrength = str;
    }
    
    //////////////////////////////////
    //ARTIFICAL INTELLIGENCE
    //////////////////////////////////
    public boolean see_target = false;
    public void ai(){
        sprint = false;
        DynamicObject pl = aiTarget;
        nonTarget();
        if(!Main.isNull(pl)){
            last_alert_level = alert_level;
            if(aiTarget instanceof Mob){
                if(isInSight(pl)){
                    onTargetSee();
                    see_target = true;
                }else{
                    if(see_target){
                        onTargetLost();
                    }else{
                        onTargetNotSee();
                        
                    }
                    see_target = false;
                }
            }else{
                if(alert_level >= ALERT_ALEVEL){
                    if(isInSight(pl)){
                        onTargetSee();
                    }
                    if(!((getDistance(pl)<=look_dist) && !isSightBlocked(pl) && z==pl.z)){
                        if(!isMoving()){
                            startMoveTo(pl.x+pl.width/2, pl.y+pl.height/2, pl.z, pl);
                            setPathReset();
                        }
                    }else{
                        looking();
                    }
                    
                    if(isMoving() && getDistance(pl)<=look_dist && isInSight(pl) && z==pl.z){
                        stopMoving();
                    }
                    
                    DynamicObject dobj = searchTarget();
                    if(!Main.isNull(dobj)){
                        aiTarget = dobj;
                    }
                }else{
                    aiTarget = null;
                }
            }
            if(!Main.isNull(aiTarget) && aiTarget.removed){
                aiTarget = null;
                primeAlertTimer();
            }
        }else{
            if(alert_level>=ALERT_ALEVEL){
            looking();
            }else{
                patrolling();
            }
            aiTarget = searchTarget();
        }
    }
    int patrolNodeNum = -1;
    public void patrolling(){
        if(!isMoving() && patrolling){
            if(patrolPathIndex >= 0){
                for(DynamicObject mrk : Main.window.nodes){
                    if(mrk instanceof MobPatrolNode){
                        MobPatrolNode mp = (MobPatrolNode) mrk;
                        if(mp.pathIndex == patrolPathIndex && mp.nodeNumber == patrolNodeNum+1){
                            startMoveTo(mp.x+mp.width/2, mp.y+mp.height/2, mp.z);
                            patrolNodeNum = mp.nodeNumber;
                            return;
                        }
                    }

                }
                for(DynamicObject mrk : Main.window.nodes){
                    if(mrk instanceof MobPatrolNode){
                        MobPatrolNode mp = (MobPatrolNode) mrk;
                        if(mp.pathIndex == patrolPathIndex && mp.nodeNumber == 0){
                            startMoveTo(mp.x+mp.width/2, mp.y+mp.height/2, mp.z);
                            patrolNodeNum = mp.nodeNumber;
                            return;
                        }
                    }

                }
            }else{
                if(hasStandPoint){
                    startMoveTo(standPointX,standPointY,standPointZ);  
                    if(getDistance(standPointX, standPointY)<=movespeed && z == standPointZ){
                        direction = standDir;
                    }
                }
            }
        }
    }
    
    public Mob searchTarget(){
        Mob trg = null;
        int mdist = Integer.MAX_VALUE;
            for(Mob ptrg : Main.window.mobs){
                if(isAttack(ptrg) && !ptrg.removed && ptrg.alive && isInSight(ptrg)){
                    if(getDistance(ptrg)<mdist){
                        trg = ptrg;
                        mdist = getDistance(ptrg);
                    }
                }
            }
        return trg;
    }
    
    public void nonTarget(){
        drawAlertLevel();
        if(getTimer(ALERT_LEVEL_ALERT_RESET)){
            alert_level = 0;
        }
        if(cover_lamps.isEmpty()){
                enableFlashlight();
            }else{
                if(getFlashlight()){
                    disableFlashlight();
                }
            }
        weapon.holdammo = weapon.maxammo;
        weapon.direction = direction;
    }
    
    public int lastTargetX = 0;
    public int lastTargetY = 0;
    public int lastTargetZ = 0;
    
    public void onTargetSee(){
        if(Main.isNull(aiTarget)){
            return;
        }
        DynamicObject tobj = aiTarget;
        if(tobj instanceof Mob){
        Mob pl = (Mob)tobj;
        direction = Main.get_dir(x, y, tobj.x, tobj.y);
        if(!isAllied(pl)){
        int alertadd = 15-((getDistance(pl)/sight_dist)*15);
        if(alert_level>=0){
        if(pl.sprint || pl.jump){alertadd = (int) (alertadd * 3.5f);} 
        if(pl.shooting){alertadd = (int) (ATTACK_ALEVEL-alert_level);} 
        }
        if(alert_level + Math.abs(alertadd)<=Defines.ALERT_LEVEL_MAX){
        alert_level += Math.abs(alertadd);
        if(pl.sprint){
            sprint = true;
        }
        }
        
        if(getTimer(TARGET_THINKING_TIMER)){
            setTimer(ALERT_LEVEL_ALERT_RESET,1);
            aiTarget = null;
        }
        
        if(alert_level>=ALERT_ALEVEL && last_alert_level<=ALERT_ALEVEL){
            alertNearMobs(pl, false, true, sight_dist, 0);
        }
        
        if(alert_level>=ATTACK_ALEVEL && last_alert_level<=ATTACK_ALEVEL){
            
        }
        
        if(alert_level >= ALERT_ALEVEL){
            nodemove_dir = false;
        }else{
            nodemove_dir = true;
        }
        
        if(alert_level >= ATTACK_ALEVEL){
            attackTarget();
            chasing();
        }
        
        if(!pl.alive){
            aiTarget = null;
            primeAlertTimer();
        }
        }else{
            if(!isTimer(TARGET_THINKING_TIMER)){
                setTimer(TARGET_THINKING_TIMER,20);
            }
        }        
        
        }else{
            if(!isTimer(TARGET_THINKING_TIMER) && !isTimer(TARGET_LOOKING_TIMER)){
            setTimer(TARGET_THINKING_TIMER,20);
            }
            
            if(getTimer(TARGET_THINKING_TIMER)){
                setTimer(TARGET_LOOKING_TIMER,80);
            }

            if(isTimer(TARGET_THINKING_TIMER)){
                direction = Main.get_dir(x, y, tobj.x, tobj.y);
            }
            
            if(isTimer(TARGET_LOOKING_TIMER)){
                looking();
            }

            if(getTimer(TARGET_LOOKING_TIMER)){
                aiTarget = null;
                setTimer(ALERT_LEVEL_ALERT_RESET,1);
                setTimer(TARGET_THINKING_TIMER,-1);
                setTimer(TARGET_LOOKING_TIMER,-1);
            }
        }
        
        lastTargetX = tobj.x+tobj.width/2;
        lastTargetY = tobj.y+tobj.height/2;
        lastTargetZ = tobj.z;
    }
    
    public void drawAlertLevel(){
        Graphics gr = Main.window.master.over;
        if(alert_level>0 && z == Main.window.master.currentZ){
        int bwd = Defines.ICON_SIZE-12*2;
        int alert = (int) Main.bound(((float)alert_level/ALERT_ALEVEL)*bwd,0,bwd);
        int attack = (int) Main.bound((((float)alert_level-ALERT_ALEVEL)/(ATTACK_ALEVEL-ALERT_ALEVEL))*bwd,0,bwd);
        gr.setColor(new Color(48,48,48));
        gr.fillRect(scrX+12, scrY-8, bwd, 6);
        gr.setColor(Defines.LOADING_YELLOW);
        gr.fillRect(scrX+12, scrY-8, alert, 6);
        
        gr.setColor(Defines.LOADING_RED);
        gr.fillRect(scrX+12, scrY-8, attack, 6);
        if(alert_level>ALERT_ALEVEL){
            gr.setColor(new Color(128,0,0));
        }else{
            gr.setColor(new Color(128,128,0));
        }
        gr.drawRect(scrX+12, scrY-8, bwd, 6);
        }
        if(alert_level>=ALERT_ALEVEL && z == Main.window.master.currentZ){
            float hppc = (float)hp/(float)maxhp;
            gr.setColor(new Color(64,64,64));
            gr.fillRect(scrX+4, scrY, 24, 3);
            gr.setColor(new Color((int)(64+(1-hppc)*(255-64)),(int)(hppc*255),(int)((1-hppc)*64)));
            gr.fillRect(scrX+4, scrY, (int) (hppc*24), 3);
            gr.drawRect(scrX+4, scrY, 24, 3);
        }
    }
    
    public void chasing(){
        DynamicObject pl = aiTarget;
        if(!Main.isNull(pl)){
            if(!shotAllowed() || getDistance(pl)>weapon.getAttackDist()){
                if(!isMoving()){
                    startMoveTo(pl.x+pl.width/2,pl.y+pl.height/2,pl.z,pl);
                }
                nodemove_dir = false;
            }
        }
    }
    
    public boolean isAlliedOnFire(){
        for(Object gobj : weapon.getCollideLineList(aiTarget.x+aiTarget.width/2, aiTarget.y+aiTarget.height/2, z, aiTarget, this)){
            GameObject obj = (GameObject) gobj;
            if(obj instanceof Mob){
                Mob m = (Mob) obj;
                if(isAllied(m)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean shotAllowed(){
        DynamicObject pl = aiTarget;
        return !weapon.getCollideLine(pl,this) && !isAlliedOnFire();
    }
    
    public void attackTarget(){
        if(Main.isNull(aiTarget)){
            return;
        }
        DynamicObject pl = aiTarget;
        if(!Main.isNull(pl)){
            setTimer(TARGET_THINKING_TIMER, -1);
            setTimer(TARGET_LOOKING_TIMER, -1);
            setTimer(ALERT_LEVEL_ALERT_RESET, -1);
            direction = Main.get_dir(x, y, pl.x, pl.y);
            if(shotAllowed()){
                if(getDistance(pl)<=weapon.getAttackDist()){
                weapon.direction = Main.get_dir(x, y, pl.x, pl.y);
                if(weapon.readyToShot() && weapon.hasAmmo()){
                weapon.shot();
                shooting = true;
                }else{
                    if(!weapon.hasAmmo()){
                        weapon.holdammo = weapon.maxammo;
                        weapon.reload();
                        weapon.holdammo = weapon.maxammo;
                    }
                }
                }
                nodemove_dir = true;
            }else{
                if(!isMoving()){
                startMoveTo(lastTargetX,lastTargetY,lastTargetZ,pl);
                }
            }
        }
    }
    
    public void primeAlertTimer(){
        alert_level = ALERT_ALEVEL;
        setTimer(ALERT_LEVEL_ALERT_RESET,ALEVEL_RESET_TIME);
    }
    
    public void onTargetLost(){
        if(Main.isNull(aiTarget)){
            return;
        }
        sprint = false;
        DynamicObject pl = aiTarget;
        nodemove_dir = true;
        if(alert_level>=ALERT_ALEVEL){
            stopMoving();
            startMoveTo(pl.x+pl.width/2,pl.y+pl.height/2,pl.z);
            setTimer(CHASE_DELAY_TIMER,5);
        }
        if(alert_level >= ATTACK_ALEVEL){
            primeAlertTimer();
        }else{
            alert_level = 0;
            aiTarget = null;
        }
    }
    
    int around_angle = 0;
    public void looking(){
        if(Main.prob(15)){
            if(around_angle == 0){
                around_angle = (int) (Math.random()*20-10);
            }else{
                around_angle = 0;
            }
        }
        direction += around_angle;
    }
    
    public void onTargetNotSee(){
        if(alert_level >= ALERT_ALEVEL && !isMoving()){
            looking();
        }
        sprint = false;
        if(getTimer(CHASE_DELAY_TIMER)){
            DynamicObject pl = aiTarget;
            startMoveTo(pl.x+pl.width/2,pl.y+pl.height/2,pl.z,pl);
        }
    }
       
    public void startMoveTo(int nx, int ny, int nz){
        startMoveTo(nx,ny,nz,null);
    }
    
    public void startMoveTo(int nx, int ny, int nz, GameObject tobj){
        stopMoving();
        target = true;
        targetX = nx;
        targetY = ny;
        targetZ = nz;
        targetObj = tobj;
    }
    
    public void stopMoving(){
        target = false;
        node = false;
        targetObj = null;
        path.clear();
        nodemove_reset();
    }

    public void setPathReset(){
        setTimer(PATH_RESET_TIMER, 1);
    }
    
    @Override
    public void onTimer(int id) {
        if(id == PATH_RESET_TIMER){
            if(path.isEmpty() && !isMoving()){
                aiTarget = null;
            }
        }
    }
    
    
    
    public boolean isMoving(){
        return target;
    }
    
    public boolean isMovingTo(int mx, int my, int mz){
        return target&&(targetX == mx)&&(targetY == my)&&(targetZ == mz);
    }
    
    public void damage(int dmg){
        if(alive){
        if(hp-dmg<=maxhp && hp-dmg>0){
            hp = hp-dmg;
        }else if(hp-dmg<=0){
            hp = 0;
            alive = false;
            onDeath();
        }else if(hp-dmg>maxhp){
            hp = maxhp;
        }
        }
    }
    
    public void ladderCollide(Ladder obj){
        if(move_dir != -1){
            if(Main.dir_diff(obj.direction, move_dir)<70){
                if(getDistance(obj.x+obj.width/2, obj.y+obj.height/2)< Defines.ICON_SIZE/2 && Main.window.isTypeOnPos(obj.x, obj.y, obj.z+1, obj) && obj.z == z && 
                        getCollideList(x,y,z+1,false,false,false).isEmpty() && underTurf(x,y,z+1)){
                    moveZ(z+1);
                }
            }else if(Main.dir_diff(obj.direction, move_dir)>120 && !jump){
                if(getDistance(obj.x+obj.width/2, obj.y+obj.height/2)< Defines.ICON_SIZE/2 && Main.window.isTypeOnPos(obj.x, obj.y, obj.z-1, obj) && obj.z == z && 
                        getCollideList(x,y,z-1,false,false,false).isEmpty() && underTurf(x,y,z-1)){
                    moveZ(z-1);
                }
            }
        }
    }
    
    public void doorCollide(Door obj){
        if(obj.canOpen(this)){
            obj.open();
        }
    }
    
    public boolean isAttack(Mob mob){
        return mob instanceof Player && !checkAllowed(mob);
    }
    
    public boolean checkAllowed(Mob mob){
        return isAllied(mob) || (mob.collidesND.contains(new AllowedZone()) && !(mob.shooting));
    }
    
    public boolean isAllied(Mob mob){
        return !(mob instanceof Player);
    }
    
    public boolean reactOn(DynamicObject obj){
        return true;
    }
    
    @Override
    public void onRemove(){
        Main.window.mobs.remove(this);
    }
    
    ///////////////////////////////////////
    //PATHFINDING
    ///////////////////////////////////////
    
    public int targetX = 0;
    public int targetY = 0;
    public int targetZ = 0;
    public GameObject targetObj = null;
    public boolean target = false;
    
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 4;
    public static final int RIGHT = 8;
    
    public static final float UPD = 90;
    public static final float DOWND = 270;
    public static final float LEFTD = 180;
    public static final float RIGHTD = 0;
    
    public ArrayList<NavDatum> path = new ArrayList();
    public int nodeX = 0;
    public int nodeY = 0;
    public int nodeZ = 0;
    public boolean node = false;
    public boolean nodemove_dir = true;
    public float nextDir = -1;
    public float lastReturnDir = -1;
    public int closedDir = 0;
    public int lastClosedDir = 0;
    public float lastDir = -1;
    public float lastDir2 = -1;
        
    public void pathfinding(){
        if(target){
            if(getDistance(targetX, targetY)<movespeed && z==targetZ 
                    /*|| !getCollideList(targetX-width/2, targetY-height/2, targetZ,false,false,false).isEmpty()*/){
                target = false;
                return;
            }
            if(!(targetX == x && targetY == y && targetZ == z)){
                if(!node){
                    if(path.size()>0){
                        if(!getCollideLine(targetX,targetY,targetZ,targetObj,this) && z==targetZ){
                            nodeX = targetX;
                            nodeY = targetY;
                            nodeZ = targetZ;
                            node = true;
                            nodemove_reset();
                        }else{
                            nodeX = path.get(0).x;
                            nodeY = path.get(0).y;
                            nodeZ = path.get(0).z;
                            path.remove(0);
                            node = true;
                            nodemove_reset();
                        }
                        
                    }else{
                        if(!getCollideLine(targetX,targetY,targetZ,targetObj,this) && z==targetZ){
                            nodeX = targetX;
                            nodeY = targetY;
                            nodeZ = targetZ;
                            node = true;
                            nodemove_reset();
                        }else{
                            if(!isTimer(PATH_TRY_TIMER)){
                            astar_to_target();
                                setTimer(PATH_TRY_TIMER, 50);
                            }
                        }
                    }
                }else{
                    node_move();
                }
            }else{
                target = false;
            }
        }
    }
        
    void node_move(){
        boolean canDirectMove = true;
        Point up = new Point(x+Main.ld_x(movespeed, UPD),y+Main.ld_y(movespeed, UPD));
        Point down = new Point(x+Main.ld_x(movespeed, DOWND),y+Main.ld_y(movespeed, DOWND));
        Point left = new Point(x+Main.ld_x(movespeed, LEFTD),y+Main.ld_y(movespeed, LEFTD));
        Point right = new Point(x+Main.ld_x(movespeed, RIGHTD),y+Main.ld_y(movespeed, RIGHTD));  
        float directd = Main.get_dir(x+width/2, y+height/2, nodeX, nodeY);
        Point direct = new Point(x+Main.ld_x(movespeed*2, directd),y+Main.ld_y(movespeed*2, directd));
        
        ArrayList<GameObject> upc = getCollideList(up.x,up.y);
        ArrayList<GameObject> downc = getCollideList(down.x,down.y);
        ArrayList<GameObject> leftc = getCollideList(left.x,left.y);
        ArrayList<GameObject> rightc = getCollideList(right.x,right.y);
        ArrayList<GameObject> directc = getCollideList(direct.x,direct.y);
        
        bump(direct.x,direct.y,z);
        
        float retdir = Main.bound_dir(lastDir+180);
        ArrayList<Float> dirs = new ArrayList();
        Graphics gr = Main.window.master.over; //GRAPHICS DEBUG
        closedDir = 0;
        if(upc.isEmpty()){
            if(UPD!=retdir){
            dirs.add(UPD);
            }else{
                closedDir |= UP;
            }
        }else{
            closedDir |= UP;
        }
        if(downc.isEmpty()){
            if(DOWND!=retdir){
            dirs.add(DOWND);
            }else{
                closedDir |= DOWN;
            }
        }else{
            closedDir |= DOWN;
        }
        if(leftc.isEmpty()){
            if(LEFTD!=retdir){
            dirs.add(LEFTD);
            }else{
                closedDir |= LEFT;
            }
        }else{
            closedDir |= LEFT;
        }
        if(rightc.isEmpty()){
            if(RIGHTD!=retdir){
            dirs.add(RIGHTD);
            }else{
                closedDir |= RIGHT;
            }
        }else{
            closedDir |= RIGHT;
        }
        if(directc.isEmpty()){
            ArrayList ddir = new ArrayList();
            for(int i = 0; i<=dirs.size()-1;i++){
                    float dr = Main.dir_diff(dirs.get(i),directd);
                    gr.setColor(Color.orange);
                    if(dr<90){
                        ddir.add(dirs.get(i));  
                        gr.setColor(Color.green);
                        
                    }
                    if(Main.window.master.drawInvs)gr.fillOval(scrX+width/2+Main.ld_x(20, dirs.get(i))-5, scrY+height/2+Main.ld_y(20, dirs.get(i))-5, 10, 10);
                }
            if(ddir.size()>=1){
            nextDir = directd;
            }else{
                canDirectMove = false;
            }
        }else{
            canDirectMove = false;
        }
        if(closedDir == lastClosedDir){
            if(!canDirectMove){
            nextDir = lastDir;
            }
        }else{
            if(!canDirectMove){
                if(dirs.size()>1 && (closedDir == 0 || dirs.size() == 3)){
                    dirs.remove(retdir);
                    gr.setColor(Color.blue);
                    if(Main.window.master.drawInvs) gr.fillOval(scrX+width/2+Main.ld_x(30, retdir)-5, scrY+height/2+Main.ld_y(30, retdir)-5, 10, 10);
                }
                float tdist = Float.MAX_VALUE;
                for(int i = 0; i<=dirs.size()-1;i++){
                    Point tp = new Point(x+width/2+Main.ld_x(movespeed,dirs.get(i)),y+height/2+Main.ld_y(movespeed,dirs.get(i)));
                    float dr = (float) tp.distance(nodeX+width/2, nodeY+height/2);
                    if(dr<tdist){
                        tdist = dr;
                        nextDir = dirs.get(i);               
                    }
                }
            }
        }
        if(Main.window.master.drawInvs){
        for(int i = 0; i<=dirs.size()-1; i++){
                if(dirs.get(i)==nextDir){
                    gr.setColor(Color.GREEN);
                }else{
                    gr.setColor(Color.red);
                }
                gr.drawLine(scrX+width/2,scrY+height/2, scrX+width/2+Main.ld_x(20,dirs.get(i)),scrY+height/2+Main.ld_y(20,dirs.get(i)));
                gr.setColor(Color.yellow);
                gr.fillOval(nodeX+Main.window.master.objShiftX-4, nodeY+Main.window.master.objShiftY-4, 8, 8);
            }
        }
        
        if(!dirs.isEmpty()){
            move(x+Main.ld_x(movespeed, nextDir), 
                        y+Main.ld_y(movespeed, nextDir));
            if(nodemove_dir){
            direction = nextDir;
            }
        }else{
            node = false;
        }
        if(true){
        lastDir = nextDir;
        }
        lastReturnDir = retdir;
        lastClosedDir = closedDir;
        lastDir2 = lastDir;
        
        if(z!=nodeZ && target){
            startMoveTo(targetX, targetY, targetZ);
        }        
        if(new Point(nodeX,nodeY).distance(x+width/2, y+height/2)<movespeed){
            node = false;
        }
    }
    
    void nodemove_reset(){
        nextDir = -1;
        lastReturnDir = -1;
        closedDir = 0;
        lastClosedDir = 0;
        lastDir = -1;
        lastDir2 = -1;
    }
    
    void astar_to_target(){
        if(Main.window.game.levelRestart){
            return;
        }
        long pftime = Calendar.getInstance().getTimeInMillis();
        int hsz = Defines.ICON_SIZE/2;
        NavDatum endpoint = null;
        ArrayList<NavNode> epcand = new ArrayList();
        float maxed = Float.MAX_VALUE;
        for(DynamicObject mrk : Main.window.nodes){
            if(mrk instanceof NavNode){
                NavNode nn = (NavNode) mrk;
            if(!nn.getNodeLine(targetX, targetY, targetZ, targetObj, this)){
                epcand.add(nn);
            }
            }
        }
        for(NavNode nn : epcand){
            if(nn.getDistance(targetX, targetY, targetZ)<maxed){
                maxed = nn.getDistance(targetX, targetY, targetZ);
                endpoint = new NavDatum(nn.x+hsz,nn.y+hsz,nn.z);
            }
        }
        ArrayList<NavDatum> open = new ArrayList();
        NavDatum sp = new NavDatum(x+width/2,y+height/2,z);
        sp.g = 0;
        sp.h = 0;
        open.add(sp);
        ArrayList<NavDatum> closed = new ArrayList();  
        while(true){
            if(open.isEmpty()){
                target = false;
                node = false;
                break;
            }
            
            NavDatum cpoint = null;
            float maxf = Float.MAX_VALUE;
            for(NavDatum pnt : open){
                if(pnt.g+pnt.h<maxf){
                    maxf = pnt.g+pnt.h;
                    cpoint = pnt;
                }
            }

            if(Main.isNull(cpoint)){
                Main.error("Error at "+this+" while astaring to taget. Cpoint is null.");
                break;
            }
            closed.add(cpoint);
            open.remove(cpoint);
            for(DynamicObject mrk : Main.window.nodes){
            if(mrk instanceof NavNode){
                NavNode nn = (NavNode) mrk;
                if(!nn.getNodeLine(cpoint.x, cpoint.y, cpoint.z, targetObj, this)){
                    NavDatum nd = new NavDatum(nn.x+hsz, nn.y+hsz, nn.z);
                    nd.parent = cpoint;
                    nd.g = cpoint.g+nd.getDistance(cpoint.x, cpoint.y, cpoint.z);
                    nd.h = 0/*(Math.max(Math.abs(endpoint.x-nd.x),Math.abs(endpoint.y-nd.y))
                      -Math.min(Math.abs(endpoint.x-nd.x),Math.abs(endpoint.y-nd.y)))*10
                      /*+Math.abs(nd.z-cpoint.z)*/;
                    if(closed.contains(nd)){
                        continue;
                    }
                    if(nd.equals(cpoint)){
                        continue;
                    }
                    if(!open.contains(nd)){
                        open.add(nd);
                    }else{
                        NavDatum ond = open.get(open.indexOf(nd));
                        if(cpoint.g+cpoint.getDistance(ond.x, ond.y, ond.z)<ond.g){
                            ond.parent = cpoint;
                            ond.g = cpoint.g+cpoint.getDistance(ond.x, ond.y, ond.z);
                        }
                    }
                }else{
                }
            }
            }
            
            if(open.contains(endpoint)){
                ArrayList<NavDatum> invpath = new ArrayList();
                NavDatum tp = open.get(open.indexOf(endpoint));
                while(!Main.isNull(tp)){
                    invpath.add(tp);
                    tp = tp.parent;
                }
                ArrayList<NavDatum> npath = new ArrayList();
                for(int i = invpath.size()-1; i>=0; i--){
                    npath.add(invpath.get(i));
                }
                npath.remove(0);
                npath.add(new NavDatum(targetX,targetY,targetZ));
                path.clear();
                path.addAll(npath);
                break;
            }
        }  
        //System.out.println("ASTAR "+this+" in "+(Calendar.getInstance().getTimeInMillis()-pftime)+" ms");
    }
    
    @Override
    public void setRangeSetting(int id){
        patrolPathIndex = id;
        DefRange = id;
    }    
}
