public abstract class GameObject {
    private Vector2 position;
    private Vector2 screenPosition;
    private Vector2 velocity;

    public Vector2 getPosition() {return position;}
    public Vector2 getScreenPosition() {return screenPosition;}
    public Vector2 getVelocity() {return velocity;}

    public void setPosition(Vector2 input) {
        position = input;
    }

    public void setScreenPosition(Vector2 input) {
        screenPosition = input;
    }

    public void setVelocity(Vector2 input) {
        velocity = input;
    }
}
