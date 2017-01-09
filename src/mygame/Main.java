package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import java.util.List;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    private final static Trigger TRIGGER_SELECT = new MouseButtonTrigger(MouseInput.BUTTON_LEFT);
    private final static String MAPPING_SELECT = "Select";
    private final static Trigger TRIGGER_REFFILL = new KeyTrigger(KeyInput.KEY_SPACE);
    private final static String MAPPING_REFILL = "Refill";
    private int selected = -1;
    
    
    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Defend the Tower!");
        settings.setSettingsDialogImage("Interface/TowerIcon.jpg");
        Main app = new Main();
        app.setSettings(settings);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        //setDisplayFps(false);
        //setDisplayStatView(false);
        cam.setLocation(new Vector3f(-0.79747856f, 16.797455f, 16.666473f));
        cam.setRotation(new Quaternion(-2.4167309E-4f, 0.96891284f, -0.24740031f, -9.4615715E-4f));
        //flyCam.setMoveSpeed(50);
        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);
        GamePlayAppState state = new GamePlayAppState();
        stateManager.attach(state);
        
        inputManager.addMapping(MAPPING_SELECT, TRIGGER_SELECT);
        inputManager.addMapping(MAPPING_REFILL, TRIGGER_REFFILL);
        inputManager.addListener(actionListener, 
                new String[]{MAPPING_REFILL, MAPPING_SELECT});
        
        
        
    }//simpleInitApp

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void SetSettings(AppSettings settings) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }//SetSettings
    
    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean isPressed, float tpf) {
            if(name.equals("Select") && isPressed){
                System.out.println("Select pressed");
                CollisionResults results = new CollisionResults();
                Vector2f clicked2d = inputManager.getCursorPosition();
                Vector3f clicked3d = cam.getWorldCoordinates(
                        new Vector2f(clicked2d.getX(), clicked2d.getY()), 0);
                Vector3f dir = cam.getWorldCoordinates(
                        new Vector2f(clicked2d.getX(), clicked2d.getY()), 1f)
                        .subtractLocal(clicked3d);
                Ray ray = new Ray(clicked3d, dir);
                rootNode.collideWith(ray, results);                
                if(results.size() > 0){
                     System.out.println("Path: 0");
                    Geometry target = results.getClosestCollision().getGeometry();
                    if(target.getName().equals("tower box")){
                        System.out.println("Path: 1");
                        selected = target.getUserData("index");
                    }//if
                    else{
                        System.out.println("Name: " + target.getName());
                        System.out.println("Path: 2");
                        selected = -1;
                    }//else
                }//if
                else{
                    System.out.println("Path: 3");
                    selected = -1;
                }//else
            }//if
            else if(name.equals("Refill") && isPressed){
                Spatial toRefill;
                 System.out.println("Refill pressed");
                if(selected > -1){
                    System.out.println("Path: 4");
                    List<Spatial> towers = stateManager.getState(GamePlayAppState.class).towerNode.getChildren();
                    for(int i = 0; i < towers.size(); i++){
                        if(towers.get(i).getControl(TowerControl.class).getIndex() == selected){
                            toRefill = towers.get(i);
                            if(stateManager.getState(GamePlayAppState.class).getBudget()
                            - toRefill.getControl(TowerControl.class)
                            .getRefillPrice() > 0 ){
                            System.out.println("Path: 5");
                            toRefill.getControl(TowerControl.class).addCharge();
                            System.out.println("Refilled. Budget: " 
                                + stateManager.getState(GamePlayAppState.class).getBudget());
                    }//if
                        }//if
                    }//for                    
                }//if
            }//else if            
        }//OnAction
    };//ActionListener Object
}//class
