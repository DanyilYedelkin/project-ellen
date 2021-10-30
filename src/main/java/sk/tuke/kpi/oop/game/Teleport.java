package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

//import org.jetbrains.annotations.NotNull;


public class Teleport extends AbstractActor {
    private Teleport destination;
    private boolean teleportIsAvaible;;
    private Player player;

    //private Animation teleportAnimation;

    public Teleport(Teleport teleport){
        this.destination = teleport;
        this.teleportIsAvaible = true;

        //player = getScene().getLastActorByType(Player.class);

        //teleportAnimation = new Animation("sprites/lift.png", 48, 48);
        //setAnimation(teleportAnimation);

        setAnimation(new Animation ("sprites/lift.png", 48, 48));
    }


    public void setDestination(Teleport destinationTeleport){
        if(this != destinationTeleport) {
            this.destination = destinationTeleport;
        }
    }

    public Teleport getDestination(){
        return destination;
    }

    /*public void teleportPlayer(Player player){
        //player = getScene().getLastActorByType(Player.class);
        this.player = player;
        //this.player = getScene().getLastActorByType(Player.class);

        //new Loop<>(new Invoke<>(this::teleporting)).scheduleFor(this.player);
        teleportPlayerDist();
    }

    private void teleportPlayerDist(){
        //if(this.player != null) {
            //Player player = getScene().getLastActorByType(Player.class);
            int x = destination.getPosX() + 8;
            int y = destination.getPosY() + 8;

            if (player.intersects(this)) {
                player.setPosition(x, y);
                if (player.intersects(destination)) {
                    destination.teleportIsAvaible = false;
                }
            }
        //}
    }*/
    /*public void teleportPlayer(Player player){
        this.player = player;
        int x = destination.getPosX() + 8;
        int y = destination.getPosY() + 8;

        if (player.intersects(this)) {
            player.setPosition(x, y);
            if (player.intersects(destination)) {
                destination.teleportIsAvaible = false;
            }
        }
    }*/

    public void teleportPlayer(Player player) {
        if(this.player != null){
            //for new player's position (into the middle of the teleport)
            int newX = this.getPosX() + Math.abs(this.getWidth() / 2) - Math.abs(this.player.getWidth() / 2);
            int newY = this.getPosY() + Math.abs(this.getHeight() / 2) - Math.abs(this.player.getHeight() / 2);

            this.player.setPosition(newX, newY);

            //to avoid repeated teleportation
            this.teleportIsAvaible = false;
            new When<>(() -> {
                    return (!this.checkPlayerPosition() && !player.intersects(this)) ;
                },
                new Invoke<>(() -> { this.teleportIsAvaible = true; })
            ).scheduleFor(this);
        }
    }
    /*
    public void teleportPlayer(Player player){
        if(player != null){
            this.player = player;
            int x = destination.getPosX()+8;
            int y = destination.getPosY()+8;

            if(!this.player.intersects(destination)){
                this.player.setPosition(x, y);
                checkPosition();
                destination.teleportIsAvaible = false;
            }
        }
    }
    private void checkPosition(){
        int xPlayer = player.getPosX();
        int yPlayer = player.getPosY();

        int xDestination = destination.getPosX() + 8;
        int yDestination = destination.getPosY() + 8;

        if(xPlayer != xDestination && yPlayer != yDestination){
            player.setPosition(xDestination, yDestination);
        } else if(xPlayer != xDestination){
            player.setPosition(xDestination, yPlayer);
        } else if(yPlayer != yDestination){
            player.setPosition(xPlayer, yDestination);
        }
    }

    private void teleportation(){
        //this.player = getScene().getLastActorByType(Player.class);

        if(!(this.player.intersects(destination)) && !this.player.intersects(this)){
             teleportIsAvaible = true;
             destination.teleportIsAvaible = true;
        }
        //for teleporting the player
        if(teleportIsAvaible && destination.teleportIsAvaible){
             teleportPlayer(player);
        }
    }

    * */

    private void teleportation(){
        if(this.destination != null && checkPlayerPosition()){
            this.destination.teleportPlayer(this.player);
        }
    }

    private boolean checkPlayerPosition(){
        //for calculating positions of player and teleports
        int xMidPlayer = player.getPosX() + Math.abs(player.getWidth() / 2);
        int yMidPlayer = player.getPosY() + Math.abs(player.getHeight() / 2);
        int xMidTeleport = this.getPosX() + Math.abs(this.getWidth() / 2);
        int yMidTeleport = this.getPosY() + Math.abs(this.getHeight() / 2);

        if(!teleportIsAvaible){
            return false;
        } else if(!this.player.intersects(this)){
            return false;
        } else if(Math.abs(xMidTeleport - xMidPlayer) > (Math.abs(this.getWidth() / 2))){
            return false;
        } else return Math.abs(yMidTeleport - yMidPlayer) <= (Math.abs(this.getHeight() / 2));
    }


    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        this.player = getScene().getLastActorByType(Player.class);

        if(destination != null){
            new Loop<>(new Invoke<>(this::teleportation)).scheduleFor(player);
        }

        //new Loop<>(new Invoke<>(this::teleportation)).scheduleFor(player);
    }
}


