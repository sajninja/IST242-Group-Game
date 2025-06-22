import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Game extends JFrame implements KeyListener {

//    Utility
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 750;
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

        Enemy enemy = new EnemyBasic(new Vector2(WIDTH / 2, HEIGHT / 2 - 250), player);
        nonPlayerObjects.add(enemy);

        enemy = new EnemyBasic(new Vector2(WIDTH / 2 - 100, HEIGHT / 2 - 250), player);
        nonPlayerObjects.add(enemy);

        enemy = new EnemyBasic(new Vector2(WIDTH / 2 + 100, HEIGHT / 2 - 250), player);
        nonPlayerObjects.add(enemy);

//        Initialize key map
        for (int i = 0; i < 255; i++) {
            keyMap.put(i, false);
        }

        try {
            for (String s : images) {
                imageMap.put(s, ImageIO.read(new File(s + ".png")));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        g.setColor(new Color(16, 72, 137));
        g.fillOval((int) player.getPosition().getX(), (int) player.getPosition().getY(), 20, 20);

        for (GameObject o : nonPlayerObjects) {
            if (o instanceof Enemy) {
                g.setColor(new Color(176, 13, 51));
                g.fillOval((int) o.getPosition().getX(), (int) o.getPosition().getY(), 20, 20);
            } else if (o instanceof Projectile) {
                g.setColor(((Projectile) o).getColor());
                g.fillOval((int) o.getPosition().getX(), (int) o.getPosition().getY(), 5, 5);
            }
        }

        for (Projectile p : projectiles) {
            g.setColor(p.getColor());
            g.fillOval((int) p.getPosition().getX(), (int) p.getPosition().getY(), 7, 7);
        }

        g.setColor(new Color(16, 72, 137));
        if (crosshair != null) {
//            Clamp the crosshair's position near the player? Somehow?
//            Vector2 crosshairPos = new Vector2(crosshair.x, crosshair.y);
//            Vector2 distance = Vector2.AtoB(crosshairPos, player.getPosition());
//            if (distance.magnitude() > 100) {
//                crosshairPos.sc
//            }
            g.fillRect(crosshair.x - 10, crosshair.y - 1, 20, 2);
            g.fillRect(crosshair.x - 1, crosshair.y - 10, 2, 20);
        }
    }

    void update() {
        if (keyMap.get(KeyEvent.VK_W)) player.setVelocity(new Vector2(player.getVelocity().getX(), player.getVelocity().getY() - player.getAcceleration()));
        if (keyMap.get(KeyEvent.VK_S)) player.setVelocity(new Vector2(player.getVelocity().getX(), player.getVelocity().getY() + player.getAcceleration()));
        if (keyMap.get(KeyEvent.VK_A)) player.setVelocity(new Vector2(player.getVelocity().getX() - player.getAcceleration(), player.getVelocity().getY()));
        if (keyMap.get(KeyEvent.VK_D)) player.setVelocity(new Vector2(player.getVelocity().getX() + player.getAcceleration(), player.getVelocity().getY()));

        if (player.getVelocity().magnitude() > 7.5) {
            player.getVelocity().normalize(7.5);
        }
        player.setPosition(new Vector2(player.getPosition().getX() + player.getVelocity().getX(), player.getPosition().getY() + player.getVelocity().getY()));
        player.getVelocity().scale(0.5);

        for (GameObject o : nonPlayerObjects) {
            if (o instanceof Enemy) {
                if (((Enemy) o).getActionTimer() == 0) {
                    ((Enemy) o).act();
                    for (Projectile p : ((Enemy) o).shootProjectile()) {
                        projectiles.add(p);
                    }
                    ((Enemy) o).resetActionTimer();
                } else {
                    ((Enemy) o).setActionTimer(((Enemy) o).getActionTimer() - 1);
                }
            }
        }

        for (Projectile p : projectiles) {
            p.setPosition(new Vector2(p.getPosition().getX() + p.getVelocity().getX(), p.getPosition().getY() + p.getVelocity().getY()));
        }
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
        Projectile projectile = new Projectile(5, new Color(0, 150, 200), true);
        projectile.setPosition(player.getPosition());
        projectile.setVelocity(Vector2.AtoB(new Vector2(e.getX(), e.getY()), player.getPosition()));
        projectile.setVelocity(new Vector2(projectile.getVelocity().getX() + player.getVelocity().getX(), projectile.getVelocity().getY() + player.getVelocity().getY()));
        projectile.getVelocity().normalize(8);
        projectile.setFriendly(true);
        projectiles.add(projectile);
    }
}