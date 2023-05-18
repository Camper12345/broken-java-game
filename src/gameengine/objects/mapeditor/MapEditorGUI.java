/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.mapeditor;
import gameengine.*;
import gameengine.objects.*;
import gameengine.objects.gui.GridGUI;
import gameengine.objects.items.*;
import gameengine.objects.light.*;
import gameengine.objects.mobs.*;
import gameengine.objects.props.*;
import gameengine.objects.scenario.*;
import gameengine.objects.turf.*;
import gameengine.objects.wall.*;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Camper2012
 */
public class MapEditorGUI extends GUI {
    ToggleButton spawnbtn;
    ToggleNumSelector gridsize;
    NumSelector dir;
    MapEditObject spawn_obj;
    Button loadmap;
    Button savemap;
    Text nametxt;
    GridGUI grid;
    
    ItemSelector category;
    
    ItemSelector scene;
    ItemSelector doors;
    ItemSelector solid;
    ItemSelector lights;
    ItemSelector markers;
    ItemSelector turfs;
    ItemSelector walls;
    ItemSelector mobs;
    ItemSelector decors;
    ItemSelector items;
    
    ToggleButton settbtn;
    
    NumSelector clrr;
    NumSelector clrg;
    NumSelector clrb;
    
    NumSelector range;
    NumSelector strength;
    
    ItemSelector status;
    
    NumSelector zlvl;
    
    Button clsbtn;
    
    public Color setColor = new Color(0,0,0);
    public int setRange = 0;
    public int setStrength = 0;
    public boolean setStat = false;
    
    @Override  
    public void init(int nx, int ny){
        Main.window.master.drawInvs = true;
        nametxt = (Text) addElement(new Text(),16,10);
        nametxt.updateText("РЕДАКТОР КАРТЫ");
        nametxt.updateColor(Color.orange);
        spawnbtn = (ToggleButton) addElement(new ToggleButton(),16,40);
        spawnbtn.setTxt("РЕДАКТ.");
        savemap = (Button) addElement(new Button(),192+192,40);
        savemap.setTxt("СОХРАНИТЬ");
        loadmap = (Button) addElement(new Button(),192+192,40+40);
        loadmap.setTxt("ЗАГРУЗИТЬ");
        gridsize = (ToggleNumSelector) addElement(new ToggleNumSelector(),192,40);
        gridsize.max = Defines.CHUNK_SIZE;
        gridsize.min = 1;
        gridsize.value = 32;
        gridsize.pressed = true;
        gridsize.prefix = "СЕТКА [";
        dir = (NumSelector) addElement(new NumSelector(),192,40+40);
        dir.max = 360;
        dir.min = 0;
        dir.cycle = true;
        dir.clickable = false;
        dir.incstep = 90;
        dir.decstep = 90;
        dir.value = 0;
        dir.prefix = "УГОЛ [";
        spawn_obj = (MapEditObject) createObject(new MapEditObject(), 0,0,0);
        grid = (GridGUI) addElement(new GridGUI(),0,0);
        grid.gridcolor = new Color(64,64,255);
        settbtn = (ToggleButton) addElement(new ToggleButton(),16,40+8+32+40+40);
        settbtn.setTxt("+");
        settbtn.pressed = true;
        clrr = (NumSelector) addElement(new NumSelector(),16+32,40+8+32+40+40);
        clrr.max = 255;
        clrr.incstep = 5;
        clrr.decstep = 5;
        clrr.clickable = false;
        clrg = (NumSelector) addElement(new NumSelector(),16+96+32,40+8+32+40+40);
        clrg.max = 255;
        clrg.incstep = 5;
        clrg.decstep = 5;
        clrg.clickable = false;
        clrb = (NumSelector) addElement(new NumSelector(),16+96+96+32,40+8+32+40+40);
        clrb.max = 255;
        clrb.incstep = 5;
        clrb.decstep = 5;
        clrb.clickable = false;
        range = (NumSelector) addElement(new NumSelector(),16+32,40+8+32+40+40+40);
        range.max = 1000;
        range.min = -1000;
        range.incstep = 1;
        range.decstep = 1;
        range.prefix = "РАДИУС [";
        range.clickable = false;
        strength = (NumSelector) addElement(new NumSelector(),16+32,40+8+32+40+40+40+40);
        strength.max = 1000;
        strength.min = -1000;
        strength.incstep = 1;
        strength.decstep = 1;
        strength.prefix = "СИЛА [";
        strength.clickable = false;
        status = (ItemSelector) addElement(new ItemSelector(),16+32,40+8+32+40+40+40+40+40);
        status.addItem((Object)false, "false");
        status.addItem((Object)true, "true");
        status.clickable = false;
        clsbtn = (Button) addElement(new Button(),Defines.WIDTH-16-40,16);
        clsbtn.setTxt("X");
        zlvl = (NumSelector) addElement(new NumSelector(), 192+96,40+40+40);
        zlvl.min = -100;
        zlvl.max = 100;
        zlvl.value = 0;
        zlvl.prefix = "Z[";
        zlvl.clickable = false;
                  
        category = (ItemSelector) addElement(new ItemSelector(), 16,40*2);
        category.clickable = false;
        
        scene = (ItemSelector) addElement(new ItemSelector(), 16, 40+8+32+40);
        scene.clickable = false;
        scene.isVisible = false;
        
        doors = (ItemSelector) addElement(new ItemSelector(), 16, 40+8+32+40);
        doors.clickable = false;
        doors.isVisible = false;
        
        solid = (ItemSelector) addElement(new ItemSelector(), 16, 40+8+32+40);
        solid.clickable = false;
        solid.isVisible = false;
        
        lights = (ItemSelector) addElement(new ItemSelector(), 16, 40+8+32+40);
        lights.clickable = false;
        lights.isVisible = false;
        
        markers = (ItemSelector) addElement(new ItemSelector(), 16, 40+8+32+40);
        markers.clickable = false;
        markers.isVisible = false;
        
        turfs = (ItemSelector) addElement(new ItemSelector(), 16, 40+8+32+40);
        turfs.clickable = false;
        turfs.isVisible = false;
        
        walls = (ItemSelector) addElement(new ItemSelector(), 16, 40+8+32+40);
        walls.clickable = false;
        walls.isVisible = false;
        
        decors = (ItemSelector) addElement(new ItemSelector(), 16, 40+8+32+40);
        decors.clickable = false;
        decors.isVisible = false;
        
        mobs = (ItemSelector) addElement(new ItemSelector(), 16, 40+8+32+40);
        mobs.clickable = false;
        mobs.isVisible = false;
        
        items = (ItemSelector) addElement(new ItemSelector(), 16, 40+8+32+40);
        items.clickable = false;
        items.isVisible = false;

        addObj(mobs, new Player(), "Player");
        addObj(mobs, new PrisonGuard(), "PrsGuard");
        addObj(mobs, new BaseGuard(), "BsGuard");  
        addObj(mobs, new Police(), "Officer");
        addObj(mobs, new Turret(), "Turret"); 
        addObj(mobs, new Hobo(), "Hobo"); 
        addObj(mobs, new Rat(), "Rat");
        
        addObj(turfs,new Dirt(), "Dirt");
        addObj(turfs,new DirtLadder(), "Dirt ladder");
        addObj(turfs,new Grass(), "Grass");
        addObj(turfs,new MetalFloor(), "Metal");
        addObj(turfs,new MetalLadder(), "Metal ladder");
        addObj(turfs,new RustedMetalFloor(), "RMetal");
        addObj(turfs,new RustedMetalLadder(), "RMetal ladder");
        addObj(turfs,new Smoothstone(), "Stone");
        addObj(turfs,new SmoothstoneLadder(), "Stone ladder");
        addObj(turfs,new Concrete(), "Concrete");
        addObj(turfs,new ConcreteLadder(), "Concrete ladder");
        addObj(turfs,new Catwalk(), "Catwalk");
        addObj(turfs,new CatwalkLadder(), "Catwalk ladder");
        
        addObj(walls, new Wall(), "Wall");
        addObj(walls, new MetalWall(), "Metal");
        addObj(walls, new RustedMetalWall(), "RMetal");
        addObj(walls, new Stonewall(), "Stone");
        addObj(walls, new ConcreteWall(), "Concrete");
        addObj(walls, new RedBrick(), "Red brick");
        addObj(walls, new WhiteBrick(), "White brick");
        addObj(walls, new InvsWall(), "Invisible wall");
                
        addObj(solid,new Fence(), "Fence");
        addObj(solid,new HighFence(), "High fence");
        addObj(solid,new Barrier(), "Barrier");
        addObj(solid,new Girder(), "Girder");
        addObj(solid,new Window(), "Window");
        addObj(solid,new ReinforcedWindow(), "Reinf window");
        
        addObj(doors,new Door(), "Door");
        addObj(doors,new BlockedDoor(), "Blocked door");
        addObj(doors,new PrisonDoor(), "Prison door");
        addObj(doors,new BlockedPrisonDoor(), "Blk prison dr");
        addObj(doors,new PrisonMainternanceDoor(), "Prs mntrn door");
        addObj(doors,new BlockedPrisonMainternanceDoor(), "Blk prs mntrn dr");
        addObj(doors,new BaseDoor(), "Base door");
        addObj(doors,new PoliceDoor(), "Police jail door");
        addObj(doors,new PoliceRegDoor(), "Police door");
        addObj(doors,new SlidingDoor(), "Sliding door");
        
        addObj(scene,new BreakablePrisonDoor(), "Brk prison door");
        addObj(scene, new lvl0GuardSpawner(), "l0 pg spawner");
        addObj(scene, new lvl0KeyGuard(), "l0 keyguard");
        addObj(scene, new RatSpawner(), "RatSpawner");
        addObj(scene, new MapLoaderOnDeath(), "Death map");
        addObj(scene, new PJGuardSpawner(), "PJ guardspw");
        addObj(scene, new PJLampTrigger(), "PJ pl lamp");
        addObj(scene, new PJExplodingPipe(), "PJ exp pipe");
        
        addObj(items,new HealthBox(), "Health");
        addObj(items,new Battery(), "Ammo");
        
        addObj(decors,new Pipes(), "Pipes");
        addObj(decors,new PipesCorner(), "Pipes corner");
        addObj(decors,new PipesInvcorner(), "Pipes invcorner");
        addObj(decors,new PipePump(), "Pipes pump");
        addObj(decors,new Fan(), "Fan");
        addObj(decors,new BigFan(), "Big fan");
        addObj(decors,new ShipEngine(), "Engine");
        addObj(decors,new BigShipEngine(), "Big Engine");
        addObj(decors,new Crate(), "Crate");
        addObj(decors,new Border(), "Border");
        addObj(decors,new Table(), "Table");
        addObj(decors,new FlippedTable(), "Flip table");
        addObj(decors,new Chair(), "Chair");
        addObj(decors,new Bench(), "Bench");
        addObj(decors,new PoliceCar(), "Police car");
        addObj(decors,new PoliceCarOpen(), "Police car open");
        addObj(decors,new PoliceCarCrashed(), "Police car crashed");
        
        addObj(markers, new ElevatorButton(), "Elevator");
        addObj(markers, new MobSpawnerButton(), "Spawn Button");
        addObj(markers, new DamageTrigger(), "Damage");
        addObj(markers, new MobPatrolNode(), "Patrol node");
        addObj(markers, new PlayerStart(), "Player start");
        addObj(markers, new AiBlocker(), "AI blocker");
        addObj(markers, new MapLoaderMarker(), "Mapchanger");
        addObj(markers, new SmoothMapLoader(), "Mapchanger smooth");
        addObj(markers, new TeleporterTrigger(), "Teleport");
        addObj(markers, new TeleporterReceiver(), "Teleport out");
        addObj(markers, new GraphicsBackgroundChanger(), "Background");
        addObj(markers, new AllowedZone(), "Allowed zone");
        addObj(markers, new MobSpawnTrigger(), "Spawn trigger");
        addObj(markers, new MobEnableTrigger(), "M enbl trigger");
        addObj(markers, new AdvHitBack(), "Back hit advice");
        addObj(markers, new AdvFlashlight(), "Flashlight adv");
        
        addObj(lights,new Lamp(), "Lamp");
        addObj(lights,new LongLamp(), "Long lamp");
        addObj(lights,new RedLamp(), "Red lamp");
        addObj(lights,new Lantern(), "Lantern");
        addObj(lights,new InvsLight(), "Source");
        addObj(lights, new SunLight(), "SUN");
        
        category.addItem(doors, "Doors");
        category.addItem(solid, "Solid");
        category.addItem(scene, "Scenario");
        category.addItem(mobs, "Mobs");
        category.addItem(lights, "Lights");
        category.addItem(markers, "Markers");
        category.addItem(turfs, "Floors");
        category.addItem(walls, "Walls");
        category.addItem(decors, "Decors");
        category.addItem(items, "Items");
    }
    
    @Override
    public void onUpdate(){
        //nametxt.updateText(""+isMouseOnElement());
        
        if(zlvl.switched && Main.isNull(Main.window.master.playerObj)){
            Main.window.master.currentZ = zlvl.value;
        }else{
            /*if(!Main.isNull(Main.window.master.playerObj) && Defines.checkFlag(Main.window.master.playerObj.flags, Defines.MOB_NOFALL)){
                Main.window.master.playerObj.z = zlvl.value;
            }else{*/
                zlvl.value = Main.window.master.currentZ;
            //}
        }
        spawn_obj.z = zlvl.value;
        
        
        int mX = Main.window.master.mouseX-Main.window.master.objShiftX;
        int mY = Main.window.master.mouseY-Main.window.master.objShiftY;
        
        
        gridsize.decstep = -(gridsize.value*2-gridsize.value);
        gridsize.incstep = -(gridsize.value/2);
        
        grid.gridsize = gridsize.value;
        
        if(!Main.isNull(spawn_obj)){
            spawn_obj.isVisible = spawnbtn.pressed;
        }
        int cx = mX;
        int cy = mY;
        if(gridsize.pressed){
        cx = ((mX/gridsize.value)-(Integer.signum(mX)<0?1:0))*(gridsize.value);
        cy = ((mY/gridsize.value)-(Integer.signum(mY)<0?1:0))*(gridsize.value);
        }
        if(!Main.isNull(spawn_obj) && spawnbtn.pressed && !((ItemSelector)category.getItem()).items.isEmpty()){
            GameObject sobj = ((GameObject)((ItemSelector)category.getItem()).getItem());
            spawn_obj.basicIconName = sobj.basicIconName;
            spawn_obj.iconName = sobj.basicIconName;
            spawn_obj.updateIcon(true);
            spawn_obj.direction = dir.value;
            spawn_obj.move(cx, cy);
            spawn_obj.can_rotated = sobj.can_rotated;
            spawn_obj.cycleAnim = sobj.cycleAnim;
            spawn_obj.iconCenter = sobj.iconCenter;
            range.prefix = sobj.RangeSettingName+" [";
            strength.prefix = sobj.StrengthSettingName+" [";
            status.prefix = sobj.StatusSettingName+" [";
            if(!isMouseOnElement() && checkMouseClick(MouseEvent.BUTTON1)){
                try {
                    if(!(Main.window.isTypeOnPos(cx, cy, sobj) && (sobj instanceof Turf))){
                    GameObject obj = createObject(sobj.getClass().newInstance(), cx, cy ,zlvl.value, false);
                    obj.direction = dir.value;
                    if(sobj.hasSettings()){
                    obj.setColorSetting(setColor);
                    obj.setRangeSetting(setRange);
                    obj.setStrengthSetting(setStrength);
                    obj.setStatusSetting(setStat);
                    }
                    obj.preInit(cx, cy, zlvl.value);
                    }
                } catch (InstantiationException | IllegalAccessException ex) {
                    Main.error("Cannot create an object "+ex);
                }
            }
        }else{
            spawn_obj.isVisible = false;
        }
        
        if(spawnbtn.pressed){
            if(!isMouseOnElement() && checkMouseClick(MouseEvent.BUTTON3)){
                ArrayList<GameObject> dobjs = new ArrayList();
                for(Object cho : Main.window.map.toArray()){
                Chunk ch = (Chunk) cho;
                    for(Object gobj : ch.contents.toArray()){
                        GameObject obj = (GameObject) gobj;
                        Rectangle cr = obj.getCollideShape();
                        if(Main.in_range(mX, mY, cr.x, cr.y, cr.x+cr.width-1, cr.y+cr.height-1) && 
                                zlvl.value == obj.z && obj.isActive && obj.isVisible && obj.removable && 
                                obj instanceof DynamicObject && !(obj instanceof MapEditObject)){
                            dobjs.add(obj);
                        }
                    }
                }
                if(!dobjs.isEmpty()){
                dobjs.sort((GameObject o1, GameObject o2) -> {
                Float l1 = o1.layer;
                Float l2 = o2.layer;
                return l2.compareTo(l1);
                });
                dobjs.get(0).remove();
                }
            }
        }
        
        if(savemap.clicked && isMouseOnElement()){
            spawnbtn.pressed = false;
            JFileChooser sf = new JFileChooser();
            sf.setCurrentDirectory(new File(Main.window.mapDir));
            sf.setFileFilter(new FileNameExtensionFilter("MAP file","map"));
            int chk = sf.showSaveDialog(null);
            if(chk == JFileChooser.APPROVE_OPTION){
                File sfl = sf.getSelectedFile();
                if(!sfl.getAbsolutePath().endsWith(".map")){
                    sfl = new File(sfl.getAbsolutePath()+".map");
                }
                Main.window.saveMap(sfl);
            }
        }
                
        if(loadmap.clicked && isMouseOnElement()){
            spawnbtn.pressed = false;
            JFileChooser sf = new JFileChooser();
            sf.setCurrentDirectory(new File(Main.window.mapDir));
            sf.setFileFilter(new FileNameExtensionFilter("MAP file","map"));
            int chk = sf.showOpenDialog(null);
            if(chk == JFileChooser.APPROVE_OPTION){
                File file = sf.getSelectedFile();
                Main.window.game.loadMap(file);
            }
        }
        
        GameObject seobj = null;  
        if(!(((ItemSelector)category.getItem()).items.isEmpty())){
        seobj = ((GameObject)((ItemSelector)category.getItem()).getItem());
        }
        if(!Main.isNull(seobj)){
        if(seobj.hasSettings() && settbtn.pressed){
            if(category.switched || ((ItemSelector)category.getItem()).switched){
            clrr.setValue(seobj.DefColor.getRed());
            clrg.setValue(seobj.DefColor.getGreen());
            clrb.setValue(seobj.DefColor.getBlue());
            range.setValue(seobj.DefRange);
            strength.setValue(seobj.DefStrength);
            status.setValue(seobj.DefStatus?1:0);
            }
            clrr.setTxtColor(setColor);
            clrg.setTxtColor(setColor);
            clrb.setTxtColor(setColor);
            range.incstep = seobj.RangeStep;
            range.decstep = seobj.RangeStep;
            strength.incstep = seobj.StrengthStep;
            strength.decstep = seobj.StrengthStep;
        }
        
        if(clsbtn.clicked){
            remove();
        }
        
        gridsize.decstep = -(gridsize.value*2-gridsize.value);
        gridsize.incstep = -(gridsize.value/2);
        
        grid.gridsize = gridsize.value;
        
        updateShowing(show);
        settbtn.isVisible = spawnbtn.pressed && seobj.hasSettings();
        category.isVisible = spawnbtn.pressed;
        for(Object obj : category.items){
            ItemSelector item = (ItemSelector) obj;
            if(category.getItem().equals(obj) && !item.items.isEmpty()){
                item.isVisible = category.isVisible;
            }else{
                item.isVisible = false;
            }
        }
        grid.isVisible = spawnbtn.pressed && gridsize.pressed && gridsize.value>=4;
        clrr.isVisible = spawnbtn.pressed && seobj.SETTING_COLOR && settbtn.pressed;
        clrg.isVisible = spawnbtn.pressed && seobj.SETTING_COLOR && settbtn.pressed;
        clrb.isVisible = spawnbtn.pressed && seobj.SETTING_COLOR && settbtn.pressed;
        range.isVisible = spawnbtn.pressed && seobj.SETTING_RANGE && settbtn.pressed;
        strength.isVisible = spawnbtn.pressed && seobj.SETTING_STRENGTH && settbtn.pressed;
        status.isVisible = spawnbtn.pressed && seobj.SETTING_STATUS && settbtn.pressed;
        }
        setColor = new Color(clrr.value,clrg.value,clrb.value);
        setRange = range.value;
        setStrength = strength.value;
        setStat = (boolean) status.getItem();
        
        if(!removed){
        Defines.LIGHT_ENABLED = !spawnbtn.pressed;
        }
        
        if(Main.isNull(Main.window.master.playerObj)){
            if(checkKey(KeyEvent.VK_S)){
                Main.window.master.objShiftY += -15;
            }
            if(checkKey(KeyEvent.VK_W)){
                Main.window.master.objShiftY += 15;
            }
            if(checkKey(KeyEvent.VK_A)){
                Main.window.master.objShiftX += 15;
            }
            if(checkKey(KeyEvent.VK_D)){
                Main.window.master.objShiftX += -15;
            }
            Main.window.master.currentZ = zlvl.value;
        }
        //Main.window.master.drawInvs = true;
    }
    
    private void addObj(ItemSelector itm, GameObject obj, String name){
        obj.initVars();
        itm.addItem(obj, name);
    }
    
    @Override
    public void onRemove(){
        Defines.LIGHT_ENABLED = true;
        super.onRemove();
        spawn_obj.remove();
        Main.window.master.drawInvs = false;
    }    
}
