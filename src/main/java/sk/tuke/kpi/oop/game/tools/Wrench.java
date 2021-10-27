package sk.tuke.kpi.oop.game.tools;

//import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;
import sk.tuke.kpi.oop.game.Reactor;
//import sk.tuke.kpi.oop.game.tools.BreakableTool;

public class Wrench extends BreakableTool <DefectiveLight>{
    //private Animation wrenchAnimation;

    public Wrench() {
        super(2);

        //wrenchAnimation = new Animation("sprites/wrench.png", 16, 16, 0.1f);
        //setAnimation(wrenchAnimation);
        setAnimation(new Animation("sprites/wrench.png", 16, 16, 0.1f));
    }

    @Override
    public void useWith(DefectiveLight defectiveLight){
        if(defectiveLight != null){
            //defectiveLight.repair();
            //super.useWith(this);
            if(defectiveLight.repair())super.useWith(defectiveLight);
        }
    }
}
