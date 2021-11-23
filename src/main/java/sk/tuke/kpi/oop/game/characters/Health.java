package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {
    private int maxHealth;
    private int currentHealth;
    private List<ExhaustionEffect> effect;

    public Health(int pointsHealth, int maxHealth){
        effect = new ArrayList<>();

        this.maxHealth = maxHealth;
        currentHealth = pointsHealth;
    }
    public Health(int pointsHealth){
        maxHealth = pointsHealth;
        currentHealth = pointsHealth;
    }

    public int getValue(){
        return currentHealth;
    }

    //metóda navýši hodnotu zdravia o množstvo určené parametrom.
    // Samozrejme, výsledná hodnota nesmie presiahnuť maximálnu hodnotu zadanú pri vytváraní objektu.
    public void refill(int amount){
        if(currentHealth + amount <= maxHealth){
            currentHealth += amount;
        } else restore();
    }

    //metóda nastaví hodnotu zdravia na maximálnu možnú.
    public void restore(){
        currentHealth = maxHealth;
    }

    //metóda zníži hodnotu zdravia o množstvo určené jej parametrom.
    // Výsledná hodnota nesmie klesnúť pod 0, čo je už stav signalizujúci, že
    // aktér vlastniaci daný objekt zdravia sa úplne vyčerpal a zomrel.
    public void drain(int amount){
        if(currentHealth - amount >= 0){
            currentHealth -= amount;
        } else exhaust();
    }

    //metóda spôsobí okamžité úplné vyčerpanie zdravia, a teda nastaví hodnotu zdravia na 0.
    public void exhaust(){
        currentHealth = 0;

        effect.forEach(ExhaustionEffect::apply);
    }

    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }

    public void onExhaustion(ExhaustionEffect effect){
        if(this.effect != null && effect != null){
            this.effect.add(effect);
        }
    }

}
