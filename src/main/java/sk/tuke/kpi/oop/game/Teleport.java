package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Teleport extends AbstractActor {
    private Teleport destination;       //a destination of a teleport
    private boolean teleportIsAvaible;  //check if a teleportation is avaible
    private Ripley player;


    public Teleport(Teleport teleport){
        this.destination = teleport;
        this.teleportIsAvaible = true;  //default

        setAnimation(new Animation ("sprites/lift.png", 48, 48));
    }

    //a method with which we can change a destination of the teleport
    public void setDestination(Teleport destinationTeleport){
        if(this != destinationTeleport) {
            this.destination = destinationTeleport;
        }
    }

    //a method, which returns the destination of the teleport
    public Teleport getDestination(){
        return destination;
    }

    
    //a method, which teleport the player
    public void teleportPlayer(Ripley player) { //Player player
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

    //a method, which teleports the player (from A to B)
    private void teleportation(){
        //for teleporting the player (from A teleport to B teleport)
        if(this.destination != null && checkPlayerPosition()){
            this.destination.teleportPlayer(player);
        }
    }

    //a method, which checks a player position
    private boolean checkPlayerPosition(){
        //for calculating positions of player and teleports
        int midWidthTeleport = 24;  // the middle of the width teleport
        int midHeightTeleport = 24; // the middle of the height teleport
        int midWidthPlayer = 16;    // the middle of the width player
        int midHeightPlayer = 16;   // the middle of the height player

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
        this.player = scene.getFirstActorByType(Ripley.class);

        if(destination != null){
            assert player != null;
            new Loop<>(new Invoke<>(this::teleportation)).scheduleFor(player);
        }
    }
}


