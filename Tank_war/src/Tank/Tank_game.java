package Tank;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * main函数
 */
public class Tank_game extends JFrame {
    Panel panel = null;
    public static void main(String[] args) {
        new Tank_game();
    }

    public Tank_game(){
        panel = new Panel();
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
