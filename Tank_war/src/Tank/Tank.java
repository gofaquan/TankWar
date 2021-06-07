package Tank;

/**
 * @author archLinux
 *
 */

public class Tank {
    private int   x;                //横坐标
    private int   y;                //纵坐标
    private int   orientation;      //移动的方向


    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
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
