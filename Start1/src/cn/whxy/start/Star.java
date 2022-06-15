package cn.whxy.start;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

/*
*
* 星星类:实现线程, 重写线程体(改变图片对象数组的下标)
*       java中的线程类Thread类, 接口Runnable
*       常使用实现Runable接口:implements Runable
*           实现线程体:public void run() 方法
* */
public class Star implements Runnable{
    // 设置星星图片对象, 定义一张星星图片对象
//    BufferedImage starPic = null;

    // 定义图片对象的数组
    BufferedImage[] starPics = null;
    int index = 0, x, y;
    // 构造方法
    public Star(){
        // 创建图片对象数组
        starPics = new BufferedImage[17];

        try {
            for(int index=0; index < starPics.length; index++) {
                starPics[index] = ImageIO.read(new FileInputStream("./pic/star/star"+ index +".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //设置星星坐标值
        x = new Random().nextInt(750); //随机取值范围
        y = new Random().nextInt(900); //随机取值范围
        //启动当前线程类
        new Thread(this).start();
    }

    // 自定义画方法
    public void draw(Graphics g){
        // 画星星
        g.drawImage(starPics[index], x, y, null);
    }


    // 重写run()方法(线程体):改变对象数组的下标
    @Override
    public void run() {
        while(true){
            index++;
            if(index==17){
                index = 0;
            }
            //设置线程休眠
            try {
                Thread.sleep(500); //50毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
