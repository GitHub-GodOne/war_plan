package cn.whxy.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Effect implements Runnable{
    // 声明爆炸效果图片
    BufferedImage[] effPics = null;
    int x, y;
    int index;
    boolean isLife=true;
    public Effect() {
        // 创建数组
        effPics = new BufferedImage[7];
        //导入图片
        for(int x=0; x<effPics.length;x++){
            try {
                effPics[x] = ImageIO.read(new FileInputStream("./pic/bz/0" + (x+1) + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 设置坐标
        x = 100;
        y = 100;
    }

    //自定义方法
    public void draw(Graphics g){
        g.drawImage(effPics[index], x, y, null);
    }

    @Override
    public void run() {
        while(isLife){
            try{
                Thread.sleep(50);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            index++;
            if(index == effPics.length - 1){
                break;
            }
        }
        isLife = false;
    }
}
