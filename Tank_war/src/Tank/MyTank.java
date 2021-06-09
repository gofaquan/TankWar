package Tank;

public class MyTank extends Tank {
    public MyTank(int x, int y){
        super(x,y);
    }

    Shot shot = null ;

    public void ShotEnemy(){
        //创建 Shot 对象, 根据当前MyTank的位置和方向来创建Shot
        switch (getDirection()) {//得到Hero对象方向
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
        //启动我们的Shot线程
        new Thread(shot).start();

    }
}
