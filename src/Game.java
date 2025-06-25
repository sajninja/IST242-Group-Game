import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Game extends JFrame implements KeyListener {

//    Utility
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 750;
    private final JPanel gamePanel;
    public static final Random random = new Random();

//    Media
    private Map<String, Image> imageMap = new HashMap<>();
    String[] images = new String[] {
            "player"
    };

//    Keys
    private Map<Integer, Boolean> keyMap = new HashMap<>();

//    Objects
    public ArrayList<GameObject> nonPlayerObjects;
    public ArrayList<Projectile> projectiles;
    public static Player player;

    public Point crosshair = new Point();

//    This value needs to be even or it won't look right
    private static final int strokeLength = 8;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game().setVisible(true);
            }
        });
    }

    public Game() {
        setTitle("Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

//        Initialize lists
        nonPlayerObjects = new ArrayList<>();
        projectiles = new ArrayList<>();

//        Create objects
        player = new Player(new Vector2(WIDTH / 2, HEIGHT / 2));
        player.setVelocity(new Vector2(0, 0));
        player.setSize(30);
        player.setHealth(100);

        Enemy enemy = new Enemy(EnemyType.STRONG, new Vector2(WIDTH / 2, HEIGHT / 2), player);
        nonPlayerObjects.add(enemy);

        enemy = new Enemy(EnemyType.BASIC, new Vector2(WIDTH / 2 - 100, HEIGHT / 2 - 250), player);
        nonPlayerObjects.add(enemy);

        enemy = new Enemy(EnemyType.BASIC, new Vector2(WIDTH / 2 + 100, HEIGHT / 2 - 250), player);
        nonPlayerObjects.add(enemy);

//        Initialize key map
        for (int i = 0; i < 255; i++) {
            keyMap.put(i, false);
        }

//        try {
//            for (String s : images) {
//                imageMap.put(s, ImageIO.read(new File(s + ".png")));
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                draw(g);
            }
        };

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePress(e);
            }
        });

        gamePanel.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent me) {
                crosshair.x = me.getX();
                crosshair.y = me.getY();
                gamePanel.repaint();
            }
        });

        //                Add a game win/lose condition here?
        Timer timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Add a game win/lose condition here?
                update();
                gamePanel.repaint();
            }
        });
        timer.start();

        add(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(this);
    }

    void draw(Graphics g) {
        g.setColor(darkened(Color.green));
        g.fillRect(WIDTH / 2 - player.getHealth() / 2 - strokeLength / 2, HEIGHT / 2 - (int) player.getSize().getY() - strokeLength / 2 - 5, player.getHealth() + strokeLength, 8 + strokeLength);
        g.setColor(Color.green);
        g.fillRect(WIDTH / 2 - player.getHealth() / 2, HEIGHT / 2 - (int) player.getSize().getY() - 5, player.getHealth(), 8);
        g.setColor(darkened(new Color(16, 72, 137)));
        g.fillOval((int) ((double) WIDTH / 2 - player.getSize().getX() / 2) - strokeLength / 2, (int) ((double) HEIGHT / 2 - player.getSize().getY() / 2) - strokeLength / 2, (int) player.getSize().getX() + strokeLength, (int) player.getSize().getY() + strokeLength);
        g.setColor(new Color(16, 72, 137));
        g.fillOval((int) ((double) WIDTH / 2 - player.getSize().getX() / 2), (int) ((double) HEIGHT / 2 - player.getSize().getY() / 2), (int) player.getSize().getX(), (int) player.getSize().getY());
//        g.fillOval((int) (player.getTruePosition().getX()), (int) (player.getTruePosition().getY()), 20, 20);

        for (GameObject o : nonPlayerObjects) {
            if (o instanceof Enemy) {
                g.setColor(darkened(new Color(176, 13, 51)));
                g.fillOval((considerPlayerX(o.getPosition())) - (int) o.getSize().getX() / 2 - strokeLength / 2, (considerPlayerY(o.getPosition())) - (int) o.getSize().getY() / 2 - strokeLength / 2, (int) o.getSize().getX() + strokeLength, (int) o.getSize().getY() + strokeLength);
                g.setColor(new Color(176, 13, 51));
                g.fillOval((considerPlayerX(o.getPosition())) - (int) o.getSize().getX() / 2, (considerPlayerY(o.getPosition())) - (int) o.getSize().getY() / 2, (int) o.getSize().getX(), (int) o.getSize().getY());
                g.setColor(darkened(Color.red));
                g.fillRect(considerPlayerX(o.getPosition()) - ((Enemy) o).getHealth() / 2 - strokeLength / 2, considerPlayerY(o.getPosition()) - (int) o.getSize().getY() - strokeLength / 2 - 5, ((Enemy) o).getHealth() + strokeLength, 8 + strokeLength);
                g.setColor(Color.red);
                g.fillRect(considerPlayerX(o.getPosition()) - ((Enemy) o).getHealth() / 2, considerPlayerY(o.getPosition()) - (int) o.getSize().getY() - 5, ((Enemy) o).getHealth(), 8);
            }
        }

        for (Projectile p : projectiles) {
            g.setColor(darkened(p.getColor()));
            g.fillOval(considerPlayerX(p.getPosition()) - (int) p.getSize().getX() / 2 - strokeLength / 2, considerPlayerY(p.getPosition()) - (int) p.getSize().getY() / 2 - strokeLength / 2, (int) p.getSize().getX() + strokeLength, (int) p.getSize().getY() + strokeLength);
            g.setColor(p.getColor());
            g.fillOval(considerPlayerX(p.getPosition()) - (int) p.getSize().getX() / 2, considerPlayerY(p.getPosition()) - (int) p.getSize().getY() / 2, (int) p.getSize().getX(), (int) p.getSize().getY());
//            g.fillOval((int) (p.getPosition().getX() - player.getPosition().getX()), (int) (p.getPosition().getY() - player.getPosition().getY()), 7, 7);
        }

        g.setColor(new Color(16, 72, 137));
        if (crosshair != null) {
//            Clamp the crosshair's position near the player? Somehow?
//            Vector2 crosshairPos = new Vector2(crosshair.x, crosshair.y);
//            Vector2 distance = Vector2.AtoB(crosshairPos, player.getPosition());
//            if (distance.magnitude() > 100) {
//                crosshairPos.sc
//            }
//            g.fillRect(crosshair.x - 10, crosshair.y - 1, 20, 2);
//            g.fillRect(crosshair.x - 1, crosshair.y - 10, 2, 20);
        }
    }

//    A neat pair of methods that handle some vector operations for drawing
    int considerPlayerX(Vector2 input) {
        return (int) (input.getX() - player.getPosition().getX());
    }

    int considerPlayerY(Vector2 input) {
        return (int) (input.getY() - player.getPosition().getY());
    }

    void update() {
        if (keyMap.get(KeyEvent.VK_W)) player.setVelocity(new Vector2(player.getVelocity().getX(), player.getVelocity().getY() - player.getAcceleration()));
        if (keyMap.get(KeyEvent.VK_S)) player.setVelocity(new Vector2(player.getVelocity().getX(), player.getVelocity().getY() + player.getAcceleration()));
        if (keyMap.get(KeyEvent.VK_A)) player.setVelocity(new Vector2(player.getVelocity().getX() - player.getAcceleration(), player.getVelocity().getY()));
        if (keyMap.get(KeyEvent.VK_D)) player.setVelocity(new Vector2(player.getVelocity().getX() + player.getAcceleration(), player.getVelocity().getY()));

        if (player.getVelocity().magnitude() > 7.5) {
            player.getVelocity().normalize(7.5);
        }
//        Change the player's position in the world
        player.setPosition(new Vector2(player.getPosition().getX() + player.getVelocity().getX(), player.getPosition().getY() + player.getVelocity().getY()));
        player.getVelocity().scale(0.85);

        for (GameObject o : nonPlayerObjects) {
            if (o instanceof Enemy) {
//              Change its position on the screen
//                o.setScreenPosition(new Vector2(o.getPosition().getX() - player.getPosition().getX(), o.getPosition().getY() - player.getPosition().getY()));
//                Nothing yet to change its position in the world...
                if (((Enemy) o).getShootTimer().getTime() == 0) {
                    ((Enemy) o).act();
                    for (Projectile p : ((Enemy) o).shootProjectile()) {
                        projectiles.add(p);
                    }
                    ((Enemy) o).getShootTimer().reset();
                } else {
                    ((Enemy) o).getShootTimer().setTime(((Enemy) o).getShootTimer().getTime() - 1);
                }
            }
        }

        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = projectiles.get(i);
            if (!p.isFriendly()) {
                double distanceToPlayer = new Vector2(p.getPosition().getX() - (player.getTruePosition().getX()), p.getPosition().getY() - (player.getTruePosition().getY())).magnitude();
                if (distanceToPlayer < player.getSize().magnitude() * 0.65) {
                    player.hit(p);
                    if (player.getHealth() < 0) {
                        player.setSize(0);
                    }
                    projectiles.remove(p);
                    i--;
                }
            } else {
                for (int j = 0; j < nonPlayerObjects.size(); j++) {
                    GameObject o = nonPlayerObjects.get(j);
                    if (o instanceof Enemy) {
                        double distanceToEnemy = new Vector2(p.getPosition().getX() - (o.getPosition().getX()), p.getPosition().getY() - (o.getPosition().getY())).magnitude();
//                        Use a value here for enemies with different sizes
                        if (distanceToEnemy < o.getSize().magnitude() * 0.8) {
                            ((Enemy) o).hit(p);
                            if (((Enemy) o).getHealth() <= 0) {
                                nonPlayerObjects.remove(o);
                                j--;
                            }
                            projectiles.remove(p);
                            i--;
                        }
                    }
                }
            }
            p.setPosition(new Vector2(p.getPosition().getX() + p.getVelocity().getX(), p.getPosition().getY() + p.getVelocity().getY()));
//            Add something here to cull projectiles that have gone too far
        }
    }

    Color darkened(Color input) {
        double scale = 1.75;
        int r = (int) (input.getRed() / scale);
        int g = (int) (input.getGreen() / scale);
        int b = (int) (input.getBlue() / scale);
        return new Color(r, g, b);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyMap.put(keyCode, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyMap.put(keyCode, false);
    }

    public void mousePress(MouseEvent e) {
//        for (int i = 0; i < random.nextInt(2) + 1; i++) {
            Projectile projectile = new Projectile(5, new Color(0, 150, 200), true);
            projectile.setPosition(player.getTruePosition());
            projectile.setSize(10);
            projectile.setVelocity(Vector2.AtoB(new Vector2(e.getX(), e.getY()), new Vector2((double) WIDTH / 2, (double) HEIGHT / 2)));
            int inAccuracy = 20;
            projectile.setVelocity(Vector2.add(projectile.getVelocity(), new Vector2(random.nextDouble(inAccuracy) - (double) inAccuracy / 2)));
//            projectile.getVelocity().normalize(random.nextDouble(1) + 7);
            projectile.getVelocity().normalize(12);
//            projectile.setVelocity(Vector2.add(projectile.getVelocity(), player.getVelocity()));
            projectile.setFriendly(true);
            projectiles.add(projectile);
//        }
    }
}


/*
 *
 *
 *
 */