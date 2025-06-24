import java.awt.*;
import java.util.ArrayList;

public class Enemy extends LivingEntity {

    private int[] actionTimeRange;
    private int actionTimer;
    private Player player;

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
        enemy.setActionTimeRange(new int[] {Game.random.nextInt(5) + 10, Game.random.nextInt(5) + 20});
    }

    static void createStrong(Enemy enemy) {
        enemy.setActionTimeRange(new int[] {Game.random.nextInt(5) + 10, Game.random.nextInt(5) + 20});
    }

    Player player() {
        return player;
    }

    public void setActionTimeRange(int[] input) {
        actionTimeRange = new int[] {input[0], input[1]};
        resetActionTimer();
    }

    public void resetActionTimer() {
        if (actionTimeRange[0] == actionTimeRange[1]) {
            actionTimer = actionTimeRange[0];
        } else {
            actionTimer = Game.random.nextInt(getActionTimerMax() - getActionTimerMin()) + getActionTimerMin();
        }
    }

    public int getActionTimer() {
        return actionTimer;
    }

    public void setActionTimer(int input) {
        actionTimer = input;
    }

    public int getActionTimerMin() {
        return actionTimeRange[0];
    }

    public int getActionTimerMax() {
        return actionTimeRange[1];
    }

    public void act() {
    }

//    public ArrayList<Projectile> shootProjectile() {
//        return new ArrayList<>();
//    }

//    Enemies that skirmish around the player
//    Enemies that stay still
//    Enemies that move away from the player if near and mvoe towards the player if far

    public ArrayList<Projectile> shootProjectile() {
        ArrayList<Projectile> output = new ArrayList<>();
        Projectile projectile = new Projectile(10, new Color(139, 197, 24), false);
        projectile.setPosition(new Vector2(getPosition().getX() + 6.5, getPosition().getY() + 6.5));
        Vector2 velocity = Vector2.AtoB(Vector2.add(new Vector2(Game.player.getTruePosition().getX() - 10, Game.player.getTruePosition().getY() - 10), Game.player.getVelocity()), getPosition());
        velocity.normalize(5);
        projectile.setVelocity(velocity);

        output.add(projectile);
        return output;
    }

}
