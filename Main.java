import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

public class Main extends JPanel implements KeyListener {
    public static final int CELL_SIZE = 20;
    public static int width = 400;
    public static int height = 400;
    public static int row = height / CELL_SIZE;
    public static int column = width / CELL_SIZE;
    private Snake snake;
    private Fruit fruit;
    private Mouse mouse;
    private Boom boom;
    private Timer t;
    private int speed = 100;
    private static String direction;
    private boolean allowKeyPress;
    private int score;
    private int highest_score;
    String myFile ="HS.txt";

    public Main() {
        readHighestScore();
        reset();
        addKeyListener(this);
    }
    private void setTimer(){
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        },0 ,speed);


    }
    private void reset(){
        score = 0;
        if(snake != null){
            snake.getSnakeBody().clear();
        }
        allowKeyPress = true;
        direction = "down";
        snake = new Snake();
        fruit = new Fruit();
        mouse = new Mouse();
        boom = new Boom();
        setTimer();
    }

    @Override
    public void paintComponent(Graphics g) {
        //System.out.println("Repaint component...");

        ArrayList<Node> snake_body = snake.getSnakeBody();
        Node head = snake_body.get(0);
        for(int i =1; i<snake_body.size();i++ ){
            if(snake_body.get(i).x ==head.x && snake_body.get(i).y ==head.y){
                allowKeyPress = false;
                t.cancel();
                t.purge();
                int response = JOptionPane.showOptionDialog(this,"GAME OVER...YOUR SCORE is "+score+".The highest_score was"+highest_score+". Continue?","GAME OVER",
                        JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,JOptionPane.YES_OPTION);
                writeNewScore(score);
                switch (response){
                    case JOptionPane.CLOSED_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.NO_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.YES_OPTION:
                        reset();
                        return;
                }
            }
        }
        if(head.x== boom.getX() && head.y==boom.getY()){
            allowKeyPress = false;
            t.cancel();
            t.purge();
            int response = JOptionPane.showOptionDialog(this,"YOU LOSE!!!!! "+score+".The highest_score was"+highest_score+". Continue?","GAME OVER",
                    JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,JOptionPane.YES_OPTION);
            writeNewScore(score);
            switch (response){
                case JOptionPane.CLOSED_OPTION:
                    System.exit(0);
                    break;
                case JOptionPane.NO_OPTION:
                    System.exit(0);
                    break;
                case JOptionPane.YES_OPTION:
                    reset();
                    return;
            }
        }
        if(snake.getSnakeBody().size()<=1){
            allowKeyPress = false;
            t.cancel();
            t.purge();
            int response = JOptionPane.showOptionDialog(this,"YOU CAN'T HAVE NO BODY!!!!! "+score+".The highest_score was"+highest_score+". Continue?","GAME OVER",
                    JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,JOptionPane.YES_OPTION);
            writeNewScore(score);
            switch (response){
                case JOptionPane.CLOSED_OPTION:
                    System.exit(0);
                    break;
                case JOptionPane.NO_OPTION:
                    System.exit(0);
                    break;
                case JOptionPane.YES_OPTION:
                    reset();
                    return;
            }
        }


        g.fillRect(0, 0, width, height);

        snake.drawSnake(g);
        fruit.drawFruit(g);
        mouse.drawMouse(g);
        boom.drawBoom(g);
        //remove tail and put it in head
        int snakeX = snake.getSnakeBody().get(0).x;
        int snakeY = snake.getSnakeBody().get(0).y;
        // right -> x += CELL_SIZE
        // left -> x -= CELL_SIZE
        // down -> y += CELL_SIZE
        // up -> y -= CELL_SIZE
        if(direction.equals("right")){
            snakeX += CELL_SIZE;
        } else if(direction.equals("left")){
            snakeX -= CELL_SIZE;
        } else if(direction.equals("down")){
            snakeY += CELL_SIZE;
        } else if(direction.equals("up")){
            snakeY -= CELL_SIZE;
        }
        Node newHead = new Node(snakeX, snakeY);
        if(snake.getSnakeBody().get(0).x ==fruit.getX() && snake.getSnakeBody().get(0).y ==fruit.getY()){
            fruit.setNewLocation(snake);
            fruit.drawFruit(g);
            score++;
        }else {
            snake.getSnakeBody().remove(snake.getSnakeBody().size()-1);
        }
        if(snake.getSnakeBody().get(0).x == mouse.getX() && snake.getSnakeBody().get(0).y == mouse.getY()){
            mouse.setNewLocation(snake);
            mouse.drawMouse(g);
            snake.getSnakeBody().remove(snake.getSnakeBody().size()-1);
        }
        if(head.x==0 || head.x==Main.width || head.y==0 ||head.y==Main.height){
            snake.getSnakeBody().remove(snake.getSnakeBody().size()-2);
        }

        snake.getSnakeBody().add(0, newHead);
        allowKeyPress = true;






        requestFocusInWindow();
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Snake Eating");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new Main());
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setResizable(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println(e.getKeyCode());
        if(allowKeyPress){
            if(e.getKeyCode()==37 && !direction.equals("right")){
                direction = "left";
            }else if(e.getKeyCode()==38 && !direction.equals("down")){
                direction = "up";
            }else if(e.getKeyCode()==39 && !direction.equals("left")){
                direction = "right";
            }else if(e.getKeyCode()==40 && !direction.equals("up")){
                direction = "down";
            }
            allowKeyPress = false;
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public void readHighestScore(){
        try{
            File myObj = new File(myFile);
            Scanner myReader = new Scanner(myObj);
            highest_score = myReader.nextInt();
            myReader.close();
        }catch (FileNotFoundException e){
            highest_score = 0;
            try{
                File myObj = new File(myFile);
                if(myObj.createNewFile()){
                    System.out.println("File created"+myObj.getName());
                }
                FileWriter myWriter = new FileWriter(myObj.getName());
                myWriter.write(""+0);
                myWriter.close();
            }catch (IOException err){
                System.out.println("An error occurred");
                err.printStackTrace();

            }
        }
    }
    public void writeNewScore(int score){
        try {
            if(score>highest_score){
                FileWriter myWriter = new FileWriter(myFile);
                System.out.println("Record Highest score");
                myWriter.write(""+score);
                highest_score= score;
                myWriter.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}

