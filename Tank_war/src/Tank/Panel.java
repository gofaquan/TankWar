package Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

@SuppressWarnings({"all"})

public class Panel extends JPanel implements KeyListener, Runnable {
    //定义我方坦克
    MyTank my_tank = null;
    //定义敌方坦克,放进Vector保存
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义爆炸数，存进Vector
    Vector<Boom> booms = new Vector<>();
    int enemyTank_nums = 9;  //敌人的坦克数量

    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public Panel() {
        //同步上次的坦克数量及坐标
        Records.setAllEnemyTankNum(enemyTanks);
        //初始化一个我方坦克
        my_tank = new MyTank(900, 600);
        //初始化敌方坦克
        for (int i = 0; i < enemyTank_nums; i++) {
            EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 0);
            //传值给敌方坦克防止重叠
            enemyTank.setEnemyTanks(enemyTanks);
            //设置方向
            enemyTank.setDirection(2);
            //启动敌方坦克线程
            new Thread(enemyTank).start();
            //给该enemyTank 加入一颗子弹
            Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
            //加入到enemyTank的Vector
            enemyTank.shots.add(shot);
            //启动 shot
            new Thread(shot).start();
            //敌方坦克加入进Vector
            enemyTanks.add(enemyTank);
        }
        //三张图片，来显示爆炸效果
        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/boom1.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/boom2.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/boom3.png"));
    }
    //编写方法，显示我方击毁敌方坦克的信息
    public void showInfo(Graphics g) {

        //画出玩家的总成绩
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("您累积击毁敌方坦克", 1020, 30);
        create_tank(1020, 60, g, 0, 0);//画出一个敌方坦克
        g.setColor(Color.BLACK);//这里需要重新设置成黑色
        g.drawString(Records.getAllEnemyTankNum()+"", 1080, 100);

    }
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //判断是我们子弹是否击中了敌人坦克
            hitEnemyTank();
            //判断是我们是否被击中了
            hitMyTank();
            //更新窗口，让子弹动起来
            this.repaint();
        }
    }

    public void hitEnemyTank() {

        //遍历我们的子弹
        for(int j = 0;j < my_tank.shots.size();j++) {
            Shot shot = my_tank.shots.get(j);
            //判断是否击中了敌人坦克
            if (shot != null && my_tank.shot.isLive) {//当我的子弹还存活
                //遍历敌人所有的坦克
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitEnemy(my_tank.shot, enemyTank);
                }

            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //按下W/A/S/D按对应方向移动
        if (e.getKeyCode() == KeyEvent.VK_W) { //按下W键
            my_tank.setDirection(0);   //向上
            if (my_tank.getY() > 0) {
                my_tank.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) { //按下D键
            my_tank.setDirection(1);   //向右
            if (my_tank.getX() + 60 < 1000) {

                my_tank.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {//按下S键
            my_tank.setDirection(2);   //向下
            if (my_tank.getY() + 60 < 750) {
                my_tank.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//按下A键
            my_tank.setDirection(3);   //向左
            if (my_tank.getX() > 0) {
                my_tank.moveLeft();
            }
        }

        //如果用户按下的是J,就发射
        if (e.getKeyCode() == KeyEvent.VK_J) {

            //发射多颗子弹
            my_tank.shotEnemyTank();

        }
        //更新窗口，让坦克动起来
        this.repaint();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //填充矩形
        g.fillRect(0, 0, 1000, 750);
        showInfo(g);
        //调用创建坦克方法生产自己的坦克
        if(my_tank != null && my_tank.isLive) {
            create_tank(my_tank.getX(), my_tank.getY(), g, my_tank.getDirection(), 0);
        }
        //画出我方坦克射击的子弹
        for(int i = 0; i < my_tank.shots.size(); i++) {
            Shot shot = my_tank.shots.get(i);
            if (shot != null && shot.isLive) {
                g.draw3DRect(shot.x, shot.y, 5, 5, false);

            } else {//如果该shot已经销毁 ,就从shots集合中拿掉
                my_tank.shots.remove(shot);
            }
        }



        //画出爆炸效果
        for (int i = 0; i < booms.size(); i++) {
            //Vector取出boom
            Boom boom = booms.get(i);
            //根据当前boom的count数值切换图片
            if (boom.count > 6) {
                g.drawImage(image1, boom.x, boom.y, 60, 60, this);
            } else if (boom.count > 3) {
                g.drawImage(image2, boom.x, boom.y, 60, 60, this);
            } else {
                g.drawImage(image3, boom.x, boom.y, 60, 60, this);
            }

            //减少count数值切换接下来的效果
            boom.countDown();
            //爆炸完毕移除这个boom
            if (boom.count == 0) {
                booms.remove(boom);
            }
        }

        //画出敌人的坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            //Vector中取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //生产敌人坦克
            //如果存在
            if (enemyTank.isLive) {
                create_tank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(), 1);
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //获取每个敌方坦克的子弹
                    Shot shot = enemyTank.shots.get(j);
                    //画出敌方坦克射击的子弹
                    if (shot.isLive) {
                        g.draw3DRect(shot.x, shot.y, 5, 5, false);
                    } else {
                        //把被鲨了的子弹进程从Vector移除
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }
    }


    /**
     * tank
     *
     * @param x         横坐标
     * @param y         纵坐标
     * @param g         画笔功能
     * @param direction 坦克移动方向
     * @param type      坦克类型(我方或敌方
     */

    //创建坦克
    public void create_tank(int x, int y, Graphics g, int direction, int type) {
        //两方的坦克颜色
        switch (type) {
            case 0:
                g.setColor(Color.cyan);    //我方坦克颜色，青色
                break;
            case 1:
                g.setColor(Color.yellow);  //敌方坦克颜色，黄色
                break;
        }

        //坦克朝的方向
        switch (direction) {
            case 0: //表示向上
                g.fill3DRect(x, y, 10, 60, false);               //坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);         //坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);  //坦克中间矩形部件
                g.fillOval(x + 10, y + 22, 20, 20);                //坦克中间圆形部件
                g.drawLine(x + 20, y + 30, x + 20, y);                       //坦克炮筒
                break;
            case 1: //表示向右
                g.fill3DRect(x, y, 60, 10, false);               //坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);         //坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);  //坦克中间矩形部件
                g.fillOval(x + 22, y + 10, 20, 20);                //坦克中间圆形部件
                g.drawLine(x + 30, y + 20, x + 60, y + 20);                 //坦克炮筒
                break;
            case 2: //表示向下
                g.fill3DRect(x, y, 10, 60, false);               //坦克左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);         //坦克右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);  //坦克中间矩形部件
                g.fillOval(x + 10, y + 22, 20, 20);                //坦克中间圆形部件
                g.drawLine(x + 20, y + 30, x + 20, y + 60);                 //坦克炮筒
                break;
            case 3: //表示向左
                g.fill3DRect(x, y, 60, 10, false);               //坦克上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);         //坦克下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);  //坦克中间矩形部件
                g.fillOval(x + 22, y + 10, 20, 20);                //坦克中间圆形部件
                g.drawLine(x, y + 20, x + 30, y + 20);                       //坦克炮筒
                break;
        }
    }

    //判断子弹是否击中敌人
    public void hitEnemy(Shot s,Tank enemyTank) {
        switch (enemyTank.getDirection()) {
            //敌人坦克向上
            case 0:
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 40
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    enemyTanks.remove(enemyTank);
                    if (enemyTank instanceof EnemyTank){
                        Records.addAllEnemyTankNum();
                    }
                    booms.add(new Boom(enemyTank.getX(), enemyTank.getY()));
                }
                break;
            //向右
            case 1:
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 60
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 40) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    enemyTanks.remove(enemyTank);
                    if (enemyTank instanceof EnemyTank){
                        Records.addAllEnemyTankNum();
                    }
                    booms.add(new Boom(enemyTank.getX(), enemyTank.getY()));
                }
                break;
            //向下
            case 2:
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 40
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    enemyTanks.remove(enemyTank);
                    if (enemyTank instanceof EnemyTank){
                        Records.addAllEnemyTankNum();
                    }
                    booms.add(new Boom(enemyTank.getX(), enemyTank.getY()));
                }
                break;
            //向左
            case 3:
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 60
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 40) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    enemyTanks.remove(enemyTank);
                    if (enemyTank instanceof EnemyTank){
                        Records.addAllEnemyTankNum();
                    }
                    booms.add(new Boom(enemyTank.getX(), enemyTank.getY()));
                }
                break;
        }
    }

    //编写方法，判断敌人坦克是否击中我的坦克
    public void hitMyTank() {
        //遍历所有的敌人坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出敌人坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //遍历enemyTank 对象的所有子弹
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                //取出子弹
                Shot shot = enemyTank.shots.get(j);
                //判断 shot 是否击中我的坦克
                if (my_tank.isLive && shot.isLive) {
                    hitEnemy(shot, my_tank);
                }
            }
        }
    }
}
