package Tank;


/**
 * @title: Boom
 * @package Tank
 * @description: 
 * @author: kashimashino
 * @date: 2021-06-8 上午11:28
 * @version: V1.0   
*/
@SuppressWarnings({"all"})
public class Boom {
    int x,y; //炸弹坐标
    int count = 9; //爆炸状态记录
    boolean isLive = true ;  //是否爆炸完


    /**
     *@title: countDown
     *@author: archLinux
     *@date: 2021/6/11 上午6:44
     *@param: []
     *@return: void
     * 数字9开始减少直到0，表示爆炸过程
     */
    public void countDown(){
        if (count >0){
            count--;
        } else {
            isLive = false;
        }
    }
    /**
     *@title: Boom
     *@author: archLinux
     *@date: 2021/6/11 上午6:46
     *@param: [x, y]
     *构造器初始化爆炸位置
     */
    public Boom(int x,int y) {
        this.x = x;
        this.y = y;
    }


}

