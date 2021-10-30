package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;


import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class ChainBomb extends TimeBomb {
    //private double ellipse;
    //private boolean isActivated;
    private float time;

    public ChainBomb(float time) {
        super(time);
        this.time = time;
        //isActivated = false;
    }
    @Override
    public void activate(){
        super.activate();
        //this.isActivated = true;
        new Loop<>(new Invoke<>(this::timeCheck)).scheduleFor(this);
    }

    private void timeCheck(){
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
        Ellipse2D Ellipse = new Ellipse2D.Float(x - 42, y - 58, 100, 100);
        //RoundRectangle2D.Float Ellipse = new RoundRectangle2D.Float(x - getWidth(), y - getWidth(), 50, 50);


        List<Actor> listBombs = getScene().getActors();

        for(Actor actor : listBombs) {
            if (actor instanceof ChainBomb && !((ChainBomb) actor).isActivated()) {
                Rectangle2D chainBomb = posChainBomb(actor);
                //for creation an ellipse of boom
                //ellipse = Math.pow((actor.getPosY() - this.getPosY()), 2) + Math.pow((actor.getPosX() - this.getPosX()), 2);
                //ellipse = Math.sqrt(ellipse);

                // probably it isn't work, I don't know why :D
                if (Ellipse.intersects(chainBomb)){
                    //((ChainBomb) actor).isActivated = true;
                    ((ChainBomb) actor).activate();
                }

                // If our boom-ellipse's radius is 50 and less
                /*if (ellipse <= 50 && !((ChainBomb) actor).isActivated){
                    ((ChainBomb) actor).isActivated = true;
                    ((ChainBomb) actor).activate();
                }*/
            }
        }
    }

    private Rectangle2D.Float posChainBomb(Actor actor) {
        int x = actor.getPosX();
        int y = actor.getPosY();
        int width = actor.getWidth();
        int height = actor.getHeight();

        return new Rectangle2D.Float(x, y - height, width, height);
    }
}
