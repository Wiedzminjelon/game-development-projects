package brick.breaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements ActionListener, KeyListener {
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballPosX = 210;
    private int ballPosY = 350;
    private int ballXDir = -1;
    private int ballYDir = -2;

    private MapGenerator mapGenerator;

    public GamePlay() {
        mapGenerator = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics graphics) {
        //border bottom
        graphics.setColor(Color.WHITE);
        graphics.fillRect(1, 1, 692, 592);

        mapGenerator.draw((Graphics2D) graphics);

        //borders left, top, right
        graphics.setColor(Color.YELLOW);
        graphics.fillRect(0, 0, 3, 592);
        graphics.fillRect(0, 0, 692, 3);
        graphics.fillRect(691, 0, 3, 592);

        //pedal
        graphics.setColor(Color.BLUE);
        graphics.fillRect(playerX, 550, 100, 8);

        //ball
        graphics.setColor(Color.GREEN);
        graphics.fillOval(ballPosX, ballPosY, 20, 20);

        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString("" + score, 590, 30);

        if (totalBricks <= 0) {
            play = false;
            ballXDir = 0;
            ballYDir = 0;

            graphics.setColor(Color.GREEN);
            graphics.setFont(new Font("serif", Font.BOLD, 30));
            graphics.drawString("You Won, Score : " + score, 190, 300);

            graphics.setFont(new Font("serif", Font.BOLD, 20));
            graphics.drawString("Press Enter to Restart!", 230, 350);
        }

        if (ballPosY > 570) {
            play = false;
            ballXDir = 0;
            ballYDir = 0;

            graphics.setColor(Color.RED);
            graphics.setFont(new Font("serif", Font.BOLD, 30));
            graphics.drawString("Game Over, Score : " + score, 190, 300);

            graphics.setFont(new Font("serif", Font.BOLD, 20));
            graphics.drawString("Press Enter to Restart.", 230, 350);
        }


        graphics.dispose();
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        timer.start();

        if (play) {
            if (new Rectangle(ballPosX, ballPosY, 20, 30).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYDir = -ballYDir;
            }

            for (int i = 0; i < mapGenerator.map.length; i++) {
                for (int j = 0; j < mapGenerator.map[0].length; j++) {
                    if (mapGenerator.map[i][j] > 0) {
                        int brickX = j * mapGenerator.brickWidth + 80;
                        int brickY = i * mapGenerator.brickHeight + 50;
                        int brickWidth = mapGenerator.brickWidth;
                        int brickHeight = mapGenerator.brickHeight;

                        Rectangle rectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = rectangle;

                        if (ballRect.intersects(brickRect)) {
                            mapGenerator.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
                                ballYDir = -ballXDir;
                            } else {
                                ballYDir = -ballYDir;
                            }
                        }
                    }
                }
            }

            //ball movement
            ballPosX += ballXDir;
            ballPosY += ballYDir;

            //bounce from left border
            if (ballPosX < 0) {
                ballXDir = -ballXDir;
            }

            //bounce from top border
            if (ballPosY < 0) {
                ballYDir = -ballYDir;
            }

            //bounce from right border
            if (ballPosX > 670) {
                ballXDir = -ballXDir;
            }

        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }

        if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballXDir = -1;
                ballYDir = -2;
                score = 0;
                totalBricks = 21;
                mapGenerator = new MapGenerator(3, 7);
                repaint();
            }
        }
    }

    private void moveLeft() {
        play = true;
        playerX -= 20;
    }

    private void moveRight() {
        play = true;
        playerX += 20;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
