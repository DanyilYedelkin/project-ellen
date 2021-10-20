package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class ChainBomb extends TimeBomb {
    public ChainBomb(float time) {
        super(time);
    }
    public void explode()
    {
        super.activate();
        int x = this.getPosX();
        int y = this.getPosY();
        Ellipse2D.Float Ellipse = new Ellipse2D.Float(x - getWidth(), y - getWidth(), 100, 100);


        List<Actor> listBombs = getScene().getActors();

        for(Actor actor : listBombs) {
            if (actor instanceof ChainBomb) {
                if (!((ChainBomb) actor).isActivated()) {
                    Rectangle2D.Float chain_bomb =  posChainBomb(actor);

                    if (Ellipse.intersects(chain_bomb)) {
                        ((ChainBomb) actor).activate();
                    }
                }
            }
        }

    }
    private Rectangle2D.Float posChainBomb(Actor actor) {
        int x = actor.getPosX();
        int y = actor.getPosY();
        int width = getWidth();
        int height = getHeight();

        return new Rectangle2D.Float(x - width, y - height, width, height);
    }
}
