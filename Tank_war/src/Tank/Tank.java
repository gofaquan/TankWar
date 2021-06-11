package Tank;


/**
 * @title: Tank
 * @package Tank
 * @description:
 * @author: kashimashino
 * @date: 2021-06-11 上午6:28
 * @version: V1.0
*/

@SuppressWarnings({"all"})
public class Tank {
    private int   x;                //横坐标
    private int   y;                //纵坐标
    private int direction;      //移动的方向
    private int speed =2;
    Boolean isLive = true;

//移动的函数，对应上右下左
    public void moveUp(){
        y -= speed;
    }
    public void moveRight(){
        x += speed;
    }
    public void moveDown(){
        y += speed;
    }
    public void moveLeft(){
        x -= speed;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }
    public void setDirection(int direction) {
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


}
