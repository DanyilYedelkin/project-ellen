package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Teleport extends AbstractActor {
    private Teleport destination;
    private boolean teleportIsAvaible;
    private Player player;

    //private Animation teleportAnimation;

    public Teleport(Teleport teleport){
        teleportIsAvaible = false;
        destination = teleport;
        //player = getScene().getLastActorByType(Player.class);

        //teleportAnimation = new Animation("sprites/lift.png", 48, 48);
        //setAnimation(teleportAnimation);
        setAnimation(new Animation("sprites/lift.png", 48, 48));
    }

    public void setDestination(Teleport destinationTeleport) {
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
    public void teleportPlayer(Player player){
        if(player != null){
            this.player = player;
            int x = destination.getPosX()+8;
            int y = destination.getPosY()+8;


            this.player.setPosition(x, y);
            if (this.player.intersects(destination)) {
                destination.teleportIsAvaible = false;
            }
        }
    }

    private void teleporting(){
        this.player = getScene().getLastActorByType(Player.class);
        /*int x = player.getPosX() + 8;
        int y = player.getPosY() + 8;
        int teleportX = this.getPosX();
        int teleportY = this.getPosY();

        if(destination != null){
            if(!this.player.intersects(this)){
                teleportIsAvaible = true;
            }
            if(!(this.player.intersects(destination))){
                destination.teleportIsAvaible = true;
            }
            //for teleporting the player
            if(teleportIsAvaible && (x > teleportX && x < teleportX + 48) && (y > teleportY && y < teleportY + 48)){
                teleportPlayer(player);
            }
        }*/
        if(!this.player.intersects(this)){
            teleportIsAvaible = true;
        }
        if(destination != null && !(this.player.intersects(destination))){
            destination.teleportIsAvaible = true;
        }

        //for teleporting the player
        if(destination != null && teleportIsAvaible && player.intersects(this)){
            teleportPlayer(player);
        }
        //&& player.intersects(this)
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        this.player = getScene().getLastActorByType(Player.class);


        /*if(player != null){
            new Loop<>(new Invoke<>(this::teleporting)).scheduleFor(player);
        }*/
        new Loop<>(new Invoke<>(this::teleporting)).scheduleFor(player);
    }
}
