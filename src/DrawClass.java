import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class DrawClass extends JPanel {
    static Vector<Ant> ants = new Vector<>();
    static boolean timerWorkOn = false;
    static boolean timerWarOn = false;
    static boolean timerTimeOn = false;
    static Timer timerWork = new Timer();
    static Timer timerWar = new Timer();
    static Timer elapsedTimer = new Timer();
    float elapsedTime = 0;
    private Lab1 lab1;
    boolean isClear = false;
    ImageIcon background;

    DrawClass(){
        String path = "resources/Murav.jpg";
        background = new ImageIcon(getClass().getResource(path));
        Ant.speed = 1;
    }

    public void timerOn(){
        if (timerTimeOn) {
            elapsedTimer.cancel();
        } else {
            elapsedTimer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    elapsedTime += 1;
                    lab1.timeElapsed.setText(String.valueOf(elapsedTime/1000));
                    int n = ants.size();

                    for (int i = 0; i< n; i++) {
                        var ant = ants.elementAt(i);
                        //System.out.println(ant.birth + " + " + WorkAnt.lifeTime + " <= " + elapsedTime);
                        if (WorkAnt.lifeTime != 0)
                            if (ant.type == 1 && ant.birth + WorkAnt.lifeTime <= elapsedTime) {
                                ants.remove(i);
                                n--;
                                System.out.println("here1");
                            }
                        if (WarAnt.lifeTime != 0)
                            if (ant.type == 2 && ant.birth + WarAnt.lifeTime <= elapsedTime) {
                                ants.remove(i);
                                n--;
                                System.out.println("here2");
                            }
                    }
                    repaint();
                }
            };
            elapsedTimer.scheduleAtFixedRate(task,1, 1);
        }
    }

    public void timerStop(){
        elapsedTimer.cancel();
        elapsedTime = 0;
    }

    public void draw() {
        Random rnd = new Random();
        if (timerWorkOn || timerWarOn) {
            timerWar.cancel();
            timerWork.cancel();
            timerWorkOn = false;
            timerWarOn = false;
        } else {
            timerWar = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    int chance = 50;
                    try {
                        chance = Integer.parseInt(lab1.bornChance2.getText());

                        if (chance < 0){
                            chance = 0;
                            lab1.bornChance2.setText("0");
                        }
                        if (chance > 100) {
                            chance = 100;
                            lab1.bornChance2.setText("100");
                        }
                    }
                    catch (Exception e){
                        lab1.bornChance2.setText(String.valueOf(chance));
                    }
                    Random rndC = new Random();
                    int a = rndC.nextInt(100);
                    if (a <= chance) {
                        var ant = new WarAnt(rnd.nextInt(getWidth()-100), rnd.nextInt(getHeight()-100), (int)(elapsedTime));


                        ants.add(ant);
                        System.out.println("War ant created in " + ant.birth);
                        Lab1.warCount.setText(String.valueOf(WarAnt.count));
                        repaint();
                    }
                }
            };
            timerWarOn = true;
            timerWar.scheduleAtFixedRate(task, Integer.parseInt(lab1.bornSec2.getText()), Integer.parseInt(lab1.bornSec2.getText()));

            timerWork = new Timer();
            TimerTask task1 = new TimerTask() {
                @Override
                public void run() {
                    int chance = 30;
                    try {
                        chance = Integer.parseInt(lab1.bornChance1.getText());
                        if (chance < 0){
                            chance = 0;
                            lab1.bornChance1.setText("0");
                        }
                        if (chance > 100) {
                            chance = 100;
                            lab1.bornChance1.setText("100");
                        }
                    }
                    catch (Exception e){
                        lab1.bornChance1.setText(String.valueOf(chance));
                    }
                    Random rndC = new Random();
                    int a = rndC.nextInt(100);
                    if (a <= chance) {
                        var ant = new WorkAnt(rnd.nextInt(getWidth()-100), rnd.nextInt(getHeight()-100), (int)(elapsedTime));

                        System.out.println("Work ant created in " + ant.birth);
                        ants.add(ant);
                        Lab1.workCount.setText(String.valueOf(WorkAnt.count));
                        repaint();
                    }
                }
            };
            timerWork.scheduleAtFixedRate(task1, Integer.parseInt(lab1.bornSec1.getText()), Integer.parseInt(lab1.bornSec1.getText()));
            timerWorkOn = true;
        }
    }

    public void stopDraw(){
        if (timerWorkOn || timerWarOn){
            timerWork.cancel();
            timerWar.cancel();
            timerWorkOn = false;
            timerWarOn = false;
        }
        isClear = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(background.getImage(),0,0,lab1.drawPanel.getWidth(),lab1.drawPanel.getHeight(),null);
        for (Ant ant:ants) {
            g2.drawImage(ant.image.getImage(),(int) ant.x1,(int)ant.y1,null);
        }
    }
}