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
            int newX = this.getPosX() + 8;
            int newY = this.getPosY() + 8;

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
        //for teleporting the player (from A teleport to B teleport)
        if(this.destination != null && checkPlayerPosition()){
            this.destination.teleportPlayer(this.player);
        }
    }

    private boolean checkPlayerPosition(){
        //for calculating positions of player and teleports
        int midWidthTeleport = 24;  // that's like  Math.abs(this.getWidth() / 2);
        int midHeightTeleport = 24; // that's like  Math.abs(this.getHeight() / 2);
        int midWidthPlayer = 16;    // that's like  Math.abs(player.getWidth() / 2);
        int midHeightPlayer = 16;   // that's like  Math.abs(player.getHeight() / 2)

        int xMidPlayer = player.getPosX() + midWidthPlayer;
        int yMidPlayer = player.getPosY() + midHeightPlayer;
        int xMidTeleport = getPosX() + midWidthTeleport;
        int yMidTeleport = getPosY() + midHeightTeleport;

        if(!teleportIsAvaible){ //check if I can use the teleport again
            return false;
        } else if(!this.player.intersects(this)){ //check if player doesn't intersect with teleport
            return false;
        } else if(Math.abs(xMidTeleport - xMidPlayer) > midWidthTeleport){ //check if player is on a teleport (X coordinate)
            return false;
        } else return Math.abs(yMidTeleport - yMidPlayer) <= midHeightTeleport; // check if player is on a teleport (Y coordinate)
    }


    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        this.player = getScene().getLastActorByType(Player.class);

        if(destination != null){
            assert player != null;
            new Loop<>(new Invoke<>(this::teleportation)).scheduleFor(player);
        }

        //new Loop<>(new Invoke<>(this::teleportation)).scheduleFor(player);
    }
}


