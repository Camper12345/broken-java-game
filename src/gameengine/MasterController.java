/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;

import com.sun.glass.events.KeyEvent;
import gameengine.objects.gui.ConsoleGui;
import gameengine.objects.gui.GameMenuGUI;
import gameengine.objects.mobs.Player;
import java.awt.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import javafx.scene.input.KeyCode;

/**
 *
 * @author Camper2012
 */
public class MasterController extends Thread {
    Graphics screen;
    public HashMap<Integer, Boolean> keys = new HashMap();
    public HashMap<Integer, Boolean> lastkeys = new HashMap();
    public HashMap<Integer, Boolean> mouse = new HashMap();
    public HashMap<Integer, Boolean> lastmouse = new HashMap();
    private Image bufferedImage;
    private Graphics buffer;
    public Image overImage;
    public Graphics over;
    private boolean active = true;
    public boolean tick = false;
    public int time = 0;
    public int mouseX = 0;
    public int lastmouseX = 0;
    public int lastmouseY = 0;
    public int mouseY = 0;
    public int lastMWheelRotation = 0;
    public int mWheelRotation = 0;
    public int objShiftX = 0;
    public int objShiftY = 0;
    public int currentZ = 0;
    public int sX = 0;
    public int sY = 0;
    public Player playerObj;
    public ConsoleGui console = null;
    public boolean pause = false;
    public boolean loading = false;
    public boolean drawInvs = false;
    public int loadingstatus = 0;
    public Color loadingcolor = Defines.LOADING_GREEN;
    public String loadingname = "";
    public String loadingtext = "";
    
    final boolean deb = false;
    public MasterController(Graphics gr) {
        init(gr);
    }
    public void init(Graphics gr){
        screen = gr;      
    }
    
    @Override
    public void run() { 
        Main.info("MasterController started!");
        process();
    }
    public void process() {
        while(active) {
            if(tick)
            {
                if(deb){
                System.out.println("");
                System.out.println("=== TICK #"+time+" STARTED ===");
            }
            long ftime = Calendar.getInstance().getTimeInMillis();
            long fttime = Calendar.getInstance().getTimeInMillis();
            sX = objShiftX;
            sY = objShiftY;
            lastmouseX = mouseX;
            mouseX = gameengine.Frame.mouseX;
            lastmouseY = mouseY;
            mouseY = gameengine.Frame.mouseY;
            lastMWheelRotation = mWheelRotation;
            mWheelRotation = gameengine.Frame.mouseWheelRotation;
            lastkeys = (HashMap<Integer, Boolean>) keys.clone();
            keys = (HashMap<Integer, Boolean>) Main.window.keys.clone();
            lastmouse = (HashMap<Integer, Boolean>) mouse.clone();
            mouse = (HashMap<Integer, Boolean>) Main.window.mouse.clone();
            if(!Main.isNull(playerObj)){
                currentZ = playerObj.z;
            }
            bufferedImage = new BufferedImage(Defines.WIDTH,Defines.HEIGHT,BufferedImage.TYPE_INT_ARGB);
            buffer = bufferedImage.getGraphics();
            overImage = new BufferedImage(Defines.WIDTH,Defines.HEIGHT,BufferedImage.TYPE_INT_ARGB);
            over = overImage.getGraphics();
            ArrayList<GameObject> objlist = new ArrayList();
            ArrayList<GameObject> drawobjs = new ArrayList();
            if(!gameengine.Frame.map.isEmpty()){
                for(Object cho : Main.window.map.toArray()){
                    Chunk ch = (Chunk) cho;
                    for(Object gobj : ch.contents.toArray()){
                        GameObject obj = (GameObject) gobj;
                        if(obj.isActive && obj.in_process_zone()){
                        objlist.add(obj);
                        }
                    }
                }
            }
            objlist.sort((GameObject o1, GameObject o2) -> {
                Integer i1 = (o1 instanceof StaticObject)?1:0;
                Integer i2 = (o2 instanceof StaticObject)?1:0;
                return i2.compareTo(i1);
            });
            if(deb)System.out.println("preparaions processed in "+(Calendar.getInstance().getTimeInMillis()-ftime));
            if(!loading){
            int cnt = 0;
            long stime = Calendar.getInstance().getTimeInMillis();
            for(Object gobj : objlist.toArray()){
                GameObject obj = (GameObject)gobj;
                if(obj.isActive){
                ftime = Calendar.getInstance().getTimeInMillis();
                if(obj instanceof StaticObject || !pause){
                    obj.process();
                }
                if(deb){
                long nftime = Calendar.getInstance().getTimeInMillis()-ftime;
                if(nftime>1){
                    System.out.println(obj+" processed in "+nftime);
                }
                cnt++;
                }
                }
                if(obj.isVisible && ((obj.in_disp_zone()) || obj.staticPos)){
                //obj.updateIcon();
                drawobjs.add(obj);}
            }
            if(deb){System.out.println("= "+(Calendar.getInstance().getTimeInMillis()-stime)+" ms processed "+cnt+" objs =");
            ftime = Calendar.getInstance().getTimeInMillis();}
            buffer.setColor(Defines.GRAPHICS_BACKGROUND_COLOR);
            buffer.clearRect(0, 0, Defines.WIDTH, Defines.HEIGHT);
            buffer.fillRect(0, 0, Defines.WIDTH, Defines.HEIGHT);
            drawobjs.sort((GameObject o1, GameObject o2) -> {
                Float l1 = (o1.z*Defines.LAYER_MAX)+o1.layer;
                Float l2 = (o2.z*Defines.LAYER_MAX)+o2.layer;
                return l1.compareTo(l2);
            }); 
            for(Object gobj : drawobjs.toArray()){
                GameObject obj = (GameObject)gobj;
                
                buffer.drawImage(obj.icon, obj.scrX+obj.offX, obj.scrY+obj.offY, null);    
                }
            }
            if(deb){System.out.println("Object draw finished in "+(Calendar.getInstance().getTimeInMillis()-ftime));
            ftime = Calendar.getInstance().getTimeInMillis();}
            BufferedImage simg = new BufferedImage(Defines.SCREEN_WIDTH_F, Defines.SCREEN_HEIGHT_F,BufferedImage.TYPE_INT_ARGB);
            Graphics gr = simg.getGraphics();
            gr.setColor(Color.black);
            gr.fillRect(0, 0, Defines.SCREEN_WIDTH_F, Defines.SCREEN_HEIGHT_F);
            if(Main.window.game.levelRestart){
                over.setColor(new Color(0,0,0,Main.window.game.restartcount));
                over.fillRect(0, 0, Defines.WIDTH, Defines.WIDTH);
            }
                gr.drawImage(bufferedImage.getScaledInstance(Defines.SCREEN_WIDTH, Defines.SCREEN_HEIGHT, 
                    BufferedImage.SCALE_DEFAULT), Defines.SCR_X, Defines.SCR_Y, null);
                gr.drawImage(overImage.getScaledInstance(Defines.SCREEN_WIDTH, Defines.SCREEN_HEIGHT, 
                    BufferedImage.SCALE_DEFAULT), Defines.SCR_X, Defines.SCR_Y, null);
                
            
            if(loading){
                gr.fillRect(0, 0, Defines.SCREEN_WIDTH_F, Defines.SCREEN_HEIGHT_F);
                BufferedImage lst = new BufferedImage(Defines.WIDTH, Defines.HEIGHT, BufferedImage.TYPE_INT_ARGB);
                Graphics lgr = lst.getGraphics();
                lgr.setColor(new Color(32,32,32));
                lgr.fillRect((Defines.WIDTH-500)/2, Defines.HEIGHT-110, 500, 10);
                lgr.setColor(loadingcolor);
                lgr.fillRect((Defines.WIDTH-500)/2, Defines.HEIGHT-110, loadingstatus*5, 10);
                lgr.setColor(new Color(255,255,128));
                lgr.setFont(new Font("Arial Black",Font.BOLD,18));
                lgr.drawString(loadingtext, (Defines.WIDTH-500)/2, Defines.HEIGHT-130);
                lgr.drawString(loadingname, (Defines.WIDTH-500)/2, Defines.HEIGHT-148);
                gr.drawImage(lst.getScaledInstance(Defines.SCREEN_WIDTH, Defines.SCREEN_HEIGHT, 
                    BufferedImage.SCALE_DEFAULT), Defines.SCR_X, Defines.SCR_Y, null);
            }
            screen.drawImage(simg, 0, 0, null);
            
            if(!Main.isNull(keys) && Main.isNull(console)){
            if(checkKey(KeyEvent.VK_CONTROL) && checkKey(KeyEvent.VK_ALT) && checkKey(KeyEvent.VK_F12)){
                Main.window.createObject(new ConsoleGui(), 0, 0, currentZ);
            }
            }
            if(!Main.isNull(keys) && !pause){
                if(checkKey(KeyEvent.VK_ESCAPE) && Main.window.game.isInGame){
                    Main.window.createObject(new GameMenuGUI(), 0, 0, currentZ);
                }
            }
            if(deb){System.out.println("screen draw finished in "+(Calendar.getInstance().getTimeInMillis()-ftime));
            ftime = Calendar.getInstance().getTimeInMillis();
            System.out.println("=== TICK PROCEED IN "+(Calendar.getInstance().getTimeInMillis()-fttime)+" ===");
            }
            time+=1;
            
            tick = false;
            /*System.out.println("TICK FINISHED IN "+(Calendar.getInstance().getTimeInMillis()-ftime)+"ms");
            System.out.println("======================================");*/
            tick_stat();
        }
            delay(1);
        }
    }
    
    public void tick(){
        tick = true;
    }
    
    public void delay(long msec) {
        try {
            sleep(msec);
        } catch (InterruptedException ex) {
            Main.error("MasterController was interrupted "+ex);
        }
    }

    @Override
    public void interrupt(){
        active = false;
        Main.error("MasterController was interrupted!");
    }
    
    //TICK STATISTICS
    public float avg_ticklag = 0;
    private int tick_time = (int)Calendar.getInstance().getTime().getTime();
    private int tick_avg = 20;
    private int tick_cnt = 0;
    private final int tick_stat_time = 500;
    public void tick_stat(){
        tick_cnt++;
        if(tick_cnt>tick_stat_time){
            avg_ticklag = (tick_avg/tick_cnt);
            tick_cnt = 0;
            tick_avg = 0;
            Main.info("AVERAGE TICKLAG IS "+1000/avg_ticklag+" ticks per sec");
        }
        tick_avg += Calendar.getInstance().getTime().getTime()-tick_time;
        tick_time = (int)Calendar.getInstance().getTime().getTime();
    }
    
    public boolean checkKey(int kc){
        return keys.containsKey(kc)?keys.get(kc):false;    
    }
}

