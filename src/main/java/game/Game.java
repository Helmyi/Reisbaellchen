package game;

import com.google.common.io.Resources;
import game.ai.AI_MoveRandom;
import game.ai.UnitAI;
import map.Map;
import map.Map3;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("serial")
public class Game extends JPanel implements KeyListener {
    Image imageBuffer;
    Graphics graphicBuffer;

    private static Game theGame;
    private int fps = 55;
    private static int width = 1000;
    private static int height = 600;
    private Map map;

    private Player player;
    private List<UnitAI> unitAIs;
    private List<Image> unitImages;
    private List<Entity> entityList;

    public Game() {
        createTestLevel();

        setFocusable(true); // needed for listeners to work
        addKeyListener(this);
    }

    public static void main(String[] args) {
        theGame = new Game();

        JFrame frame = new JFrame("Reisbaellchen Game");
        frame.add(theGame);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        theGame.run();
    }

    /**
     * only paint, no logic
     */
    @Override
    public void paint(Graphics g) {
        if (imageBuffer == null) {
            imageBuffer = this.createImage(width, height);
            graphicBuffer = imageBuffer.getGraphics();
        }

        player.getPlayerCamera().paint(graphicBuffer);
        g.drawImage(imageBuffer, 0, 0, this);
    }

    /**
     * game logic
     */
    private void tick() {

        for (UnitAI ai : unitAIs) {
            ai.tick();
        }

        Collections.sort(entityList);
        for (Entity ent : entityList) {
            ent.tick();
        }

        //update camera position
        Player player = getPlayer();
        PlayerCamera cam = getPlayer().getPlayerCamera();
        player.updatePlayerPosition();
        cam.updateViewPointX();
        cam.updateViewPointY();
    }

    public void createTestLevel() {
        entityList = new ArrayList<Entity>();
        unitImages = new ArrayList<Image>();
        unitAIs = new ArrayList<UnitAI>();
        player = new Player();

        try {
            unitImages.add(ImageIO.read(Resources.getResource("Fa_big_head2.png")));
            unitImages.add(ImageIO.read(Resources.getResource("Hero_Base.png")));
            entityList.add(new Unit(unitImages.get(1), 10 * 32, 5 * 32));
            player.setPlayerUnit((Unit) entityList.get(0));
            PlayerCamera cam = new PlayerCamera((int) player.getPlayerUnit().getX(),
                    (int) player.getPlayerUnit().getY());
            cam.setPlayerTileWidth(player.getPlayerUnit().getTileWidth());
            cam.setPlayerTileHeight(player.getPlayerUnit().getTileHeight());
            player.setPlayerCamera(cam);

            entityList.add(new Unit(unitImages.get(1), 11 * 32, 5 * 32, 1));
            entityList.add(new Unit(unitImages.get(1), 13 * 32, 7 * 32, 2));
            entityList.add(new Unit(unitImages.get(1), 15 * 32, 7 * 32, 3));
            entityList.add(new Unit(unitImages.get(1), 15 * 32 + 2, 7 * 32, 14));

            unitAIs.add(new AI_MoveRandom((Unit) entityList.get(1)));
            unitAIs.get(0).addUnit((Unit) entityList.get(2));
            //unitAIs.get(0).addUnit((Unit)entityList.get(3));
            // unitAIs.add(new AI_MoveRandom((Unit)entityList.get(4)));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        map = new Map3("src/main/resources/Zones/TestMap/Wüste6.tmx");
    }

    public void run() {
        long timeBefore, timePassed, sleepTime;
        timeBefore = System.currentTimeMillis();

        // loop
        while (true) {
            tick();
            repaint();

            timePassed = System.currentTimeMillis() - timeBefore;

            sleepTime = (1000 / fps) - timePassed;
            if (sleepTime < 0) {
                sleepTime = 0;
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }
            timeBefore = System.currentTimeMillis();
        }
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if (arg0.getKeyCode() == KeyEvent.VK_0) {
            map = new Map3("resources/Zones/TestMap/Wüste1.tmx");

            return;
        }

        player.keyPressed(arg0);
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        player.keyReleased(arg0);
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        player.keyTyped(arg0);
    }

    public static Game getGameInstance() {
        return theGame;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public Map getMap() {
        return map;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Unit> getUnitsAt(Point2D point) {
        double x = point.getX();
        double y = point.getY();
        List<Unit> units = new ArrayList<>();
        for (Entity entity : entityList) {
            if (entity instanceof Unit) {
                Unit unit = (Unit) entity;
                if (unit.getX() < x && x < (unit.getX() + unit.getTileWidth()) &&
                        unit.getY() < y && y < (unit.getY() + unit.getTileHeight())) {
                    units.add(unit);
                }
            }
        }
        return units;
    }
}
