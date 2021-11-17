package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.items.Usable;


public class Use<A extends Actor> extends AbstractAction<A> {
    private Usable<A> item;

    public Use(Usable<A> item){
        this.item = item;
    }

    @Override
    public void execute(float deltaTime) {
        if(!isDone()){
            item.useWith(getActor());
            setDone(true);
        }
    }

    public Disposable scheduleForIntersectingWith(Actor mediatingActor) {
        Scene scene = mediatingActor.getScene();
        if (scene == null) return null;
        Class<A> usingActorClass = item.getUsingActorClass();  // `usable` je spominana clenska premenna

        for (Actor actor : scene) {
            if (mediatingActor.intersects(actor) && usingActorClass.isInstance(actor)) {
                return this.scheduleFor(usingActorClass.cast(actor));  // naplanovanie akcie v pripade, ze sa nasiel vhodny akter
            }
        }
        return null;
    }

    /*
    //Poznámka
    //Ak ste sa už oboznámili so stream API pre prácu s kolekciami a chceli by ste ho využiť v tomto prípade, ekvivalentná implementácia tejto metódy môže byť nasledovná:

    public Disposable scheduleForIntersectingWith(Actor mediatingActor) {
        Scene scene = mediatingActor.getScene();
        if (scene == null) return null;
        Class<T> usingActorClass = usable.getUsingActorClass();  // `usable` je spominana clenska premenna
        return scene.getActors().stream()  // ziskame stream aktérov na scene
            .filter(mediatingActor::intersects)  // vyfiltrujeme akterov, ktori su v kolizii so sprostredkovatelom
            .filter(usingActorClass::isInstance) // vyfiltrujeme akterov kompatibilneho typu
            .map(usingActorClass::cast)  // vykoname pretypovanie streamu akterov
            .findFirst()  // vyberieme prveho (ak taky existuje) aktera zo streamu
            .map(this::scheduleFor)  // zavolame metodu `scheduleFor` s najdenym akterom a vratime `Disposable` objekt
            .orElse(null);  // v pripade, ze ziaden vyhovujuci akter nebol najdeny, vratime `null`
    }
    */
}
