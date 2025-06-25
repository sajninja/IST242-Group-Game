/*
 * Karis Jones
 * Enemy
 * This class creates an enemy object.
 * Enemies can have a variety of types, and its type determines its parameters like health, size, color, projectiles, and movement and attack AI.
 */

import java.awt.*;
import java.util.ArrayList;

public class Enemy extends LivingEntity {

    private int[] actionTimeRange;
    private int actionTimer;
    private ActionTimer moveTimer;
    private ActionTimer shootTimer;
    private Player player;
    private ArrayList<Action> moveActions;
    private ArrayList<Action> shootActions;

    public Enemy(EnemyType type, Vector2 position, Player player) {
        createFromType(type);
        setPosition(position);
        setScreenPosition(position);
        this.player = player;
    }

    void createFromType(EnemyType type) {
        switch (type) {
            case BASIC -> createBasic(this);
            case STRONG -> createStrong(this);
        }
    }

    static void createBasic(Enemy enemy) {
        enemy.moveTimer = new ActionTimer(new int[] {Game.random.nextInt(5) + 10, Game.random.nextInt(5) + 20});
        enemy.shootTimer = new ActionTimer(new int[] {Game.random.nextInt(5) + 20, Game.random.nextInt(5) + 30});
        enemy.setHealth(50);
        enemy.setSize(30);
    }

    static void createStrong(Enemy enemy) {
        enemy.moveTimer = new ActionTimer(new int[] {Game.random.nextInt(5) + 10, Game.random.nextInt(5) + 20});
        enemy.shootTimer = new ActionTimer(new int[] {Game.random.nextInt(5) + 30, Game.random.nextInt(5) + 60});
        enemy.setHealth(75);
        enemy.setSize(40);
    }

    Player player() {
        return player;
    }

    public ActionTimer getMoveTimer() {
        return moveTimer;
    }

    public ActionTimer getShootTimer() {
        return shootTimer;
    }



    public void act() {
    }

//    public ArrayList<Projectile> shootProjectile() {
//        return new ArrayList<>();
//    }

//    Enemies that skirmish around the player
//    Enemies that stay still
//    Enemies that move away from the player if near and mvoe towards the player if far
//    Small enemies with low health that move towards allies and shoot healing bullets

    public ArrayList<Projectile> shootProjectile() {
        ArrayList<Projectile> output = new ArrayList<>();
        Projectile projectile = new Projectile(5, new Color(139, 197, 24), false);
        projectile.setPosition(new Vector2(getPosition().getX(), getPosition().getY()));
        projectile.setSize(10);
        Vector2 velocity = Vector2.AtoB(new Vector2(Game.player.getTruePosition().getX(), Game.player.getTruePosition().getY()), getPosition());
        velocity.normalize(5);
        projectile.setVelocity(velocity);

        output.add(projectile);
        return output;
    }

}
