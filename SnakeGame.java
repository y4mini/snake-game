
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;//used to store segemnts of snakes body
import java.util.Random;//used for getting random values to place food on screen
import javax.swing.*;
//once we have made snake and food, we need to make the snake move. before that we need to make the snake
//game loop . when we make snake move, its x and y position changes, to reflect that on the screen 
//need to redraw the panel. to create gameloop we need a timer
public class SnakeGame extends JPanel implements ActionListener,KeyListener{ 
    private class Tile{               //need to create a new actionPerformed method and override it
        int x;
        int y; 
        Tile(int x, int y){
            this.x=x;
            this.y=y;

        }
    }
    int boardWidth;
    int boardHeight;
    int tileSize=25;
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    Tile food;
    Random random;
    //game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver=false;
    //create constructor
    SnakeGame(int boardWidth, int boardHeight){
        this.boardWidth=boardWidth;
        this.boardHeight=boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);//we want snake game to listen for key presses
        
        snakeHead=new Tile(5,5); //set default starting point for snake head/tile object created
        snakeBody=new ArrayList<Tile>();
        food=new Tile(10,10);
        random=new Random();
        placeFood();
        //if we want to move snake we have to specify a velocity i.e. change in position with time
        velocityX=0;
        velocityY=0;
        gameLoop=new Timer(100, this);//(how long timer runs, what to do every 100 ms)
        gameLoop.start();//same frame drawn over and over again every 100 ms
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        //grid i.e to draw gridline for each 5x5 box
        //for(int i=0;i<boardWidth/tileSize;i++){
            //x1,y1,x2,y2
        //    g.drawLine(i*tileSize,0,i*tileSize,boardHeight);//vertical lines--y:0 to height, x:changes to move from left to right
        //    g.drawLine(0,i*tileSize,boardWidth,i*tileSize);//horizontal --x:0 to width, y:changes from top to bottom
        //}
        //food
        g.setColor(Color.red); //3D rect gives border around tiles
        g.fill3DRect(food.x*tileSize, food.y*tileSize, tileSize,tileSize, true);
        //snake head
        g.setColor(Color.green);
        g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize, true);
        //snake body
        for(int i=0;i<snakeBody.size();i++){
            Tile snakePart=snakeBody.get(i);
            g.fill3DRect(snakePart.x*tileSize,snakePart.y*tileSize, tileSize, tileSize,true);
        }
        //score
        g.setFont(new Font("Arial",Font.PLAIN,16));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: "+String.valueOf(snakeBody.size()),tileSize-16, tileSize);
        }
        else{
            g.drawString("Score: "+String.valueOf(snakeBody.size()),tileSize-16,tileSize);
        }
        
    }
    public void placeFood(){ //randomly places food
        food.x=random.nextInt(boardWidth/tileSize);//24 i.e x position will be a random number between 0 to 24 
        food.y=random.nextInt(boardHeight/tileSize);
    }
    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x==tile2.x && tile1.y==tile2.y;
    }
    public void move(){
        //eat food
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }
        //snake body-- makes snake body move with snake head; without this part only head 
        //moves and body parts scattered in prev food positions
        for(int i=snakeBody.size()-1;i>=0;i--){
            Tile snakePart=snakeBody.get(i);
            if(i==0){ //only head, first case
                snakePart.x=snakeHead.x;
                snakePart.y=snakeHead.y;
            }
            else{ //copy the x and y position of previous snakepart
                Tile prevSnakePart=snakeBody.get(i-1);
                snakePart.x=prevSnakePart.x;
                snakePart.y=prevSnakePart.y;
            }
        }
        //snake head
        snakeHead.x+=velocityX;
        snakeHead.y+=velocityY;
        //game over conditions
        for(int i=0;i<snakeBody.size();i++){
            Tile snakePart=snakeBody.get(i);
            //if snakehead collides with body
            if(collision(snakeHead, snakePart)){
                gameOver=true;
            }
        }
        if(snakeHead.x*tileSize<0 || snakeHead.x*tileSize>boardWidth || 
           snakeHead.y*tileSize<0 || snakeHead.y*tileSize>boardHeight){
               gameOver=true;
           }
    }
    @Override
    public void actionPerformed(ActionEvent e){
        move();//updates x and y position of the snake
        repaint();//every 100 ms, we call this method which will repaint which basically draw over and over again
        if(gameOver){
            gameLoop.stop();
        }
    }
    //on implenting KeyListener, these 3 methods need to be added, we only need to use jeyPressed though
    @Override
    public void keyPressed(KeyEvent e){
        //define the 4 keys we need
        if(e.getKeyCode()==KeyEvent.VK_UP && velocityY!=1){
            velocityX=0;   //snake shouldn't move backward eg if it moves up, it shouldnt
            velocityY=-1;  //move downwards else it will run into its own body that's why we add these conditions
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN && velocityY!=-1){
            velocityX=0;
            velocityY=1;
        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT && velocityX!=1){
            velocityX=-1;
            velocityY=0;
        }
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT && velocityX!=-1){
            velocityX=1;
            velocityY=0;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
