package sk.tuke.kpi.oop.game.characters;

//add libraries
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

//create public class Ripley
public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed {
    //create private variables
    private Disposable disposable;      //for disposable
    private int speed;                  //for ripley's speed of moving
    private Animation ripleyAnimation;  //ripley's animation (default)
    private Animation diedRipleyAnimation; //ripley's dead animation (if ripley is dead)
    private int ammo;                   //for ammo
    private Backpack backpack;          //create a backpack for ripley
    private Health health;              //for ripley's health
    private Armor armor;                //for ripley's armor
    private Firearm weapon;             //for ripley's weapon
    private TunnelFinish tunnelFinish;  //the finish point in the map
    //topics of current ripley states
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);
    public static final Topic<Ripley> RIPLEY_WIN = Topic.create("ripley win", Ripley.class);


    //default Ripley()
    public Ripley(){
        //set default variables
        super("Ellen");

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

    //a method returns ripley's speed of moving
    @Override
    public int getSpeed() {
        return speed;
    }

    //a method, which can help ripley to start move
    @Override
    public void startedMoving(Direction direction){
        ripleyAnimation.setRotation(direction.getAngle());
        ripleyAnimation.play();
    }
    //a method for stop moving of the ripley
    @Override
    public void stoppedMoving(){
        ripleyAnimation.stop();
    }


    //a method returns ripley's ammo
    public int getAmmo() { return ammo; }

    //a method for setting ripley's ammo
    public void setAmmo(int ammo) { this.ammo = ammo; }

    //a method returns ripley's backpack
    @Override
    public Backpack getBackpack() {
        return backpack;
    }

    //a method shows current ripley's state
    public void showRipleyState(){
        int windowHeight = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        int windowWidth = getScene().getGame().getWindowSetup().getWidth();
        int xTextPos = windowWidth - GameApplication.STATUS_LINE_OFFSET - 680;

        //text for current ripley's energy
        getScene().getGame().getOverlay().drawText(" | Energy: " + health.getValue(), xTextPos, yTextPos);
        //text for current ripley's ammo
        getScene().getGame().getOverlay().drawText("| Ammo: " + getFirearm().getAmmo(), 255, yTextPos);
        //text for current ripley's armor
        getScene().getGame().getOverlay().drawText("| Armor: " + armor.getValue(), 375, yTextPos);
    }

    //a method for decreasing energy of ripley
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
                        }
                    }),
                    new Wait<>(1))
            ).scheduleFor(this);
        } else{
            checkEnergy();
        }
    }

    //a method, which checks current situation with ripley's energy points (health points)
    private void checkEnergy(){
        if(health.getValue() <= 0){
            this.setAnimation(diedRipleyAnimation);
            Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED, this);
        }
    }

    //a method for stopping decreasing energy
    public Disposable stopDecreaseEnergy(){
        return disposable;
    }

    //a method returns current ripley's health
    @Override
    public Health getHealth() {
        return health;
    }

    //a method returns current ripley's armor
    public Armor getArmor(){
        return armor;
    }

    //a method returns current ripley's weapon
    @Override
    public Firearm getFirearm(){
        return weapon;
    }

    //a method for setting new ripley's weapon
    @Override
    public void setFirearm(Firearm weapon){
        this.weapon = weapon;
    }

    //a method for stealing ripley's ammo by Lurker
    public void stealAmmo(int stealingAmount){
        this.getFirearm().stealAmmo(stealingAmount);
    }

    //a method for setting ripley's speed
    public void setSpeed(int speed){
        this.speed = speed;
    }
}
