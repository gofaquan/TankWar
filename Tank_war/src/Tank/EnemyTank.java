package Tank;

import java.util.Vector;

/**
 * 敌人的坦克
 */
@SuppressWarnings({"all"})
public class EnemyTank extends Tank implements Runnable {
    boolean isLive = true;  //敌方坦克是否存活

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    Vector<Shot> shots = new Vector<>();

    @Override
    public void run() {
        while (true) {

            if (isLive && shots.size() < 1) {
                Shot s = null;
                switch (getDirection()) {
                    case 0:
                        s = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1:
                        s = new Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2: //向下
                        s = new Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3://向左
                        s = new Shot(getX(), getY() + 20, 3);
                        break;
                }
                shots.add(s);
                //启动
                new Thread(s).start();
            }


            switch (getDirection()) {
                case 0: //向上
                    for (int i = 0; i < 15; i++) {
                        if (getY() > 0) {
                            moveUp();
                        }
                    }
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1: //向右
                    for (int i = 0; i < 15; i++) {
                        if (getX() + 60 < 1000) {
                            moveRight();
                        }
                    }
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2: //向下
                    for (int i = 0; i < 15; i++) {
                        if (getY() + 60 < 750) {
                            moveDown();
                        }
                    }
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:  //向左
                    for (int i = 0; i < 15; i++) {
                        if (getX() > 0) {
                            moveLeft();
                        }
                    }
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }


            setDirection((int) (Math.random() * 4));
            //敌方坦克爆炸，退出线程
            if (!isLive) {
                break;
            }
        }
    }
}
