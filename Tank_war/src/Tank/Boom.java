package Tank;

public class Boom {
    int x,y; //炸弹坐标
    int count = 9; //爆炸状态记录
    boolean isLive = true ;  //是否爆炸完

    public void countDown(){
        if (count >0){
            count--;
        } else {
            isLive = false;
        }
    }

    public Boom(int x,int y) {
        this.x = x;
        this.y = y;
    }
}
