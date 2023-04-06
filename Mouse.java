import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Mouse {
    private int x;
    private int y;
    private ImageIcon img;

    public Mouse() {
        img = new ImageIcon(getClass().getResource("cursor.png"));
        this.x = (int) Math.floor(Math.random() * Main.column) * Main.CELL_SIZE;
        this.y = (int) Math.floor(Math.random() * Main.row) * Main.CELL_SIZE;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void drawMouse(Graphics g) {
        //g.setColor(Color.gray);
        //g.fillOval(this.x, this.y, Main.CELL_SIZE, Main.CELL_SIZE);
        img.paintIcon(null,g,this.x,this.y);

    }

    public void setNewLocation(Snake s) {
        int newX;
        int newY;
        boolean overLap;
        do {
            newX = this.x = (int) Math.floor(Math.random() * Main.column) * Main.CELL_SIZE;
            newY = (int) Math.floor(Math.random() * Main.row) * Main.CELL_SIZE;
            overLap = checkOverLap(newX, newY, s);
        } while (overLap);


        this.x = newX;
        this.y = newY;
    }

    private boolean checkOverLap(int x, int y, Snake s) {
        ArrayList<Node> snake_body = s.getSnakeBody();
        for (int j = 0; j < s.getSnakeBody().size(); j++) {
            if (x == snake_body.get(j).x && y == snake_body.get(j).y) {
                return true;
            }
        }

        return false;
    }

}

