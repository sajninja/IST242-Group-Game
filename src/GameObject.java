public abstract class GameObject {
    private Vector2 position;
    private Vector2 velocity;

    public Vector2 getPosition() {return position;}
    public Vector2 getVelocity() {return velocity;}

    public void setPosition(Vector2 input) {
        position = input;
    }

    public void setVelocity(Vector2 input) {
        velocity = input;
    }
}
