package cn.whxy.start;

import javax.swing.*;
import java.awt.*;

public class Sky extends JPanel implements Runnable{
    // 定义星星对象
//    Star star = null;
    // 定义星星对象数组
    Star[] stars = null;
    Moon moon = null;
    // 构造方法: 设置画布属性
    public Sky(){
        // 设置画布大小
        this.setSize(600,800);
        // 创建星星对象
//        star = new Star();
        // 创建星星对象数组
        stars = new Star[50];
        moon = new Moon();
        // 循环操作数组, 实例化每一个星星对象
        for(int x=0; x<stars.length; x++){
            stars[x] = new Star();
        }
        //启动当前线程类
        new Thread(this).start();
    }

    // 重写paint()方法: 画方法
    // Graphics: 画笔类 g:画笔对象
    public void paint(Graphics g){
        // 设置画笔颜色
        g.setColor(Color.black);

        // 填充矩形区域
        g.fillRect(0, 0, 600, 800);

        // 画星星
//        star.draw(g);
        // 遍历数组, 画星星
        for(int x = 0;x < stars.length; x++){
            stars[x].draw(g);
        }

        moon.draw(g);

        // 画笔销毁
        g.dispose();
    }
    // 重写线程体run(), 实现整个页面不断重绘
    @Override
    public void run() {
        while(true){
            this.repaint(); // 再次调用paint()方法
            try {
                Thread.sleep(40); // 每40毫秒重绘一次
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
