import java.awt.*;
import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class EnemyBasic extends Enemy {
    public EnemyBasic(Vector2 position, Player player) {
        super(position, player);
        setActionTimeRange(new int[] {Game.random.nextInt(5) + 10, Game.random.nextInt(5) + 20});
    }

    public void act() {
//        int randomAction = Game.random.nextInt(1);
//        switch (randomAction) {
////            case 0:
////                setPosition(new Vector2(getPosition().getX(), getPosition().getY() - 10));
////                break;
////            case 1:
////                setPosition(new Vector2(getPosition().getX(), getPosition().getY() + 10));
////                break;
////            case 2:
////                setPosition(new Vector2(getPosition().getX() - 10, getPosition().getY()));
////                break;
////            case 3:
////                setPosition(new Vector2(getPosition().getX() + 10, getPosition().getY()));
////                break;
//            case 0:
                shootProjectile();
//                break;
//            default:
//                break;
//        }
    }

    public ArrayList<Projectile> shootProjectile() {
        ArrayList<Projectile> output = new ArrayList<>();
        Projectile projectile = new Projectile(10, new Color(139, 197, 24), false);
        projectile.setPosition(new Vector2(getPosition().getX() + 6.5, getPosition().getY() + 6.5));
        Vector2 velocity = Vector2.AtoB(Vector2.add(Game.player.getPosition(), Game.player.getVelocity()), getPosition());
//        Vector2 velocity = new Vector2(5, 10);
//        System.out.println("pre: " + velocity.getX() + " " + velocity.getY());
        velocity.normalize(5);
//        System.out.println("post: " + velocity.getX() + " " + velocity.getY());
        projectile.setVelocity(velocity);

        output.add(projectile);
        return output;
    }
}
