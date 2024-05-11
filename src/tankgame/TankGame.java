package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @author 王俊彪
 * @version 1.0
 *坦克大战的绘图区域
 */

public class TankGame extends JFrame{//定义一个画框，这里就是窗口
    private MyPanel mp =null; //定义一个画板
    private String key;
    public static void main(String[] args) {
        new TankGame();
    }

    public TankGame()  {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入选择 1、新游戏 2、上一局游戏");
        key = scanner.next();
        mp= new MyPanel(key);//初始化画板
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);
//        this.setSize(1016,788);
        this.setSize(1300,950);
        //窗口可以mp 面板发生的键盘事件
        this.addKeyListener(mp);
        this.setVisible(true);//定义可视化
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//单击关闭按钮时，关闭窗口
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
            }
        });
    }
}

