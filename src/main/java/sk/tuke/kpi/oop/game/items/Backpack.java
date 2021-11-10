package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.*;

public class Backpack implements ActorContainer<Collectible> {
    private String name;
    private int capacity;
    private List<Collectible> backpackItems;

    public Backpack(String name, int capacity){
        this.name = name;
        this.capacity = capacity;

        backpackItems = new ArrayList<>(this.capacity);
    }

    @Override
    public @NotNull List<Collectible> getContent() {
        return backpackItems;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getSize() {
        return backpackItems.size();
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if(capacity > backpackItems.size()){
            backpackItems.add(actor);
        } else{
            throw new IllegalStateException(name + " is full");
        }

    }

    @Override
    public void remove(@NotNull Collectible actor) {
        if(this.backpackItems.contains(actor)){
            this.backpackItems.remove(actor);
        } else{
            throw new IllegalStateException(name + " is empty");
        }

    }

    @Nullable
    @Override
    public Collectible peek() {
        if(!isEmpty()){
            return backpackItems.get(getSize() - 1);
        } else{
            return null;
        }

    }

    @Override
    public void shift() {
        Collections.rotate(backpackItems, 1);
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return backpackItems.iterator();
    }

    public boolean isEmpty() {
        return backpackItems.isEmpty();
    }

}
