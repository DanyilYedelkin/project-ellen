package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;


public class Helicopter extends AbstractActor {
    private boolean isOn;
    //private Animation helicopterAnimation;
    //private Scene scene;
    private Player player;

    public Helicopter(Player player){
        this.player = player;
        isOn = false;
        //helicopterAnimation = new Animation("sprites/heli.png", 64, 64, 0.2f);
        //setAnimation(helicopterAnimation);
        setAnimation(new Animation("sprites/heli.png", 64, 64, 0.2f));
    }

    public void working(){
        isOn = true;
    }
    public void searchAndDestroy(){
        if(isOn){
            int x = this.player.getPosition().getX();
            int y = this.player.getPosition().getY();
            if(x > this.getPosX()){
                setPosition(this.getPosX() + 1, this.getPosY());
            } else{
                setPosition(this.getPosX() - 1, this.getPosY());
            }
            if(y > this.getPosY()){
                setPosition(this.getPosX(), this.getPosY() + 1);
            } else{
                setPosition(this.getPosX(), this.getPosY() - 1);
            }
            if(intersects(this.player)){
                this.player.setEnergy(this.player.getEnergy() - 1);
            }

        }
    }

    public void addedToScene(Scene scene) {
        super.addedToScene(scene);

        new Loop<>(new Invoke<>(this::searchAndDestroy)).scheduleFor(this);
    }
}
