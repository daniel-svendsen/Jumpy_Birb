package se.yrgo.jumpybirb.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import se.yrgo.jumpybirb.utils.ScoreManager;

import java.util.Random;

public class Obstacle {
    private Texture topObstacle;
    private Texture bottomObstacle;
    private Vector2 posTopObstacle, posBottomObstacle; // Position of top and bottom tubes on X axis.

    public static final int OBSTACLE_WIDTH = 40; // pixel width of the tube from the image
    private static final int FLUCTUATION = 140; // so it can move randomly between 0 and 140
    private static final int OBSTACLE_GAP = 140; //  this will be the gap between 2 tubes
    private static final int LOWEST_OPENING = 130; // where from the bottom of the screen can we have top tube
    private Random rand; // to get random top and bottom positions on Y axis
    public Rectangle boundsTop, boundsBot;
    private Rectangle boundSpace;
    private boolean isPassed; // Variable to track whether the obstacle is passed


    public Obstacle(float x) {
        topObstacle = new Texture("UpperObstacle1.png");
        bottomObstacle = new Texture("LowerObstacle1.png");
        rand = new Random();
        posTopObstacle = new Vector2(x, rand.nextInt(FLUCTUATION) + OBSTACLE_GAP + LOWEST_OPENING);
        posBottomObstacle = new Vector2(x, posTopObstacle.y - OBSTACLE_GAP - bottomObstacle.getHeight());
        boundsTop = new Rectangle(posTopObstacle.x, posTopObstacle.y, topObstacle.getWidth() - 5, topObstacle.getHeight() + 5); //Set position of invisible rectangle for top tube
        boundsBot = new Rectangle(posBottomObstacle.x, posTopObstacle.y, bottomObstacle.getWidth() - 5, bottomObstacle.getHeight() - 5); //Set position of invisible rectangle for bottom tube
        boundSpace = new Rectangle(posTopObstacle.x, posTopObstacle.y - OBSTACLE_GAP, topObstacle.getWidth(), topObstacle.getHeight());
        isPassed = false;
    }

    // Method to check if the bird has passed the obstacle
    public boolean isPassed() {
        return isPassed;
    }

    public boolean collides (Rectangle player) {
        boolean collision = player.overlaps(boundsBot) || player.overlaps(boundsTop);
        if (collision && !isPassed) {
            isPassed = true;
            ScoreManager.getInstance().updateScore(); // Update the score using ScoreManager
        }
        return collision;
    }

    public void dispose() {
        topObstacle.dispose();
        bottomObstacle.dispose();
    }

    public void reposition(float x) {
        posTopObstacle.set(x, rand.nextInt(FLUCTUATION) + OBSTACLE_GAP + LOWEST_OPENING);
        posBottomObstacle.set(x, posTopObstacle.y - OBSTACLE_GAP - bottomObstacle.getHeight());
        boundsTop.setPosition(posTopObstacle.x, posTopObstacle.y);
        boundsBot.setPosition(posBottomObstacle.x, posBottomObstacle.y);
        boundSpace.setPosition(posTopObstacle.x, posTopObstacle.y - OBSTACLE_GAP);
        isPassed = false;
    }


    public Texture getTopObstacle() {
        return topObstacle;
    }

    public Texture getBottomObstacle() {
        return bottomObstacle;
    }

    public Vector2 getPosTopObstacle() {
        return posTopObstacle;
    }

    public Vector2 getPosBottomObstacle() {
        return posBottomObstacle;
    }
}