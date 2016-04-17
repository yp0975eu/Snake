package com.branden;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

/**
 * Created by badams on 4/16/16.
 */
public class Options extends JFrame{
    private JTextField screenSizeTextField;
    private JPanel rootPanel;
    private JCheckBox mazesOnCheckBox;
    private JCheckBox warpWallsOnCheckBox;
    private JButton startGameButton;
    private JLabel gameSpeedLabel;
    private JTextField gameSpeedTextField;
    private JLabel gameSpeedErrorLabel;
    private JLabel screenSizeErrorLabel;
    private int gameSpeed;
    private int screenSize;
    private boolean mazes;
    private boolean warp;
    private boolean errors;

    Options(){
        setContentPane(rootPanel);
        setVisible(true);
        pack();
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check that game speed is an int and is within range

                try {
                    gameSpeed = Integer.parseInt(gameSpeedTextField.getText());
                    if (gameSpeed < 1 || gameSpeed > 10) {
                        throw new Exception();
                    }
                    // game speed is in milliseconds.
                    // multiplying by 100 allows for adjusting the game speed by the 10th of a second
                    gameSpeed *= 100;
                    errors = false;
                }
                catch(Exception err){
                    gameSpeedErrorLabel.setText("Only numbers between 1 and 10 allowed.");
                    pack();
                    errors = true;
                }
                try{
                    // TODO: constrain window size to prevent making playing board off screen
                    screenSize = Integer.parseInt(screenSizeTextField.getText());
                    if ( screenSize < 5 || screenSize > 10 ){
                        throw new Exception();
                    }
                    screenSize *= 100;
                    errors = false;
                }catch (Exception err){
                    screenSizeErrorLabel.setText("Enter number between 5 and 10");
                    pack();
                    errors = true;
                }
                // check that screen size is within range
                // check for mazes
                mazes = mazesOnCheckBox.isSelected();
                // check for warp
                warp = warpWallsOnCheckBox.isSelected();
                if ( !errors ) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            SnakeGame.setGameSpeed(gameSpeed);
                            SnakeGame.setScreenSize(screenSize);
                            SnakeGame.setMaze(mazes);
                            SnakeGame.setWarp(warp);
                            SnakeGame.initializeGame();
                            SnakeGame.createAndShowGUI();
                        }
                    });
                }
            }
        });
    }

}
