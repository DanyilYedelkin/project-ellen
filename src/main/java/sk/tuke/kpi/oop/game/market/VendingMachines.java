package sk.tuke.kpi.oop.game.market;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Repairable;
import sk.tuke.kpi.oop.game.items.*;

import java.util.Objects;
import java.util.Random;


public class VendingMachines extends AbstractActor implements Repairable {
    private boolean buyItems;
    public static final Topic<VendingMachines> ITEM_PURCHASED = Topic.create("item purchased", VendingMachines.class);

    public VendingMachines(){
        buyItems = true;
        setAnimation(new Animation("sprites/vending_machines.png", 65, 65));
    }

    private boolean isBuy(){
        return buyItems;
    }

    @Override
    public boolean repair() {
        if(isBuy()){
            //buyItems = false;
            Objects.requireNonNull(getScene()).getMessageBus().publish(ITEM_PURCHASED,this);

            Random random = new Random();
            int randomNumber = random.nextInt(5);
            if(randomNumber == 0){
                getScene().addActor(new CocaCola(), this.getPosX(), this.getPosY() - 10);
            } else if(randomNumber == 1){
                getScene().addActor(new Sprite(), this.getPosX(), this.getPosY() - 10);
            } else if(randomNumber == 2){
                getScene().addActor(new Energy(), this.getPosX(), this.getPosY() - 10);
            } else if(randomNumber == 3){
                getScene().addActor(new Ammo(), this.getPosX(), this.getPosY() - 10);
            } else{
                getScene().addActor(new BulletproofVest(), this.getPosX(), this.getPosY() - 10);
            }

            return true;
        } else return false;
    }
}
