package sk.tuke.kpi.oop.game.characters;

//add libraries
import java.util.ArrayList;
import java.util.List;

//create a public class Health
public class Health {
    //create private variables
    private int maxHealth;          //for max points of health (hp)
    private int currentHealth;      //for current points of health
    private List<ExhaustionEffect> effect;  //list of the effects

    //default Health()
    public Health(int pointsHealth, int maxHealth){
        //set variables
        effect = new ArrayList<>();

        this.maxHealth = maxHealth;
        currentHealth = pointsHealth;
    }
    //another type of health, if variables: maxHealth = currentHealth
    public Health(int pointsHealth){
        maxHealth = pointsHealth;
        currentHealth = pointsHealth;
    }

    //a method returns the current health points of the character
    public int getValue(){
        return currentHealth;
    }

    //metóda navýši hodnotu zdravia o množstvo určené parametrom.
    // Samozrejme, výsledná hodnota nesmie presiahnuť maximálnu hodnotu zadanú pri vytváraní objektu.
    public void refill(int amount){
        if(currentHealth + amount <= maxHealth) currentHealth += amount;
        else restore();
    }

    //metóda nastaví hodnotu zdravia na maximálnu možnú.
    public void restore(){
        currentHealth = maxHealth;
    }

    //metóda zníži hodnotu zdravia o množstvo určené jej parametrom.
    // Výsledná hodnota nesmie klesnúť pod 0, čo je už stav signalizujúci, že
    // aktér vlastniaci daný objekt zdravia sa úplne vyčerpal a zomrel.
    public void drain(int amount){
        if(currentHealth - amount > 0) currentHealth -= amount;
        else exhaust();
    }

    //metóda spôsobí okamžité úplné vyčerpanie zdravia, a teda nastaví hodnotu zdravia na 0.
    public void exhaust(){
        if(currentHealth > 0){
            currentHealth = 0;

            effect.forEach(ExhaustionEffect::apply);
        }
    }

    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }

    //a method for adding effects
    public void onExhaustion(ExhaustionEffect effect){
        if(this.effect != null && effect != null){
            this.effect.add(effect);
        }
    }

}
