package Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

@SuppressWarnings({"all"})

public class Panel extends JPanel implements KeyListener , Runnable {
    //定义我方坦克
    MyTank my_tank = null;
    //定义敌方坦克,放进Vector保存
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemyTank_nums = 3;  //敌人的坦克数量

    public Panel(){
        //初始化一个我方坦克
        my_tank = new MyTank(100,100);
        //初始化敌方坦克
        for (int i = 0; i < enemyTank_nums; i++) {
        EnemyTank enemyTank= new EnemyTank((100*(i+1)),0);
            //设置方向
            enemyTank.setDirection(2);

            //敌方坦克加入进Vector
            enemyTanks.add(enemyTank);

        }
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //更新窗口，让子弹动起来
            this.repaint();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        //按下W/A/S/D按对应方向移动
        if (e.getKeyCode() == KeyEvent.VK_W){ //按下W键
                my_tank.setDirection(0);   //向上
                my_tank.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_D){ //按下D键
            my_tank.setDirection(1);   //向右
            my_tank.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_S){//按下S键
            my_tank.setDirection(2);   //向下
            my_tank.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A){//按下A键
            my_tank.setDirection(3);   //向左
            my_tank.moveLeft();
        }

        //如果用户按下的是J,就发射
        if(e.getKeyCode() == KeyEvent.VK_J) {
            my_tank.ShotEnemy();
        }
        //更新窗口，让坦克动起来
        this.repaint();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //以黑色填充矩形
        g.fillRect(0,0,1000,750);

        //调用创建坦克方法生产自己的坦克
        create_tank(my_tank.getX(), my_tank.getY(), g,my_tank.getDirection(),0);

        //画出我方坦克射击的子弹
        if(my_tank.shot != null && my_tank.shot.isLive == true) {
            g.draw3DRect(my_tank.shot.x, my_tank.shot.y, 5, 5, false);
        }






        //创建敌人的坦克
        for (int i=0;i<enemyTank_nums;i++){
            //集合中取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //生产敌人坦克
            create_tank(enemyTank.getX(),enemyTank.getY(),g,enemyTank.getDirection(),1);
        }
    }

    /**
     * tank
     * @param x         横坐标
     * @param y         纵坐标
     * @param g         画笔功能
     * @param direction 坦克移动方向
     * @param type      坦克类型(我方或敌方
     */

    //创建坦克
    public void create_tank(int x,int y,Graphics g,int direction,int type){
        //两方的坦克颜色
        switch (type){
            case 0:  g.setColor(Color.cyan);    //我方坦克颜色，青色
                break;
            case 1:  g.setColor(Color.yellow);  //敌方坦克颜色，黄色
                break;
        }

        //坦克朝的方向
        switch (direction){
            case 0 : //表示向上
                g.fill3DRect(x,y,10,60,false);               //坦克左边轮子
                g.fill3DRect(x+30,y,10,60,false);         //坦克右边轮子
                g.fill3DRect(x+10,y+10 ,20,40,false);  //坦克中间矩形部件
                g.fillOval(x+10,y+22 ,20,20);                //坦克中间圆形部件
                g.drawLine(x+20,y+30,x+20,y);                       //坦克炮筒
            break;
            case 1 : //表示向右
                g.fill3DRect(x,y,60,10,false);               //坦克上边轮子
                g.fill3DRect(x,y+30,60,10,false);         //坦克下边轮子
                g.fill3DRect(x+10,y+10 ,40,20,false);  //坦克中间矩形部件
                g.fillOval(x+22,y+10 ,20,20);                //坦克中间圆形部件
                g.drawLine(x+30,y+20,x+60,y+20);                 //坦克炮筒
                break;
            case 2 : //表示向下
                g.fill3DRect(x,y,10,60,false);               //坦克左边轮子
                g.fill3DRect(x+30,y,10,60,false);         //坦克右边轮子
                g.fill3DRect(x+10,y+10 ,20,40,false);  //坦克中间矩形部件
                g.fillOval(x+10,y+22 ,20,20);                //坦克中间圆形部件
                g.drawLine(x+20,y+30,x+20,y+60);                 //坦克炮筒
                break;
            case 3 : //表示向左
                g.fill3DRect(x,y,60,10,false);               //坦克上边轮子
                g.fill3DRect(x,y+30,60,10,false);         //坦克下边轮子
                g.fill3DRect(x+10,y+10 ,40,20,false);  //坦克中间矩形部件
                g.fillOval(x+22,y+10 ,20,20);                //坦克中间圆形部件
                g.drawLine(x,y+20,x+30,y+20);                       //坦克炮筒
                break;
        }
    }


}
