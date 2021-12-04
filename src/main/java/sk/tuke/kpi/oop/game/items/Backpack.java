package sk.tuke.kpi.oop.game.items;

//add libraries
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.*;

//create a public class Backpack
public class Backpack implements ActorContainer<Collectible> {
    //create private variables
    private String name;        //the name of the backpack
    private int capacity;       //the capacity of the backpack
    private List<Collectible> backpackItems = new ArrayList<>(); //the list of items in the backpack

    //default Backpack()
    public Backpack(String name, int capacity){
        //set name and capacity of the backpack
        this.name = name;
        this.capacity = capacity;

        //filling the list by items from the backpack
        backpackItems = new ArrayList<>(this.capacity);
    }

    //a method for looking, what is in the backpack
    @Override
    public @NotNull List<Collectible> getContent() {
        //return backpackItems;
        return List.copyOf(backpackItems);
    }

    //a method, which returns the backpack's capacity
    @Override
    public int getCapacity() {
        return capacity;
    }

    ////a method, which returns the backpack's size
    @Override
    public int getSize() {
        return backpackItems.size();
    }

    //a method, which returns the backpack's name
    @Override
    public @NotNull String getName() {
        return name;
    }

    //a method, which adds the item into backpack
    @Override
    public void add(@NotNull Collectible actor) {
        if(capacity > backpackItems.size()) backpackItems.add(actor);
        else throw new IllegalStateException(name + " is full");    //the mistake, which tells that backpack is full
    }

    //a method, which removes the item from backpack
    @Override
    public void remove(@NotNull Collectible actor) {
        if(this.backpackItems.contains(actor)) this.backpackItems.remove(actor);
        /*else{
            throw new IllegalStateException(name + " is empty");
        }*/

    }

    @Nullable
    @Override
    public Collectible peek() {
        if(!isEmpty()) return backpackItems.get(getSize() - 1);
        else return null;
    }

    //a method, which change the items' place
    @Override
    public void shift() {
        Collections.rotate(backpackItems, 1);
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return backpackItems.iterator();
    }

    //a method returns the current situation with fulling backpack (if it's empty, or not)
    public boolean isEmpty() {
        return backpackItems.isEmpty();
    }
}
