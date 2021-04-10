
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class Moving extends Thread {
    static private Vector<Ant> ants = DrawClass.ants;
    static private Timer moveTimer;
    static boolean moveOn = false;
    private static boolean isEnabled = true;
    static public void disable(){
        isEnabled = false;
        moveTimer.cancel();
        moveOn = false;
        System.out.println("disabled");
    }
    static public void enable(){
        isEnabled = true;
        startTimer();
        System.out.println("enabled");
    }

    static private void startTimer(){
        if (moveOn) {
            moveTimer.cancel();
        } else {
            System.out.println("Timer on");
            moveOn = true;
            moveTimer = new Timer();
            TimerTask moveTask = new TimerTask() {
                @Override
                public void run() {
                    if (!ants.isEmpty() && Ant.speed > 0) {
                        int n = ants.size();
                        for (int i = 0; i < n; i++) {
                            var ant = ants.elementAt(i>=ants.size() ? i--: i);
                            if(ant.isDragging == true) continue;
                            if(ant.type == 1) {
                                WorkAnt ant1 = (WorkAnt) ant;
                                ant1.getVel();
                                if (ant1.state == 0) {
                                    if (ant1.x1 > 0) ant1.x1 += ant1.velX;
                                    if (ant1.y1 > 0) ant1.y1 += ant1.velY;
                                    if (ant1.x1 <= 0 && ant1.y1 <= 0) {
                                        ant1.x1 = 0;
                                        ant1.y1 = 0;
                                        ant1.state = 1;
                                    }
                                }
                                if (ant1.state == 1) {
                                    if (ant1.x1 < ant1.x) ant1.x1 -= ant1.velX;
                                    if (ant1.y1 < ant1.y) ant1.y1 -= ant1.velY;
                                    if (ant1.x1 >= ant1.x && ant1.y1 >= ant1.y) {
                                        ant1.x1 = ant1.x;
                                        ant1.y1 = ant1.y;
                                        ant1.state = 0;
                                    }
                                }
                            }
                            else{
                                WarAnt ant1 = (WarAnt) ant;
                                ant1.x1 = ant1.x + 100*Math.cos(ant1.angle);
                                if(ant1.x1+100 > Lab1.drawPanel.getWidth()) ant1.x1 = Lab1.drawPanel.getWidth()-100;
                                if(ant1.x1 < 0) ant1.x1 = 0;
                                ant1.y1 = ant1.y + 100*Math.sin(ant1.angle);
                                if(ant1.y1+128 > Lab1.drawPanel.getHeight()) ant1.y1 = Lab1.drawPanel.getHeight()-128;
                                if(ant1.y1 < 0) ant1.y1 = 0;

                                ant1.angle+=Ant.speed*0.01;
                            }
                        }
                    }
                }
            };
            moveTimer.scheduleAtFixedRate(moveTask, 1, 5);
        }
    }

    @Override
    public void run() {
        super.run();
        if (isEnabled) {
            System.out.println("in thread");

        }
    }
}
