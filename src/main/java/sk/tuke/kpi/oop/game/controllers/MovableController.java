package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;
//import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {
    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        // another entries of mapping ...
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.RIGHT, Direction.EAST),
        Map.entry(Input.Key.LEFT, Direction.WEST)
    );
    private Movable actor;
    private Set<Input.Key> key;
    private Move<Movable> move;
    private Disposable disposable;
    private Input.Key firstKey;
    private Input.Key secondKey;
    private Direction direction;

    private int counter;

    public MovableController(Movable actor){
        counter = 0;
        move = null;
        this.actor = actor;
        key = new HashSet<>();
    }

    public void keyPressed(@NotNull Input.Key key){
        if(keyDirectionMap.containsKey(key)){
            this.key.add(key);
            if(firstKey == null){
                firstKey = key;
            } else if(secondKey == null){
                secondKey = key;
            }
            starting();//==================
        }
    }
    public void starting() {
        direction = Direction.NONE;
        counter = 0;

        for(Input.Key iterator : this.key){
            if(counter == 0){
                direction = keyDirectionMap.get(iterator);
            }
            if(counter == 1){
                direction = direction.combine(keyDirectionMap.get(iterator));
            }
            counter++;
        }
        if(move != null){
            stop();
        }
        if(direction != Direction.NONE){
            move = new Move<>(direction, Float.MAX_VALUE);
            disposable = move.scheduleFor(actor);//==================
        }

    }
    public void stop(){
        move.stop();
        disposable.dispose();

        move = null;
    }
    public void keyReleased(@NotNull Input.Key key){
        if(keyDirectionMap.containsKey(key)){
            this.key.remove(key);

            if(firstKey == key) firstKey = null;
            if(secondKey == key) secondKey = null;

            starting();
        }
    }
}
