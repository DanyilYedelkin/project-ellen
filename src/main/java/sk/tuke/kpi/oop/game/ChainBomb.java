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
