import java.util.ArrayList;

public abstract class Enemy extends LivingEntity {

    private int[] actionTimeRange;
    private int actionTimer;
    private Player player;

    public Enemy(Vector2 position, Player player) {
        setPosition(position);
        this.player = player;
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

    public ArrayList<Projectile> shootProjectile() {
        return new ArrayList<>();
    }


}
