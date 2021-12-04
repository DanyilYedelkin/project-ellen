package sk.tuke.kpi.oop.game.weapons;

//create an abstract class Firearm
public abstract class Firearm {
    //create private variables
    private int maxAmmo;        //the max number of ammo
    private int currentAmmo;    //the current number of ammo

    public Firearm(int amount, int maxAmmo){
        //set variables
        this.maxAmmo = maxAmmo;
        this.currentAmmo = amount;
    }

    public Firearm(int amount){
        //set variables, if the another new variable in the () is one
        this.maxAmmo = amount;
        this.currentAmmo = amount;
    }

    //a method, which return number of current ammo  (metóda vráti počet momentálne dostupných nábojov.)
    public int getAmmo(){
        return currentAmmo;
    }

    //metóda zvýši počet nábojov zbrane o hodnotu definovanú parametrom newAmmo. Hodnota sa ale môže
    // zvýšiť len natoľko, aby neprekročila maximálny počet nábojov zbrane definovaný pri jej vytvorení.
    //a method, with which we can reload our gun
    public void reload(int newAmmo){
        //checking if currentAmmo + newAmmo is <= than maxAmmo
        if(currentAmmo + newAmmo <= maxAmmo) currentAmmo += newAmmo;
        else currentAmmo = maxAmmo;     //if  currentAmmo + newAmmo is > than maxAmmo, so we can't reload the gun
    }

    //a protected and abstract method
    protected abstract Fireable createBullet();

    //a method, which create bullets after each shoot
    public Fireable fire(){
        if(currentAmmo == 0) return null;
        else{
            currentAmmo--;              //after each shoot, the currentAmmo is decrease by 1 point
            return createBullet();      //create bullet
        }
    }

    //a new method, with which Luker can steal the ellen's currentAmmo
    public void stealAmmo(int stealingAmount){
        if(currentAmmo - stealingAmount <= 0) currentAmmo = 0;
        else currentAmmo -= stealingAmount;
    }
}
