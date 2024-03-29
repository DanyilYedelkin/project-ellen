package sk.tuke.kpi.oop.game;

//add libraries
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;


public class Helicopter extends AbstractActor {

    public Helicopter(){
        setAnimation(new Animation("sprites/heli.png", 64, 64, 0.2f));
    }

    //a method with which a helicopter can find the player and punch him
    public void searching(){
            Player player = getScene().getLastActorByType(Player.class);
            int x = player.getPosition().getX();
            int y = player.getPosition().getY();

            //for X coordinate
            if(x > this.getPosX()){
                setPosition(this.getPosX() + 1, this.getPosY());
            } else{
                setPosition(this.getPosX() - 1, this.getPosY());
            }

            //for Y coordinate
            if(y > this.getPosY()){
                setPosition(this.getPosX(), this.getPosY() + 1);
            } else{
                setPosition(this.getPosX(), this.getPosY() - 1);
            }

            //for a punch the player and decrease player's energy
            if(intersects(player)){
                player.setEnergy(player.getEnergy() - 1);
            }
    }

    //a method for search and destroy the player
    public void searchAndDestroy(){
        new Loop<>(new Invoke<>(this::searching)).scheduleFor(this);
    }
}
