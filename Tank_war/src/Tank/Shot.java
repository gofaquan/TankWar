package Tank;

public class Shot implements Runnable {
    int x;            //横坐标
    int y;           //纵坐标
    int direction;   //方向
    int speed = 10;       //速度
    boolean isLive = true;  //子弹进程是否存在


    public Shot(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override   //射击
    public void run() {
        while (isLive) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //射击方向
            switch (direction) {
                case 0:  //向上射
                    y -= speed;
                    break;
                case 1: //向右射
                    x += speed;
                    break;
                case 2: //向下射
                    y += speed;
                    break;
                case 3: //向左射
                    x -= speed;
                    break;
            }

            //碰到边界鲨了这个进程
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750)) {
                        isLive = false;
            }
        }
    }
}
