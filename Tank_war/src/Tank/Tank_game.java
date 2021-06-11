package Tank;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * main函数
 */
public class Tank_game extends JFrame {
    Panel panel = null;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        new Tank_game();
    }



    public Tank_game(){
        System.out.println("请输入你的选择：         1： 新游戏                2：继续上局");
        String option = scanner.next();

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
