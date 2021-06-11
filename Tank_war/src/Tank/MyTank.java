package Tank;

import java.util.Vector;

/**
 * @title: MyTank
 * @package Tank
 * @description:
 * @author: kashimashino
 * @date: 2021-06-11 下午9:25
 * @version: V1.0
 * 继承父类Tank
*/

@SuppressWarnings({"all"})
public class MyTank extends Tank {
    public MyTank(int x, int y) {
        super(x, y) ;
    }

    //初始化子弹射击的两个变量，分别为子弹和子弹的存放的Vector
    Shot shot = null;
    //发射多颗子弹要保存进去
//    Vector<Shot> shots = new Vector<>();
    /**
     *@title: shotEnemyTank
     *@author: archLinux
     *@date: 2021/6/11 上午6:59
     *@param: []
     *@return: void
     *射击敌方坦克
     */
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

        //发射多颗子弹
//        //把新创建的shot放入到shots
//        shots.add(shot);
        //启动我们的Shot线程
        new Thread(shot).start();

    }
}
