package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor {
    private Animation lightOffAnimation;
    private Animation lightOnAnimation;
    private boolean isRunning;
    private boolean isPowered;

    public Light() {
        lightOnAnimation = new Animation("sprites/light_on.png", 16, 16, 0.1f);
        lightOffAnimation = new Animation("sprites/light_off.png", 16, 16, 0.1f);

        setAnimation(lightOffAnimation);
    }

    public void toggle(){
        if(isRunning == false){
            isRunning = true;
            updateAnimation();
        }else{
            isRunning = false;
            updateAnimation();
        }
    }
    private void updateAnimation(){
        if(isRunning == false && isPowered == false) setAnimation(lightOffAnimation);
        else setAnimation(lightOnAnimation);
    }
    public void setElectricityFlow(boolean newPower){
        isPowered = newPower;
        updateAnimation();
    }
    public boolean isWorking(){
        return isRunning;
    }

}
