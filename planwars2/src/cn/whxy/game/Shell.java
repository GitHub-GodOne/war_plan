package cn.whxy.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/*
* 4. 玩家飞机炮弹类Shell
* */
public class Shell implements Runnable{
    // 声明炮弹图片
    BufferedImage shellPic = null;
    boolean isLife = true;
    // 声明用于判断炮弹飞行方向的三个
    boolean U = false, LU = false, RU = false, isWD=false;

    // 声明炮弹坐标
    int x,y;

    // 构造方法
    public Shell(Plane plane) {
        try {
            shellPic = ImageIO.read(new FileInputStream("./pic/8X8ZD1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        x = plane.x + plane.planePic.getWidth()/2 - shellPic.getWidth()/2;
        y = plane.y;
        new Thread(this).start();
    }

    // 自定义炮弹移动方法
    public void move(){
        System.out.println(U + " " + LU + " " + RU);
        if(U){
//            try{ TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) { e.printStackTrace(); }
//            System.out.println("上");
            y-=6;
        }
        if(LU){ // 左上
//            System.out.println("左上");
            x-=5;
            y-=7;
        }
        if(RU){
//            System.out.println("右上");
            x+=5;
            y-=7;
        }
        if(x < 0 || x > 480 || y < 0){
            // 设置炮弹死亡
            isLife = false;
        }
    }

    // 自定义画炮弹方法
    public void draw(Graphics g){
        g.drawImage(shellPic, x, y,null);
    }

    @Override
    public void run() {
        while(isLife){ //如果还活着
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.move(); //调用炮弹移动方法
        }
    }

    public Rectangle getRect(){
        return new Rectangle(x, y, shellPic.getWidth(), shellPic.getHeight());
    }

    // 碰撞检测方法: 检测子弹与飞机碰撞
    public boolean isHitPlane(EnemyPlane plane){
        if(this.isLife && plane.isLife && this.getRect().intersects(plane.getRect())){
            // 碰撞了, 都死掉了
            this.isLife = false;
            plane.isLife = false;
            return true;
        }
        return false;
    }
}
