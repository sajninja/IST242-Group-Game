import java.awt.*;

public class Projectile extends GameObject{
    int damage;
    Color color;
    boolean friendly;
    public Projectile(int damage, Color color, boolean friendly) {
        this.damage = damage;
        this.color = color;
        this.friendly = friendly;
    }

    public Color getColor() {
        return this.color;
    }

    public boolean isFriendly() {
        return friendly;
    }

    public void setFriendly(boolean input) {
        friendly = input;
    }

//    Make homing bullets
//    Every tick, add a little velocity towards the player
//    Normalize the vector afterwards
}
