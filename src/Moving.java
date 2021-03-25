
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class Moving extends Thread {
    private Vector<Ant> ants = DrawClass.ants;
    private Timer moveTimer;
    static boolean moveOn = false;

    @Override
    public void run() {
        super.run();
        if (!isInterrupted()) {
            if (moveOn) {
                moveTimer.cancel();
            } else {
                moveTimer = new Timer();
                TimerTask moveTask = new TimerTask() {
                    @Override
                    public void run() {
                        if (!ants.isEmpty() && Ant.speed > 0) {
                            int n = ants.size();
                            for (int i = 0; i < n; i++) {
                                var ant = ants.elementAt(i);
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
                                    ant1.y1 = ant1.y + 100*Math.sin(ant1.angle);
                                    ant1.angle+=Ant.speed*0.01;
                                }
                            }
                        }
                    }
                };
                moveTimer.scheduleAtFixedRate(moveTask, 1, 5);
            }
        }
    }
}
