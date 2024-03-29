package Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
 * @title: Panel
 * @package Tank
 * @description:
 * @author: kashimashino
 * @date: 2021-06-11 上午7:27
 * @version: V2.0
 */


@SuppressWarnings({"all"})

public class Panel extends JPanel implements KeyListener, Runnable {
    //定义我方坦克
    MyTank myTank = null;
    //定义敌方坦克,放进Vector保存
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义爆炸数，存进Vector
    Vector<Boom> booms = new Vector<>();
    int enemyTank_nums = 8;  //敌人的坦克数量
    Vector<EnemyInfo> enemyInfos = new Vector<>();
    //定义爆炸的三张图片
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public Panel(String option) {
      //验证是否存在该文件
        File file = new File(Records.getRecordFilePath());
        if (file.exists()) {
            //同步上次的坦克数量及坐标
            enemyInfos = Records.getEnemyInfos();
            Records.setAllEnemyTank(enemyTanks);
        } else {
            System.out.println("文件不存在，只能开启新的游戏");
            option = "1";
        }

        //初始化一个我方坦克
        myTank = new MyTank(900, 550);

        //选择新游戏还是继续上局
        switch (option) {
            case "1":   //新游戏
                Records.setAllEnemyTankNum(0);
                //初始化敌方坦克
                for (int i = 0; i < enemyTank_nums; i++) {
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
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
                break;
            case "2":   //继续上局
                //初始化敌人坦克
                for (int i = 0; i < enemyInfos.size(); i++) {
                    EnemyInfo enemyInfo = enemyInfos.get(i);
                    //创建一个敌人的坦克
                    EnemyTank enemyTank = new EnemyTank(enemyInfo.getX(), enemyInfo.getY());
                    //将enemyTanks 设置给 enemyTank !!!
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置方向
                    enemyTank.setDirection(enemyInfo.getDirection());
                    //启动敌人坦克线程，让他动起来
                    new Thread(enemyTank).start();
                    //给该enemyTank 加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
                    //加入enemyTank的Vector 成员
                    enemyTank.shots.add(shot);
                    //启动 shot
                    new Thread(shot).start();
                    //敌方坦克加入进Vector
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("你的输入有误...");
        }

        //三张图片，来显示爆炸效果
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/boom1.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/boom2.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/boom3.png"));

        new AePlayWave("/home/archLinux/IdeaProjects/TankWar/Tank_war/src/111.wav").start();
    }

    /**
     *@title: showInfo
     *@author: archLinux
     *@date: 2021/6/11 上午7:58
     *@param: [g]
     *@return: void
     *显示我方击毁敌方坦克的信息
     */
    public void showInfo(Graphics g) {
        //右侧的画板
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("您累积击毁敌方坦克", 1020, 30);
        create_tank(1020, 60, g, 0, 0);//画出一个敌方坦克
        g.setColor(Color.BLACK);//这里需要重新设置成黑色
        //画出玩家的总成绩
        g.drawString(Records.getAllEnemyTankNum() + "", 1080, 100);

    }
    /**
     *@title: run
     *@author: archLinux
     *@date: 2021/6/11 上午7:46
     *@param: []
     *@return: void
     *刷新线程的动作
     */
    @Override
    public void run() {
        while (true) {
            //每隔100ms刷新一次
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

    /**
     *@title: hitEnemyTank
     *@author: archLinux
     *@date: 2021/6/11 上午7:48
     *@param: []
     *@return: void
     *我方发射子弹摧毁敌方
     */
    public void hitEnemyTank() {
//        //遍历我们的子弹
//        for(int j = 0;j < my_tank.shots.size();j++) {
//            Shot shot = my_tank.shots.get(j);
//            //判断是否击中了敌人坦克
//            if (shot != null && my_tank.shot.isLive) {//当我的子弹还存活
//                //遍历敌人所有的坦克
//                for (int i = 0; i < enemyTanks.size(); i++) {
//                    EnemyTank enemyTank = enemyTanks.get(i);
//                    hitEnemy(my_tank.shot, enemyTank);
//                }
//
//            }
//        }
        //单颗子弹。
        if (myTank.shot != null && myTank.shot.isLive) {//当我的子弹还存活
            //遍历敌人所有的坦克
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                hitEnemy(myTank.shot, enemyTank);
            }
        }
    }


    //用不上的函数
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }


    /**
     *@title: keyPressed
     *@author: archLinux
     *@date: 2021/6/11 上午7:50
     *@param: [e]
     *@return: void
     *监听按键的动作发生做出回应
     */
    @Override
    public void keyPressed(KeyEvent e) {
        //按下W/A/S/D按对应方向移动
        if (e.getKeyCode() == KeyEvent.VK_W) { //按下W键
            myTank.setDirection(0);   //向上
            if (myTank.getY() > 0) {
                myTank.moveUp();
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_D) { //按下D键
            myTank.setDirection(1);   //向右
            if (myTank.getX() + 60 < 1000) {

                myTank.moveRight();
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_S) {//按下S键
            myTank.setDirection(2);   //向下
            if (myTank.getY() + 60 < 750) {
                myTank.moveDown();
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_A) {//按下A键
            myTank.setDirection(3);   //向左
            if (myTank.getX() > 0) {
                myTank.moveLeft();
            }
        }

        //如果用户按下的是J,就发射
        if (e.getKeyCode() == KeyEvent.VK_J) {
            //发射单颗子弹
            if (myTank.shot == null || !myTank.shot.isLive) {
                myTank.shotEnemyTank();
            }
            //发射多颗子弹
//            my_tank.shotEnemyTank();

        }
        //更新窗口，让坦克动起来
        this.repaint();
    }
    /**
     *@title: paint
     *@author: archLinux
     *@date: 2021/6/11 上午7:52
     *@param: [g]
     *@return: void
     *画板，写出重绘内容
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //填充矩形
        g.fillRect(0, 0, 1000, 750);
        showInfo(g);
        //调用创建坦克方法生产自己的坦克
        if (myTank != null && myTank.isLive) {
            create_tank(myTank.getX(), myTank.getY(), g, myTank.getDirection(), 0);
        }
        //画出射击的子弹
        if (myTank.shot != null && myTank.shot.isLive == true) {
            g.draw3DRect(myTank.shot.x, myTank.shot.y, 5, 5, false);

        }
        //多颗子弹
//        //画出我方坦克射击的子弹
//        for (int i = 0; i < my_tank.shots.size(); i++) {
//            Shot shot = my_tank.shots.get(i);
//            if (shot != null && shot.isLive) {
//                g.draw3DRect(shot.x, shot.y, 5, 5, false);
//
//            } else {//如果该shot已经销毁 ,就从shots集合中拿掉
//                my_tank.shots.remove(shot);
//            }
//        }


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
     *@title: create_tank
     *@author: archLinux
     *@date: 2021/6/11 上午7:09
     *@param: [x, y, g, direction, type] x:横坐标 y:纵坐标 g:画笔 type:决定是我方坦克还是敌方坦克
     *@return: void
     *画出坦克
     */
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

    /**
     *@title: hitEnemy
     *@author: archLinux
     *@date: 2021/6/11 上午7:08
     *@param: [s, enemyTank]         s:子弹     enemyTank:我方或敌方坦克
     *@return: void
     *判断子弹是否击中敌人
     */

    public void hitEnemy(Shot s, Tank enemyTank) {
        switch (enemyTank.getDirection()) {
            //敌人坦克向上
            case 0:
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 40
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    enemyTanks.remove(enemyTank);
                    if (enemyTank instanceof EnemyTank) {
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
                    if (enemyTank instanceof EnemyTank) {
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
                    if (enemyTank instanceof EnemyTank) {
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
                    if (enemyTank instanceof EnemyTank) {
                        Records.addAllEnemyTankNum();
                    }
                    booms.add(new Boom(enemyTank.getX(), enemyTank.getY()));
                }
                break;
        }
    }

    /**
     *@title: hitMyTank
     *@author: archLinux
     *@date: 2021/6/11 上午7:07
     *@param: []
     *@return: void
     *判断敌人坦克是否击中我的坦克
     */
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
                if (myTank.isLive && shot.isLive) {
                    hitEnemy(shot, myTank);
                }
            }
        }
    }
}
