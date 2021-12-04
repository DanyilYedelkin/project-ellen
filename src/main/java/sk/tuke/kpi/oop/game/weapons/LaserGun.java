package sk.tuke.kpi.oop.game.weapons;

public class LaserGun extends Firearm {

    public LaserGun(int currentAmmo, int maxAmmo) {
        super(currentAmmo, maxAmmo);
    }

    @Override
    protected Fireable createBullet() {
        return new LaserBeam();
    }
}
