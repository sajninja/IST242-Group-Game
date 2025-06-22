public class Player extends LivingEntity {
    private final double ACCELERATION = 5;
    public Player(Vector2 position) {
        this.setPosition(position);
    }

    public double getAcceleration() {
        return ACCELERATION;
    }
}
