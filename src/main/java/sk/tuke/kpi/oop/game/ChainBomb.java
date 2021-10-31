//package sk.tuke.kpi.oop.game;

//import sk.tuke.kpi.gamelib.Actor;
/*
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
*/

/*import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class ChainBomb extends TimeBomb {
    //private double ellipse;

    public ChainBomb(float time){
        super(time);
        //this.time = time;
    }
    @Override
    public void setExploded(){
        super.setExploded();
        //new Invoke<>(this::timeCheck).scheduleFor(this);
        if(isExploded()){
            explodeAll();
        }
    }

    // probably in the arena (site, which check code), has some problems with multithreading
    //private void timeCheck(){
        //new ActionSequence<>(
            //new Wait<>(time),
            //new Invoke<>(this::explodeAll)
        //).scheduleFor(this);
    //}

    //function for explode all chain bombs in the area of destroying (boom)
    private void explodeAll(){
        int x = this.getPosX();
        int y = this.getPosY();
        Ellipse2D Ellipse = new Ellipse2D.Float(x - 42, y - 58, 100, 100);

        List<Actor> listBombs = getScene().getActors();

        for(Actor actor : listBombs){
            if(actor instanceof ChainBomb && !((ChainBomb) actor).isActivated()){
                Rectangle2D chainBomb = posChainBomb(actor);

                //for creation an ellipse of boom, without Ellipse2D (bad idea)
                //ellipse = Math.pow((actor.getPosY() - this.getPosY()), 2) + Math.pow((actor.getPosX() - this.getPosX()), 2);
                //ellipse = Math.sqrt(ellipse);

                // probably it is work :D
                if(Ellipse.intersects(chainBomb)){
                    //((ChainBomb) actor).isActivated = true;
                    ((ChainBomb) actor).activate();
                }

                // If our boom-ellipse's radius is 50 and less (without function Ellipse2D and Rectangle2D) - bad idea
                //if(ellipse <= 50){
                    //((ChainBomb) actor).isActivated = true;
                    //((ChainBomb) actor).activate();
                //}
            }
        }
    }

    //function, which can help us find the Rectangle2D for all chain bombs
    private Rectangle2D.Float posChainBomb(Actor actor){
        int x = actor.getPosX();
        int y = actor.getPosY();
        int width = actor.getWidth();
        int height = actor.getHeight();

        return new Rectangle2D.Float(x, y - height, width, height);
    }
}*/


// (the code is not perfect, later, it will be modified and improved)

//a new version code (which is working in the arena)
package sk.tuke.kpi.oop.game;

//add libraries
import sk.tuke.kpi.gamelib.Actor;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class ChainBomb extends TimeBomb {

    public ChainBomb(float time) {
        super(time);
    }

    @Override   //override method "explode" for chain bombs
    public void explode(){
        super.explode();
        if (this.isExploded()) explodeAll();
    }

    //a method, which explode all chain bombs
    private void explodeAll(){
        //add coordinates of the chain bomb
        int x = this.getPosX();
        int y = this.getPosY();
        //create an ellipse of the explodes area
        Ellipse2D Ellipse = new Ellipse2D.Float(x - 42, y - 58, 100, 100);

        //add a list of all actors in the scene
        List<Actor> listBombs = getScene().getActors();

        for(Actor actor : listBombs) {
            //check, if actors are from the class ChainBomb, and if they're activated
            if (actor instanceof ChainBomb && !((ChainBomb) actor).isActivated()) {
                Rectangle2D chainBomb = posChainBomb(actor);

                //for explode (BOOOOOM)
                if (Ellipse.intersects(chainBomb)){
                    ((ChainBomb) actor).activate();
                }

            }
        }
    }

    //create a rectangle for all chain bombs
    private Rectangle2D.Float posChainBomb(Actor actor) {
        int x = actor.getPosX();
        int y = actor.getPosY();
        int width = actor.getWidth();
        int height = actor.getHeight();

        return new Rectangle2D.Float(x, y - height, width, height);
    }
}
