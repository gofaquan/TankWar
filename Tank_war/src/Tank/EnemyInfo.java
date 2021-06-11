package Tank;


/**
 * @title: EnemyInfo
 * @package Tank
 * @description:
 * @author: kashimashino
 * @date: 2021-06-11 上午6:24
 * @version: V1.0
*/
@SuppressWarnings({"all"})
public class EnemyInfo {

    private int x; //定义横坐标
    private int y; //定义纵坐标
    private int direction; //定义方向

    /**
    *@title: EnemyInfo
    *@author: archLinux
    *@date: 2021/6/11 上午6:48
    *@param: [x, y, direction]
    *构造器初始化敌人坦克的位置
     */
    public EnemyInfo(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getDirection() {
        return direction;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }
}
