/*
 * Karis Jones
 * ShootAction
 * This class creates an action specifically for shooting
 * Move actions extend Action and contain a projectile and shoot type.
 */

public class ShootAction extends Action {

    private Projectile projectile;
    private ShootType shootType;

    public ShootAction(Projectile projectile, ShootType shootType) {
        this.projectile = projectile;
        this.shootType = shootType;
    }


    @Override
    public boolean checkCondition() {
        return false;
    }

    @Override
    public boolean doAction() {
        return false;
    }
}
