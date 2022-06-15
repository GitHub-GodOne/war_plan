package cn.whxy.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Plane{
    BufferedImage bgPic = null;
    int x, y;
    BufferedImage planePic = null;
    Shell shell = null;
//    fire fire = null;

    // 声明炮弹集合对象
    ArrayList<Shell> shellList = null;
//    ArrayList<fire> fireList = null;

    // 声明boolean值: 判断飞机是否开炮
    boolean isFire = false;
    boolean isLife = true;
    boolean flag = true;
//    boolean isfire = false;

    public Plane(BufferedImage bgPic) {
        this.bgPic = bgPic;
        try {
            planePic = ImageIO.read(new FileInputStream("./pic/plane.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 设置坐标
        x = this.bgPic.getWidth()/2 - planePic.getWidth()/2;
        y = this.bgPic.getHeight() - planePic.getHeight();

        // 创建炮弹集合对象
        shellList = new ArrayList<>();
//        fireList = new ArrayList<>();
    }


    public void draw(Graphics g){
        g.drawImage(planePic, x, y, null);
        // 如果飞机还活着, 是否开火
        if(isLife && isFire && flag){
//            fire();
            flag=false;
            new Thread(new FireSpeed()).start();
        }
    }

    // 自定义飞机移动方法
    public void moveUp(){
        if(y>0)
            y-=5;
    }

    // 自定义飞机移动方法
    public void moveDown(){
        if(y<522){
            y+=5;
        }
    }

    // 自定义飞机移动方法
    public void moveLeft(){
        if(x>0)
            x-=4;
    }

    // 自定义飞机移动方法
    public void moveRight(){
        if(x<375){
            x+=4;
        }

    }

    // 自定义开炮方法
    public void fire(){
        shell = new Shell(this);
        shell.U = true;
        // 将炮弹对象存入一个集合
//        shell.x -= 50;
        shellList.add(shell);

//        fire = new fire(this);
//        fire.U = true;
//        fireList.add(fire);

        shell = new Shell(this);
        shell.LU = true;
//        shell.x -=50;
        // 将炮弹对象存入一个集合

        shellList.add(shell);

//        fire = new fire(this);
//        fire.LU = true;
//        fire.x -= 20;
//        fireList.add(fire);

        shell = new Shell(this);
        shell.RU = true;
        // 将炮弹对象存入一个集合
        shellList.add(shell);

//        fire = new fire(this);
//        fire.RU = true;
//        fire.x += 20;
//        fireList.add(fire);
    }
    // 自定义方法, 获取玩家飞机的矩形区域: 用于碰撞检测
    // 使用Rectangle类
    public Rectangle getRect(){
        return new Rectangle(x, y, planePic.getWidth(), planePic.getHeight());
    }

    //创建内部类: 实现线程, 用于控制敌机的开关频率
    class FireSpeed implements Runnable{
        // 重写线程体
        @Override
        public void run() {
            // 控制开火频率
            while(isFire){
                fire(); // 调用开炮方法
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            flag=true;
        }
    }
}
