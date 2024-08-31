
import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
      int boardWidth=600; //pixels
      int boardHeight=boardWidth;
      JFrame frame=new JFrame("Snake"); //create window
      frame.setVisible(true);//make frame visible
      frame.setSize(boardWidth, boardHeight);//window size
      frame.setLocationRelativeTo(null);//opens window at centre of the screen
      frame.setResizable(false);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//program terminates when user clicls on x close button
      //now jpanel needed to draw the game
      //create an instance of SnakeGame
      SnakeGame snakeGame=new SnakeGame(boardWidth,boardHeight);
      frame.add(snakeGame);//places jpanel in the frame
      frame.pack();//title bar takes up space from boardHeight 
      snakeGame.requestFocus();

    }
}