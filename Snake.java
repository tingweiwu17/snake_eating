import java.awt.*;
import java.util.ArrayList;


public class Snake {

    private ArrayList<Node> snakeBody;

    public Snake() {
        snakeBody = new ArrayList<>();
        snakeBody.add(new Node(100,20));
        snakeBody.add(new Node(80, 20));
        snakeBody.add(new Node(60, 20));
        snakeBody.add(new Node(40, 20));
        snakeBody.add(new Node(20, 20));
    }

    public ArrayList<Node> getSnakeBody() {
        return snakeBody;
    }

    public void drawSnake(Graphics g) {

        for(int i = 0; i < snakeBody.size(); i++) {
            if(i%2 ==1){
                g.setColor(Color.ORANGE);
            }else {
                g.setColor(Color.GREEN);
            }
            Node n = snakeBody.get(i);

            if(n.x >= Main.width){
                n.x = 0;
            }
            if(n.y >= Main.height) {
                n.y = 0;
            }
            if(n.x < 0 ){
                n.x = Main.width - Main.CELL_SIZE;
            }
            if(n.y < 0) {
                n.y = Main.height - Main.CELL_SIZE;
            }
            g.fillRect(n.x, n.y, Main.CELL_SIZE, Main.CELL_SIZE);
        }
    }
}
