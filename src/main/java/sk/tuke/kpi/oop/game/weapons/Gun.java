package sk.tuke.kpi.oop.game.weapons;

//create a class Gun, which extends the abstract class Firearm
public class Gun extends Firearm{
    //set default class Gun
    public Gun(int amount, int maxAmmo){
        super(amount, maxAmmo);
    }

    @Override
    protected Fireable createBullet(){
        return new Bullet();
    }
}
