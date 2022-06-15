package cn.whxy.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/*
* 4. 玩家飞机炮弹类Shell
* */
public class fire{
    // 声明炮弹图片
    BufferedImage shellPic = null;
    boolean isLife = true;
    // 声明用于判断炮弹飞行方向的三个
    boolean U = false, LU = false, RU = false;

    // 声明炮弹坐标
    int x,y;

    // 构造方法
    public fire(Plane plane) {
        try {
            shellPic = ImageIO.read(new FileInputStream("./pic/fire3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        x = plane.x + plane.planePic.getWidth()/2 - shellPic.getWidth()/2;
        y = plane.y - shellPic.getHeight() - 10;
//        new Thread(this).start();
    }

    // 自定义炮弹移动方法
    public void move(){
        if(U){
            y-=6;
        }
        else if(LU){ // 左上
            x-=5;
            y-=7;
        }
        else if(RU){
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

//    @Override
//    public void run() {
//        while(isLife){ //如果还活着
////            this.move(); //调用炮弹移动方法
//            try {
//                Thread.sleep(30);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("结束");
//    }

}
