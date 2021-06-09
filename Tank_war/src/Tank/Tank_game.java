package Tank;

import javax.swing.*;

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
        this.setSize(1000,750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addKeyListener(panel);  //添加监听事件
    }
}
