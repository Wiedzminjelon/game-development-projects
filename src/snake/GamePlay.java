package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Objects;

public class GamePlay extends JPanel implements ActionListener, KeyListener {
    private int[] snakeXLength = new int[750];
    private int[] snakeYLength = new int[750];

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    private ImageIcon headRight;
    private ImageIcon headLeft;
    private ImageIcon headUp;
    private ImageIcon headDown;

    private int lengthOfTheSnake = 3;

    private Timer timer;
    private int delay = 100;

    private ImageIcon tail;
    private int moves = 0;
    private int score = 0;


    private ImageIcon titleImage;

    public GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics graphics) {

        if (moves == 0) {
            snakeXLength[0] = 100;
            snakeXLength[1] = 75;
            snakeXLength[2] = 50;

            snakeYLength[0] = 100;
            snakeYLength[1] = 100;
            snakeYLength[2] = 100;
        }

        //Display title
        titleImage = createImageIcon("title.png");
        titleImage.paintIcon(this, graphics, 25, 5);

        //Display gameplay border
        graphics.setColor(Color.DARK_GRAY);
        graphics.drawRect(24, 74, 851, 577);

        //Display gameplay background
        graphics.setColor(Color.BLACK);
        graphics.fillRect(25, 75, 850, 575);


        // initial position of snake head
        headRight = createImageIcon("headRight.png");
        headRight.paintIcon(this, graphics, snakeXLength[0], snakeYLength[0]);

        for (int i = 0; i < lengthOfTheSnake; i++) {
            if (i == 0 && right) {
                headRight = createImageIcon("headRight.png");
                headRight.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if (i == 0 && left) {
                headLeft = createImageIcon("headLeft.png");
                headLeft.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if (i == 0 && up) {
                headUp = createImageIcon("headUp.png");
                headUp.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if (i == 0 && down) {
                headDown = createImageIcon("headDown.png");
                headDown.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }

            if (i != 0) {
                tail = createImageIcon("tail.png");
                tail.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
        }

        graphics.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private ImageIcon createImageIcon(String path) {
        String picturesLocation = "pictures/";
        try {
            return new ImageIcon(new URL(Objects.requireNonNull(getClass().getResource(picturesLocation + path)).toString()));
        } catch (Exception e) {
            return null;
        }
    }
}
