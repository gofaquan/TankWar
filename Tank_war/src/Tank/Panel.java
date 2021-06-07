package Tank;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    //初始化一个坦克
    My_tank my_tank = null;

    public Panel(){
        my_tank = new My_tank(100,100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //以黑色填充矩形
        g.fillRect(0,0,1000,750);

        //调用创建坦克方法生产坦克
        create_tank(my_tank.getX(), my_tank.getY(), g,0,0);
        create_tank(my_tank.getX()+60, my_tank.getY(), g,0,1);
    }

    /**
     * tank
     * @param x         横坐标
     * @param y         纵坐标
     * @param g         画笔功能
     * @param direction 坦克移动方向
     * @param type      坦克类型(我方或敌方
     */

    public void create_tank(int x,int y,Graphics g,int direction,int type){
        //两方的坦克颜色
        switch (type){
            case 0:  g.setColor(Color.cyan);    //我方坦克颜色，青色
                break;
            case 1:  g.setColor(Color.yellow);  //敌方坦克颜色，黄色
                break;
        }

        //坦克的方向
        switch (direction){
            case 0 : //表示向上
                g.fill3DRect(x,y,10,60,false);               //坦克左边轮子
                g.fill3DRect(x+30,y,10,60,false);         //坦克右边轮子
                g.fill3DRect(x+10,y+10 ,20,40,false);  //坦克中间矩形部件
                g.fillOval(x+10,y+22 ,20,20);                //坦克中间圆形部件
                g.drawLine(x+20,y+30,x+20,y);                       //坦克炮筒
            break;
        }
    }
}
