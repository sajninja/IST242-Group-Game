/*
 * Karis Jones
 * Projectile
 * This class creates a projectile object
 * Projectiles are non-living entities that are created by living entities. They can be set as friendly, meaning they won't damage the player.
 */

import java.awt.*;

public class Projectile extends GameObject {
    int damage;
    Color color;
    boolean friendly;
    int speed;

    public Projectile(int damage, Color color, boolean friendly, int speed, int size) {
        this.damage = damage;
        this.color = color;
        this.friendly = friendly;
        setSize(size);
        this.speed = speed;
    }

    public Projectile(Projectile input) {
        this.damage = input.damage;
        this.color = input.color;
        this.friendly = input.friendly;
        setSize(input.getSize());
        this.speed = input.speed;
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
