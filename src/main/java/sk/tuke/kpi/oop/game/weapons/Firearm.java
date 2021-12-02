package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {
    private int maxAmmo;
    private int currentAmmo;

    public Firearm(int amount, int maxAmmo){
        this.maxAmmo = maxAmmo;
        this.currentAmmo = amount;
    }

    public Firearm(int amount){
        this.maxAmmo = amount;
        this.currentAmmo = amount;
    }

    //metóda vráti počet momentálne dostupných nábojov.
    public int getAmmo(){
        return currentAmmo;
    }

    //metóda zvýši počet nábojov zbrane o hodnotu definovanú parametrom newAmmo. Hodnota sa ale môže
    // zvýšiť len natoľko, aby neprekročila maximálny počet nábojov zbrane definovaný pri jej vytvorení.
    public void reload(int newAmmo){
        if(currentAmmo + newAmmo <= maxAmmo){
            currentAmmo += newAmmo;
        } else{
            currentAmmo = maxAmmo;
        }
    }

    protected abstract Fireable createBullet();

    public Fireable fire(){
        if(currentAmmo == 0){
            return null;
        } else{
            currentAmmo--;
            return createBullet();
        }
    }

    public void stealAmmo(int stealingAmount){
        if(currentAmmo - stealingAmount <= 0){
            currentAmmo = 0;
        } else{
            currentAmmo -= stealingAmount;
        }
    }
}
