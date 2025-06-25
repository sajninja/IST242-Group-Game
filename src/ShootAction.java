/*
 * Karis Jones
 * ShootAction
 * This class creates an action specifically for shooting
 * Move actions extend Action and contain a projectile and shoot type.
 */

import java.awt.*;
import java.util.ArrayList;

public class ShootAction extends Action {

    private Projectile projectile;
    private ShootType shootType;

    public ShootAction(Projectile projectile, ShootType shootType, int weight) {
        this.projectile = projectile;
        this.shootType = shootType;
        setWeight(weight);
    }


    @Override
    public boolean checkCondition() {
        return true;
    }

    @Override
    public void doAction() {

    }

    public ArrayList<Projectile> shootProjectile(Vector2 position) {
        ArrayList<Projectile> output = new ArrayList<>();
        Projectile projectile = new Projectile(this.projectile);
        projectile.setPosition(new Vector2(position.getX(), position.getY()));
        Vector2 velocity = Vector2.AtoB(new Vector2(Game.player.getTruePosition().getX(), Game.player.getTruePosition().getY()), position);
        velocity.normalize(projectile.speed);
        projectile.setVelocity(velocity);

        output.add(projectile);
        return output;
    }
}
