package cn.whxy.start;

import javax.swing.*;

/*
*  程序人口
* */
public class GameMain extends JFrame {

    // 定义天空类对象
    Sky sky = null;
    Moon moon = null;

    public GameMain() {
        // 创建天空类
        sky = new Sky();

        // 创建顶层窗口类
        this.add(sky);

        // 设置标题
        this.setTitle("满天星");

        // 设置窗口大小
        this.setSize(600,800);

        // 设置窗口在屏幕中的显示位置
        this.setLocation(300,100);

        // 设置窗口可见
        this.setVisible(true);

        // 设置窗口大小不可变
        this.setResizable(false);

        // 设置窗口关闭, 程序退出
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new GameMain();
    }
}
