import javax.swing.*;

abstract public class Ant{

    ImageIcon image;
    String path;
    int type;
    double x;
    double y;
    double x1;
    double y1;
    int birth;
    int state = 0;
    static double speed;
    boolean isDragging;
}

class WorkAnt extends Ant{
    static int count;
    static int lifeTime;
    double velX;
    double velY;
    WorkAnt(int x, int y, int birth){
        this.x = x;
        this.y = y;
        x1 = x;
        y1 = y;
        path = "resources/AntWork.png";
        type = 1;
        image = new ImageIcon(Lab1.class.getResource(path));
        count++;
        this.birth = birth;
        state = 0;
        isDragging = false;
    }
    void getVel(){
        double h = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        velX = speed * (-x / h);
        velY = speed * (-y / h);
    }
}

class WarAnt extends Ant{
    static int count;
    static int lifeTime;
    double angle;
    WarAnt(int x, int y, int birth){
        this.x = x;
        this.y = y;
        x1 = x;
        y1 = y;
        path = "resources/AntWar.png";
        type = 2;
        image = new ImageIcon(getClass().getResource(path));
        count++;
        this.birth = birth;
        state = 0;
        isDragging = false;
    }
}
