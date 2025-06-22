public abstract class LivingEntity extends GameObject {
    private int health;

    public int getHealth() {
        return health;
    }

    public void setHealth(int input) {
        health = input;
    }

    public void changeHealth(int input) {
        health += input;
    }
}
