package com.branden;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;

import javax.swing.JPanel;

/** This class responsible for displaying the graphics, so the snake, grid, kibble, instruction text and high score
 * 
 * @author Clara
 *
 */
public class DrawSnakeGamePanel extends JPanel {
	//FINDBUGS
	private int gameStage = SnakeGame.BEFORE_GAME;  //use this to figure out what to paint

	private Snake snake;
	private Kibble kibble;
	private Score score;
	private Maze maze;

	DrawSnakeGamePanel(GameComponentManager components){
		this.snake = components.getSnake();
		this.kibble = components.getKibble();
		this.score = components.getScore();
		this.maze = components.getMaze();
	}
	
	public Dimension getPreferredSize() {
		//FINDBUGS
        return new Dimension(SnakeGame.getxPixelMaxDimension(), SnakeGame.getyPixelMaxDimension());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        /* Where are we at in the game? 4 phases.. 
         * 1. Before game starts
         * 2. During game
         * 3. Game lost aka game over
         * 4. or, game won
         */

        gameStage = SnakeGame.getGameStage();
        
        switch (gameStage) {
			case SnakeGame.BEFORE_GAME: {
				displayInstructions(g);
				break;
			}
			case SnakeGame.DURING_GAME: {
				displayGame(g);
				break;
			}
			case SnakeGame.GAME_OVER: {
				displayGameOver(g);
				break;
			}
			case SnakeGame.GAME_WON: {
				displayGameWon(g);
				break;
        	}
			//FINDBUGS
			default : {
				displayInstructions(g);
			}
        }
    }

	private void displayGameWon(Graphics g) {
		// TODO Replace this with something really special!
		g.clearRect(100,100,350,350);
		g.drawString("YOU WON SNAKE!!!", 150, 150);
		
	}
	private void displayGameOver(Graphics g) {

		g.clearRect(100,100,350,350);
		g.drawString("GAME OVER", 150, 150);
		
		String textScore = score.getStringScore();
		String textHighScore = score.getStringHighScore();
		String newHighScore = score.newHighScore();
		
		g.drawString("SCORE = " + textScore, 150, 250);
		
		g.drawString("HIGH SCORE = " + textHighScore, 150, 300);
		g.drawString(newHighScore, 150, 400);
		
		g.drawString("press a key to play again", 150, 350);
		g.drawString("Press q to quit the game",150,400);		
    			
	}

	private void displayGame(Graphics g) {
		displayGameGrid(g);
		displaySnake(g);
		drawGameGridKey(g);
		displayKibble(g);
		displayMaze(g);

	}

	// display current x,y coords for snake head in lower right hand of screen
	private void drawGameGridKey(Graphics g) {
		int maxX = SnakeGame.getxPixelMaxDimension();
		int maxY= SnakeGame.getyPixelMaxDimension();
		g.drawString(snake.getSnakeHead(),maxX -100, maxY-50);
	}
	private void displayGameGrid(Graphics g) {
		//FINDBUGS

		int maxX = SnakeGame.getxPixelMaxDimension();
		int maxY = SnakeGame.getyPixelMaxDimension();

		Graphics2D g2d = (Graphics2D) g;

		// create gradient background that repeats, move it according to players direction
		// use the head of the snake as a reference  to move the background.


		//https://docs.oracle.com/javase/7/docs/api/java/awt/RadialGradientPaint.html
		Point2D center = new Point2D.Float(snake.getSnakeHeadX(), snake.getSnakeHeadY());
		float radius = 25;
		float[] dist = {0.0f, 0.2f, 1.0f};
		Color[] colors = {Color.RED, Color.WHITE, Color.BLUE};
		RadialGradientPaint p =
				new RadialGradientPaint(center, radius, dist, colors, RadialGradientPaint.CycleMethod.REPEAT);
		g2d.setPaint(p);

		g.fillRect(0, 0, maxX, maxY);
	}

	private void displayKibble(Graphics g) {

		//Draw the kibble in green
		g.setColor(Color.WHITE);

		//FINDBUGS
		int x = kibble.getKibbleX() * SnakeGame.getSquareSize();
		int y = kibble.getKibbleY() * SnakeGame.getSquareSize();

		//FINDBUGS
		g.fillRect(x+1, y+1, SnakeGame.getSquareSize()-2, SnakeGame.getSquareSize()-2);
		
	}

	private void displaySnake(Graphics g) {

		LinkedList<Point> coordinates = snake.segmentsToDraw();

		//Draw head in grey
		g.setColor(Color.WHITE);
		Point head = coordinates.pop();


		// snake will always be centered in screen. The background will move
		int drawX =  SnakeGame.getxPixelMaxDimension() / 2;
		int drawY =  SnakeGame.getyPixelMaxDimension() / 2;


		g.fillRect( drawX, drawY, SnakeGame.getSquareSize(), SnakeGame.getSquareSize());
		
		//Draw rest of snake in black
		g.setColor(Color.WHITE);
		for (Point p : coordinates) {
			//FINDBUGS
			g.fillOval((int)p.getX(), (int)p.getY(), SnakeGame.getSquareSize(), SnakeGame.getSquareSize());
		}
	}

	private void displayMaze(Graphics g) {
		// set maze color the same as grid color
		g.setColor(Color.DARK_GRAY);
		LinkedList<Point> coordinates = maze.segmentsToDraw();
		for (Point p : coordinates) {
			//FINDBUGS
			g.fillRect((int)p.getX(), (int)p.getY(), SnakeGame.getSquareSize(), SnakeGame.getSquareSize());
		}
	}

	private void displayInstructions(Graphics g) {
        g.drawString("Press any key to begin!",100,200);		
        g.drawString("Press q to quit the game",100,300);		
    	}
}

