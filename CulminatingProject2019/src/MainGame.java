import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGame extends JPanel implements MouseListener, ActionListener, KeyListener {
    // Constants
    private final int FRAME_HEIGHT = 600;
    private final int FRAME_WIDTH = 712;
    private final int RADIUS = 50;
    private final int CIRCLE_SPAWN_AREA_X = 100;
    private final int CIRCLE_SPAWN_AREA_Y = 100;

    // Dimension
    private final Dimension frameDimensions = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);

    // Integer
    private int delay;
    private int circleX = 100;
    private int circleY = 100;
    private int missCount = 0;
    private int bombClickedCount = 0;
    private int score = 0;

    // Boolean
    private boolean bombSpawned = false;
    private boolean playerLost;
    private boolean playerWon;
    private boolean redSpawned = false;
    private boolean yellowSpawned = false;
    private boolean blueSpawned = false;
    private boolean returnToMainMenu = false;

    // Other Classes
    private StopWatch stopWatch;
    private MusicPlayer musicPlayer;

    // Timer
    private Timer timer;
    private Timer winChecker;


    // Colour Array
    private Color[] colour = {Color.BLUE, Color.RED, Color.YELLOW, Color.BLACK};
// each colour has different characteristics:
    // Black = bomb, do not click
    // Blue = quick boi
    // Red = Z
    // Yellow = X

    public MainGame(String filePath, String songName) {
        super();
        createMainGamePanel();

        // Initialize Variables
        playerWon = false;
        playerLost = false;
        delay = 3000;
        stopWatch = new StopWatch();
        musicPlayer = new MusicPlayer(filePath);
        timer = new Timer(delay, this);
        winChecker = new Timer(0, this);

        musicPlayer.playMusic();
        timer.start();
    }

    public void createMainGamePanel() {
        setPreferredSize(frameDimensions);
        setBackground(Color.ORANGE);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
    }

    //------------------------------------------------OTHER METHODS-----------------------------------------------------

    public void generateRandomX() {
        circleX = (int) (Math.random() * CIRCLE_SPAWN_AREA_X) + 100;
    }

    public void generateRandomY() {
        circleY = (int) (Math.random() * CIRCLE_SPAWN_AREA_Y) + 100;
    }

    public boolean clickedInsideCircle(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        double d = Math.sqrt(Math.pow(mouseX - getCircleX(), 2) + Math.pow(mouseY - getCircleY(), 2));


        return d <= RADIUS;
    }

    public void paint(Graphics g) {
        super.paint(g);

        int i = (int) (Math.random() * (colour.length*2));
        int j = 0;
        if (i == 0|| i == colour.length || i == colour.length + 3) {
            // Blue Circle spawned
            timer.setDelay(delay / 4);
            j = 0;
            blueSpawned = true;
        } else if (i == 1|| i == colour.length + 1) {
            // Red Circle spawned
            j = 1;
            redSpawned = true;
            timer.setDelay(delay);
        } else if (i == 2 || i == colour.length + 2) {
            // Yellow Circle spawned.
            j = 2;
            yellowSpawned = true;
            timer.setDelay(delay);
        } else if (i == 3) {
            // Black Circle spawned
            j = 3;
                bombSpawned = true;
        }

        g.setColor(colour[j]);

        generateRandomX();
        generateRandomY();
        g.fillOval(circleX, circleY, RADIUS, RADIUS);
    }

    public void calculateScore(long timeElapsed) {
        int points = 0;
        if (timeElapsed >= 0 && timeElapsed <= delay / 10) {
            points = 300;
        } else if (timeElapsed > delay / 10 && timeElapsed <= delay / 6) {
            points = 200;
        } else if (timeElapsed > delay / 6 && timeElapsed <= delay / 4) {
            points = 100;
        } else if (timeElapsed > delay / 4 && timeElapsed <= delay / 2) {
            points = 50;
        } else {
            points = 25;
        }

        score += points;
    }

    //---------------------------------------------OVERRIDDEN METHODS---------------------------------------------------
    // Action Listener
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == timer) {
            stopWatch.start();
            generateRandomX();
            generateRandomY();
            if (!bombSpawned) {
                missCount++;
            }

            if (missCount == 5) {
                playerLost = true;
            } else {
                bombSpawned = false;
                repaint();
            }
        }

        if (getMusicPlayer().clipFinished() && !returnToMainMenu) {
            playerWon = true;
        }
    }

    // Mouse Listener
    @Override
    public void mouseClicked(MouseEvent e) {
        long stopTime = 0;
        int points = 0;
        long timeElapsed = 0;

        if (clickedInsideCircle(e) && blueSpawned) {
            stopWatch.stop();
            timer.setDelay(delay);
            missCount = 0;
            generateRandomX();
            generateRandomY();

            System.out.println("Clicked inside circle");
            timeElapsed = stopWatch.getTimeElapsed() / (int) Math.pow(10, 6);
            System.out.println("BC: " + timeElapsed);

            calculateScore(timeElapsed);

            System.out.println("Score: " + getScore());
            stopWatch.start();
            blueSpawned = false;
            timer.restart();
            if (!didPlayerWin()) {
                repaint();
            }
        } else if (clickedInsideCircle(e) && bombSpawned) {
            stopWatch.stop();
            timer.setDelay(delay);
            bombClickedCount++;
            System.out.println("BCC: " + bombClickedCount);
            bombSpawned = false;
            if (bombClickedCount == 3) {
                playerLost = true;
            } else {
                stopWatch.start();
                timer.restart();
                repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // Key Listener

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_Z && redSpawned) {
            stopWatch.stop();
            redSpawned = false;
            calculateScore(stopWatch.getTimeElapsed() / (int) Math.pow(10, 6));
            System.out.println("Pressed Z");
            System.out.println("Score: " + getScore());
            generateRandomX();
            generateRandomY();

            timer.restart();
            stopWatch.start();
            if (!didPlayerWin()) {
                repaint();
            }
        } else if (key == KeyEvent.VK_X && yellowSpawned) {
            stopWatch.stop();
            yellowSpawned = false;

            generateRandomX();
            generateRandomY();
            calculateScore(stopWatch.getTimeElapsed() / (int) Math.pow(10, 6));
            System.out.println("Pressed X");
            System.out.println("Score: " + getScore());
            timer.restart();
            stopWatch.start();
            if (!didPlayerWin()) {
                repaint();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    //--------------------------------------------------GETTERS---------------------------------------------------------

    public int getDelay() {
        return delay;
    }

    public int getCircleX() {
        return circleX;
    }

    public int getCircleY() {
        return circleY;
    }

    public int getMissCount() {
        return missCount;
    }

    public int getBombClickedCount() {
        return bombClickedCount;
    }

    public int getScore() {
        return score;
    }

    public boolean isBombSpawned() {
        return bombSpawned;
    }

    public boolean didPlayerLose() {
        return playerLost;
    }

    public boolean didPlayerWin() {
        return playerWon;
    }

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }

    public void setReturnToMainMenu(boolean returnToMainMenu) {
        this.returnToMainMenu = returnToMainMenu;
    }
}
