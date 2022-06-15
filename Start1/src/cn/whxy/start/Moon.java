package cn.whxy.start;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Moon implements Runnable{
    // 月亮图片
    BufferedImage moonPic = null;
    // 声明月亮坐标
    int x = 0, y = 0;

    public Moon() {

        try {
            moonPic = ImageIO.read(new FileInputStream("./pic/month.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        x = 0;
        y = 0;
        //启动当前线程类
        new Thread(this).start();
    }

    // 自定义月亮方法
    public void draw(Graphics g){
        g.drawImage(moonPic, x, y, null);
    }

    public void move(){
        if(x<500||y<500){
            x+=20;
            y = (int)(Math.sin(x)*100) + x;
            y+=20;
        }else{
            x=0;
            y=0;
        }
    }

    @Override
    public void run() {
        while(true){
            move();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
