package Tank;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;


/**
 * @title: Tank_game
 * @package Tank
 * @description:
 * @author: kashimashino
 * @date: 2021-06-11 上午6:22
 * @version: V1.0
*/
@SuppressWarnings({"all"})
public class TankGame extends JFrame {
    //初始化画板
    Panel panel = null;
    //读取玩家输入的数字决定新游戏还是继续上局
    static Scanner scanner = new Scanner(System.in);

    //启动游戏
    public static void main(String[] args) {
        new TankGame();
    }


    /**
     *@title: Tank_game
     *@author: archLinux
     *@date: 2021/6/11 上午8:11
     *@param: []
     *@return:
     *main函数的核心
     */
    public TankGame(){

        System.out.println("请输入你的选择：         1： 新游戏                2：继续上局");
        String option = scanner.next();

        //加入线程，用run方法不断重画让子弹和坦克动起来
        panel = new Panel(option);
        Thread thread = new Thread(panel);
        thread.start();
        this.add(panel);

        //窗口设置
        this.setSize(1300,950);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addKeyListener(panel);  //添加监听事件

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("你正在关闭窗口");
                Records.Save();
                System.exit(0);
            }
        });
    }
}
