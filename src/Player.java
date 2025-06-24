public class Player extends LivingEntity {
    private final double ACCELERATION = 2;
    public Player(Vector2 position) {
        this.setPosition(position);
    }

    public double getAcceleration() {
        return ACCELERATION;
    }

    public Vector2 getTruePosition() {
        return new Vector2(getPosition().getX() + (double) Game.WIDTH / 2, getPosition().getY() + (double) Game.HEIGHT / 2);
    }
}
