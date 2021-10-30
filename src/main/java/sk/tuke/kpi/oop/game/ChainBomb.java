package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.actions.Loop;


import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
//import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class ChainBomb extends TimeBomb {
    private double ellipse;
    private boolean isActivated = false;
    private float time;

    public ChainBomb(float time) {
        super(time);
        this.time = time;
        isActivated = false;
    }

    public void explode() {
        this.isActivated = true;
        super.activate();
        new Loop<>(new Invoke<>(this::timeCheck)).scheduleFor(this);
    }
    private void timeCheck(){
        //time--;
        /*if(time <= 0) {
            new Loop<>(new Invoke<>(this::explodeAll)).scheduleFor(this);
        }*/
        /*new When<>(
            () -> this.time <= 0,
            new Invoke<>(this::explodeAll)
        ).scheduleFor(this);*/

        new ActionSequence<>(
            new Wait<>(time),
            new Invoke<>(this::explodeAll)
        ).scheduleFor(this);
    }

    private void explodeAll(){
        //this.isActivated = true;
        time = 0;
        //super.activate();
        int x = this.getPosX();
        int y = this.getPosY();
        //Ellipse2D.Float Ellipse = new Ellipse2D.Float(x, y, getWidth(), getHeight());
        Ellipse2D.Float Ellipse = new Ellipse2D.Float(x - getWidth(), y - getWidth(), 100, 100);
        //RoundRectangle2D.Float Ellipse = new RoundRectangle2D.Float(x - getWidth(), y - getWidth(), 50, 50);


        List<Actor> listBombs = getScene().getActors();

        for(Actor actor : listBombs) {
            if (actor instanceof ChainBomb && !((ChainBomb) actor).isActivated()) {
                Rectangle2D.Float chainBomb =  posChainBomb(actor);

                //for creation an ellipse of boom
                ellipse = Math.pow((actor.getPosY() - this.getPosY()), 2) + Math.pow((actor.getPosX() - this.getPosX()), 2);
                ellipse = Math.sqrt(ellipse);

                // If our boom-ellipse's radius is 50 and less
                if (ellipse <= 50 && !((ChainBomb) actor).isActivated){
                    ((ChainBomb) actor).isActivated = true;
                    ((ChainBomb) actor).explode();
                }

                // probably it isn't work, I don't know why :D
                if (Ellipse.intersects(chainBomb)){
                    ((ChainBomb) actor).isActivated = true;
                    ((ChainBomb) actor).explode();
                }
            }
        }
    }

    /*public void explode() {
        this.isActivated = true;
        super.activate();
        int x = this.getPosX();
        int y = this.getPosY();
        Ellipse2D.Float Ellipse = new Ellipse2D.Float(x - getWidth(), y - getWidth(), 50, 50);
        //RoundRectangle2D.Float Ellipse = new RoundRectangle2D.Float(x - getWidth(), y - getWidth(), 50, 50);

        List<Actor> listBombs = getScene().getActors();

        for(Actor actor : listBombs) {
            if (actor instanceof ChainBomb && !((ChainBomb) actor).isActivated()) {
                Rectangle2D.Float chainBomb =  posChainBomb(actor);

                //for creation an ellipse of boom
                ellipse = Math.pow((actor.getPosY() - this.getPosY()), 2) + Math.pow((actor.getPosX() - this.getPosX()), 2);
                ellipse = Math.sqrt(ellipse);

                // If our boom-ellipse's radius is 50 and less
                if (ellipse <= 84 && !((ChainBomb) actor).isActivated){
                    ((ChainBomb) actor).isActivated = true;
                    ((ChainBomb) actor).activate();
                }

                // probably it isn't work, I don't know why :D
                if (Ellipse.intersects(chainBomb)){
                    ((ChainBomb) actor).isActivated = true;
                    ((ChainBomb) actor).activate();
                }
            }
        }
    }*/
    private Rectangle2D.Float posChainBomb(Actor actor) {
        int x = actor.getPosX();
        int y = actor.getPosY();
        int width = getWidth();
        int height = getHeight();

        return new Rectangle2D.Float(x - width, y - height, width, height);
    }
}
