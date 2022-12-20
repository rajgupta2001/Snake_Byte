import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MyPanel extends JPanel implements KeyListener, ActionListener {

    //Pixel Size is of 25px (So +25 / -25)
    //Image icon object for our game
    ImageIcon snakeTitle=new ImageIcon(getClass().getResource("snaketitle.jpg"));
    ImageIcon snakeImage=new ImageIcon(getClass().getResource("snakeimage.png"));
    ImageIcon up=new ImageIcon(getClass().getResource("upmouth.png"));
    ImageIcon down=new ImageIcon(getClass().getResource("downmouth.png"));
    ImageIcon left=new ImageIcon(getClass().getResource("leftmouth.png"));
    ImageIcon right=new ImageIcon(getClass().getResource("rightmouth.png"));
    ImageIcon food=new ImageIcon(getClass().getResource("food.png"));

    //Right is true coz initially snake is moving right
    boolean isUp=false, isDown=false, isRight=true, isLeft=false;

    //Position of food in x and y axis
    int[] xPos= {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    int[] yPos= {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};

    //For creating random positions for food
    Random random=new Random();
    int foodX=150, foodY=150;

    //Length of snake according to 2D plane (x and y axis)
    int[] snakeX=new int[750];
    int[] snakeY=new int[750];

    //Number of moves
    int move=0;

    //Length of snake beforehand
    int lengthOfSnake=3;
    Timer time;
    boolean gameOver=false;
    int score=0, length=3;

    MyPanel(){
        //3 methods down - key typed, pressed, released
        addKeyListener(this);

        setFocusable(true);

        //Speed of movement of snake in ms
        time=new Timer(50, this);
        time.start();
    }

    //Creating Paint method
    @Override
    public void paint(Graphics g){
        super.paint(g);

        //Making 2 rectangles on frame
        g.setColor(Color.white);
        g.drawRect(24, 10, 851, 55);
        g.drawRect(24, 74, 851, 576);

        //Adding Snake title image on frame
        snakeTitle.paintIcon(this, g, 25, 11);

        //Colouring our game's frame
        g.setColor(Color.black);
        g.fillRect(25,75, 850, 575);

        //Creating snake position at beginning
        if(move==0){
            snakeX[0]=100; //Head of snake
            snakeX[1]=75; //Body of snake
            snakeX[2]=50;

            snakeY[0]=100;
            snakeY[1]=100;
            snakeY[2]=100;
        }

        //Changing image on basis of move
        if(isRight) right.paintIcon(this, g, snakeX[0], snakeY[0]);
        if(isDown) down.paintIcon(this, g, snakeX[0], snakeY[0]);
        if(isLeft) left.paintIcon(this, g, snakeX[0], snakeY[0]);
        if(isUp) up.paintIcon(this, g, snakeX[0], snakeY[0]);

        //Printing body of snake at beginning
        for(int i=1; i<lengthOfSnake; i++){
            snakeImage.paintIcon(this, g, snakeX[i], snakeY[i]);
        }

        //For printing food
        food.paintIcon(this, g, foodX, foodY);

        //If Game Over
        if(gameOver) {
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
            g.drawString("GAME OVER", 300, 300);
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
            g.drawString("Press SPACE to Restart", 330, 360);
        }

        //For Score and Length
        g.setColor(Color.white);
        g.setFont(new Font("ITALIC", Font.PLAIN, 15));
        g.drawString("Score: "+score, 750, 30);
        g.drawString("Length: "+lengthOfSnake, 750, 50);
        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override //To know which key pressed
    public void keyPressed(KeyEvent e) {
        //If GameOver and to restart
        if(e.getKeyCode()==KeyEvent.VK_SPACE && gameOver){
            restart();
        }

        //For Up Key
        if(e.getKeyCode()==KeyEvent.VK_UP && (!isDown)){
            isUp=true;
            isDown=false;
            isLeft=false;
            isRight=false;
            move++;
        }
        //For Down Key
        if(e.getKeyCode()==KeyEvent.VK_DOWN && (!isUp)){
            isUp=false;
            isDown=true;
            isLeft=false;
            isRight=false;
            move++;
        }
        //For Left Key
        if(e.getKeyCode()==KeyEvent.VK_LEFT && (!isRight)){
            isUp=false;
            isDown=false;
            isLeft=true;
            isRight=false;
            move++;
        }
        //For Right Key
        if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!isLeft)){
            isUp=false;
            isDown=false;
            isLeft=false;
            isRight=true;
            move++;
        }
    }

    private void restart() {
        gameOver=false;
        move=0;
        score=0;
        lengthOfSnake=3;
        isUp=false;
        isDown=false;
        isLeft=false;
        isRight=true;
        time.start();
        newFood();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override //Action performed for specific key
    public void actionPerformed(ActionEvent e) {
        for(int i=lengthOfSnake-1; i>0; i--){
            snakeX[i]=snakeX[i-1];
            snakeY[i]=snakeY[i-1];
        }

        //Moving the snake according to direction
        if(isUp) snakeY[0]=snakeY[0]-25;
        if(isDown) snakeY[0]=snakeY[0]+25;
        if(isLeft) snakeX[0]=snakeX[0]-25;
        if(isRight) snakeX[0]=snakeX[0]+25;

        //To make snake move by border and come from opposite border
        if(snakeX[0]>850) snakeX[0]=25;
        if(snakeX[0]<25) snakeX[0]=850;
        if(snakeY[0]>625) snakeY[0]=75;
        if(snakeY[0]<75) snakeY[0]=625;

        //Method for Collisoin with food
        CollisionWithFood();

        //Method for Collisoin with own body
        //Comment this for unlimited health
        //CollisionWithBody();

        repaint();
    }

    //Comment this for unlimited health
    /*private void CollisionWithBody() {
        for(int i=lengthOfSnake-1; i>0; i--){
            if(snakeX[i]==snakeX[0] && snakeY[i]==snakeY[0]){
                time.stop();
                gameOver=true;
            }
        }
    }*/

    private void CollisionWithFood() {
        if(snakeX[0]==foodX && snakeY[0]==foodY){
            //Create new position for food
            newFood();
            lengthOfSnake++;
            score++;
            snakeX[lengthOfSnake-1]=snakeX[lengthOfSnake-2];
            snakeY[lengthOfSnake-1]=snakeY[lengthOfSnake-2];
        }
    }

    private void newFood() {
        foodX=xPos[random.nextInt(xPos.length-1)];
        foodY=yPos[random.nextInt(yPos.length-1)];
        //New food position not to collide with snake
        for(int i=lengthOfSnake-1; i>=0; i--){
        //for(int i=lengthOfSnake-1; i>=0; i++){ //For snake to stop at food
            if(snakeX[i]==foodX && snakeY[i]==foodY){
                newFood();
            }
        }
    }
}
