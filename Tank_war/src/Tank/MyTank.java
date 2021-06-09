package Tank;

import java.util.Vector;
@SuppressWarnings({"all"})
public class MyTank extends Tank {
    public MyTank(int x, int y) {
        super(x, y);
    }

    Shot shot = null;
    Vector<Shot> shots = new Vector<>();

    public void shotEnemyTank() {
        //创建 Shot 对象, 根据当前Hero对象的位置和方向来创建Shot
        switch (getDirection()) {
            case 0: //向上
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1: //向右
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2: //向下
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3: //向左
                shot = new Shot(getX(), getY() + 20, 3);
                break;
        }

        //把新创建的shot放入到shots
        shots.add(shot);
        //启动我们的Shot线程
        new Thread(shot).start();

    }
}
