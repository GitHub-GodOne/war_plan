package cn.whxy.game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;


public class SingletoneShell {
    // 声明图片
    private static volatile BufferedImage[] eShellPic=new BufferedImage[10];

    public static BufferedImage getInstance(int index){
        if(eShellPic[index]==null){
            synchronized (SingletoneShell.class){
                if(eShellPic[index]==null){
                    try {
                        System.out.println("只执行一次炮弹!!!");
//                        eShellPic = new BufferedImage[10];
                        eShellPic[0] = ImageIO.read(new FileInputStream("./pic/8X8ZD1.png"));
                        eShellPic[1] = ImageIO.read(new FileInputStream("./pic/8X8ZD2.png"));
                        eShellPic[2] = ImageIO.read(new FileInputStream("./pic/16X16ZD1.png"));
                        eShellPic[3] = ImageIO.read(new FileInputStream("./pic/16X16ZD2.png"));
                        eShellPic[4] = ImageIO.read(new FileInputStream("./pic/16X16ZD3.png"));
                        eShellPic[5] = ImageIO.read(new FileInputStream("./pic/16X16ZD4.png"));
                        eShellPic[6] = ImageIO.read(new FileInputStream("./pic/16X25ZD7.png"));
                        eShellPic[7] = ImageIO.read(new FileInputStream("./pic/fire0.png"));
                        eShellPic[8] = ImageIO.read(new FileInputStream("./pic/fire1.png"));
                        eShellPic[9] = ImageIO.read(new FileInputStream("./pic/fire3.png"));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return eShellPic[index];
    }
}