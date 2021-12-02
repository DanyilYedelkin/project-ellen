package sk.tuke.kpi.oop.game.characters;

public class Armor {
    private int currentArmor;
    private int maxArmor;

    public Armor(int currentArmor, int maxArmor){
        this.currentArmor = currentArmor;
        this.maxArmor = maxArmor;
    }

    public int getValue(){
        return currentArmor;
    }

    public void refill(int amount){
        if(currentArmor + amount <= maxArmor){
            currentArmor += amount;
        } else restore();
    }

    public void restore(){
        currentArmor = maxArmor;
    }

    public void drain(int amount){
        if(currentArmor - amount > 0){
            currentArmor -= amount;
        } else exhaust();
    }

    public void exhaust(){
        if(currentArmor > 0){
            currentArmor = 0;
        }
    }
}
