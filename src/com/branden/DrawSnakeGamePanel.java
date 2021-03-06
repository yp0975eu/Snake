package com.branden;

import java.awt.*;
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
		displayKibble(g);
		displayMaze(g);

	}

	private void displayGameGrid(Graphics g) {
		//FINDBUGS
		int maxX = SnakeGame.getxPixelMaxDimension();
		int maxY= SnakeGame.getyPixelMaxDimension();
		int squareSize = SnakeGame.getSquareSize();
		
		g.fillRect(0, 0, maxX, maxY);

		g.setColor(Color.BLACK);

		//Draw grid - horizontal lines
		for (int y=0; y <= maxY ; y+= squareSize){			
			g.drawLine(0, y, maxX, y);
		}
		//Draw grid - vertical lines
		for (int x=0; x <= maxX ; x+= squareSize){			
			g.drawLine(x, 0, x, maxY);
		}
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

		//FINDBUGS
		g.fillRect((int)head.getX(), (int)head.getY(), SnakeGame.getSquareSize(), SnakeGame.getSquareSize());
		
		//Draw rest of snake in black
		g.setColor(Color.WHITE);
		for (Point p : coordinates) {
			//FINDBUGS
			g.fillRect((int)p.getX(), (int)p.getY(), SnakeGame.getSquareSize(), SnakeGame.getSquareSize());
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

