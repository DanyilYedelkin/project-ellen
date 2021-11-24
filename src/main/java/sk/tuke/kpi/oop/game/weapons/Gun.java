package sk.tuke.kpi.oop.game.weapons;

public class Gun extends Firearm{
    public Gun(int amount, int maxAmmo){
        super(amount, maxAmmo);
    }

    @Override
    protected Fireable createBullet(){
        return new Bullet();
    }
}
