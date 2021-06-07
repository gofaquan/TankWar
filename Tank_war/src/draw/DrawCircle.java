package draw;
import javax.swing.*;
import java.awt.*;
@SuppressWarnings({"all"})
//1.定义一个框架drawCircle()，继承 JFrame,显示对应的窗口
public class DrawCircle extends JFrame {
    //定义一个面板
    private Panel panel = null;
    public static void main(String[] args) {
            new DrawCircle();
    }

    public DrawCircle() {
         panel = new Panel();
        //把面板放进框架中
        this.add(panel);
        //设置窗口
        this.setSize(400,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
//2.定义一个面板Panel(), 继承 JPanel(),用来画图
class Panel extends  JPanel{
    @Override
    public void paint(Graphics g) {
        //调用父类的方法
        super.paint(g);
        g.drawOval(10,10,100,100);
     }
}