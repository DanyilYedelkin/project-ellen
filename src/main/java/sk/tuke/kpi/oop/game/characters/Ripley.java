package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.TunnelFinish;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

import java.util.Objects;


public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed {
    private Disposable disposable;
    private int speed;
    private Animation ripleyAnimation;
    private Animation diedRipleyAnimation;
    //private int energy;
    private int ammo;
    private Backpack backpack;
    private Health health;
    private Armor armor;
    private Firearm weapon;
    private TunnelFinish tunnelFinish;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);
    public static final Topic<Ripley> RIPLEY_WIN = Topic.create("ripley win", Ripley.class);


    public Ripley(){
        super("Ellen");
        //energy = 60;
        health = new Health(100, 100);
        armor = new Armor(20, 100);
        speed = 2;
        ammo = 20;
        weapon = new Gun(ammo,400);
        backpack = new Backpack("Ripley's backpack", 10);

        ripleyAnimation = new Animation("sprites/player.png",
            32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);

        diedRipleyAnimation = new Animation("sprites/player_die.png",
            32,32,0.1f, Animation.PlayMode.ONCE);

        setAnimation(ripleyAnimation);
        stoppedMoving();

        health.onExhaustion(() -> {
            if(this.getHealth().getValue() == 0){
                setAnimation(diedRipleyAnimation);
                Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED,this);
            }
        });

        tunnelFinish = new TunnelFinish();
        tunnelFinish.isOn(() -> {
            if(this.intersects(tunnelFinish)){
                Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_WIN,this);
            }
        });
    }

    @Override
    public int getSpeed() {
        return speed;
    }
    @Override
    public void startedMoving(Direction direction){
        ripleyAnimation.setRotation(direction.getAngle());
        ripleyAnimation.play();
    }
    @Override
    public void stoppedMoving(){
        ripleyAnimation.stop();
    }

    /*public int getEnergy(){
        return energy;
    }*/

    /*public void setEnergy(int energy){
        if(energy >=0 && energy <= 100){
            this.energy = energy;
        }
    }*/

    public int getAmmo() { return ammo; }

    public void setAmmo(int ammo) { this.ammo = ammo; }


    @Override
    public Backpack getBackpack() {
        return backpack;
    }

    public void showRipleyState(){
        int windowHeight = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        int windowWidth = getScene().getGame().getWindowSetup().getWidth();
        int xTextPos = windowWidth - GameApplication.STATUS_LINE_OFFSET - 680;

        getScene().getGame().getOverlay().drawText(" | Energy: " + health.getValue(), xTextPos, yTextPos);
        getScene().getGame().getOverlay().drawText("| Ammo: " + getFirearm().getAmmo(), 255, yTextPos);
        getScene().getGame().getOverlay().drawText("| Armor: " + armor.getValue(), 375, yTextPos);
    }

    public void decreaseEnergy(){
        if(health.getValue() > 0){
            disposable = new Loop<>(
                new ActionSequence<>(
                    new Invoke<>(() -> {
                        if(health.getValue() > 0){
                            //this.decrease();
                            if(armor.getValue() <= 0){
                                getHealth().drain(2);
                            } else{
                                getArmor().drain(2);
                            }
                        } else{
                            //for one full animation
                            checkEnergy();

                            //for repeated animation
                            //this.setAnimation(new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE));
                            //Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED, this);
                        }
                    }),
                    new Wait<>(1))
            ).scheduleFor(this);
        } else{
            checkEnergy();
        }
    }
    /*private void decrease(){
        if(health.getValue() >= 0){
            energy--;
        } else{
            checkEnergy();
        }
    }*/
    private void checkEnergy(){
        if(health.getValue() <= 0){
            this.setAnimation(diedRipleyAnimation);
            Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED, this);
        }
    }

    public Disposable stopDecreaseEnergy(){
        return disposable;
    }

    @Override
    public Health getHealth() {
        return health;
    }

    public Armor getArmor(){
        return armor;
    }

    @Override
    public Firearm getFirearm(){
        return weapon;
    }

    @Override
    public void setFirearm(Firearm weapon){
        this.weapon = weapon;
    }

    public void stealAmmo(int stealingAmount){
        this.getFirearm().stealAmmo(stealingAmount);
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }
}
