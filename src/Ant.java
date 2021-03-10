import javax.swing.*;

abstract public class Ant{
    ImageIcon image;
    int type;
    int x;
    int y;
}

class WorkAnt extends Ant{
    static int count;
    String path = "resources/AntWork.png";
    WorkAnt(){
        type = 1;
        image = new ImageIcon(Lab1.class.getResource(path));
        count++;
    }
}

class WarAnt extends Ant{
    static int count;
    String path = "resources/AntWar.png";

    WarAnt(){
        type = 2;
        image = new ImageIcon(getClass().getResource(path));
        count++;
    }
}
