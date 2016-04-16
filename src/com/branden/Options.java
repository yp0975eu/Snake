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
    private int gameSpeed;
    Options( SnakeGame s){
        setContentPane(rootPanel);
        setVisible(true);
        pack();
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // check that game speed is an int and is within range
                try{
                    gameSpeed = Integer.parseInt(gameSpeedTextField.getText());
                } catch(InputMismatchException err){
                    gameSpeedErrorLabel.setText("Only numbers allowed");
                }
                // check that screen size is within range
                // check for mazes
                // check for warp

                System.out.println("StartGame");
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        s.initializeGame();
                        s.createAndShowGUI();
                    }
                });
            }
        });
    }

}
