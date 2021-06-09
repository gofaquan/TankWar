package Tank;

import java.util.Vector;

/**
 * 敌人的坦克
 */

public class EnemyTank extends Tank  {
    boolean isLive = true;  //敌方坦克是否存活

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    Vector<Shot> shots = new Vector<>();



    }

