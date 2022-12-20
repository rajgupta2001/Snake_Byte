import javax.swing.*;
import java.awt.*;

/*
Project(Game) - SNAKE BYTE
Created using Java Swing.
As we used to play snake game on our old mobile phones,
created a same game in which snake eats food and grows in length.
Game will be over when snake overlaps its own body.
Date of Project - 15 December 2022
*/
public class SnakeGame {
    JFrame frame;

    SnakeGame(){

        //Creating frame for our game
        frame=new JFrame("Snake Byte");
        frame.setBounds(10, 10, 905, 700);

        //Editing background using MyPanel Class
        MyPanel myPanel=new MyPanel();
        myPanel.setBackground(Color.darkGray);
        frame.add(myPanel);

        //Stop execution if we close
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SnakeGame sg=new SnakeGame();
    }
}
