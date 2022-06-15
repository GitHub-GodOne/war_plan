package cn.whxy.game;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class FireDemo implements Runnable {
    BufferedImage Image[]=null;
    private int x, y, index=0;
    volatile boolean isLife=false;
    FireDemo(){
        Image = new BufferedImage[4];
        try {
            Image[0] = ImageIO.read(new FileInputStream("./pic/12.png"));
            Image[1] = ImageIO.read(new FileInputStream("./pic/13.png"));
            Image[2] = ImageIO.read(new FileInputStream("./pic/14.png"));
            Image[3] = ImageIO.read(new FileInputStream("./pic/15.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void draw(Graphics g){
        if(index < 4){
            g.drawImage(Image[index], x,y, null);
            g.drawImage(Image[index], x-20,y, null);
            g.drawImage(Image[index], x+20,y, null);
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            index++;
            if(index==4)
                index=0;
        }
    }
}
