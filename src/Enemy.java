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
    private ArrayList<MoveAction> moveActions;
    private ArrayList<ShootAction> shootActions;

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

    void createBasic(Enemy enemy) {
        enemy.moveTimer = new ActionTimer(new int[] {Game.random.nextInt(5) + 10, Game.random.nextInt(5) + 20});
        enemy.shootTimer = new ActionTimer(new int[] {Game.random.nextInt(5) + 20, Game.random.nextInt(5) + 30});
        enemy.setHealth(50);
        enemy.setSize(30);
        this.shootActions = new ArrayList<>();
        this.shootActions.add(new ShootAction(new Projectile(5, new Color(139, 197, 24), false, 7, 6), ShootType.SINGLE, 1));
    }

    void createStrong(Enemy enemy) {
        enemy.moveTimer = new ActionTimer(new int[] {Game.random.nextInt(5) + 10, Game.random.nextInt(5) + 20});
        enemy.shootTimer = new ActionTimer(new int[] {Game.random.nextInt(5) + 30, Game.random.nextInt(5) + 60});
        enemy.setHealth(75);
        enemy.setSize(40);
        this.shootActions = new ArrayList<>();
        this.shootActions.add(new ShootAction(new Projectile(20, new Color(50, 100, 0), false, 5, 30), ShootType.SINGLE, 1));
        this.shootActions.add(new ShootAction(new Projectile(20, new Color(50, 100, 0), false, 10, 10), ShootType.SINGLE, 1));
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

    public ArrayList<ShootAction> getShootActions() {
        return shootActions;
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

}
