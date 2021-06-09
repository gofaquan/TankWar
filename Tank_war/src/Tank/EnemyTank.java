package Tank;

import java.util.Vector;

/**
 * 敌人的坦克
 */

public class EnemyTank extends Tank implements Runnable {
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    Vector<Shot> shots = new Vector<>();
    boolean isLive = true;

    @Override
    public void run() {
        while (true){

        }
    }
}
