/*
 * Karis Jones
 * MoveAction
 * This class determines an action specifically for movement.
 * Move actions extend Action and can have a dedicated direction and speed.
 */

public class MoveAction extends Action {

    private double speed;
    private MoveType moveType;
    private Vector2 targetPosition;

    public MoveAction(double speed, MoveType moveType, int weight) {
        this.speed = speed;
        this.moveType = moveType;
        this.setWeight(weight);
    }

    @Override
    public boolean checkCondition() {
        return false;
    }

    @Override
    public boolean doAction() {
        return false;
    }

    public void setTarget(Vector2 input) {
        targetPosition = input;
    }
}
