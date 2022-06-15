package cn.whxy.game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class SingletonePlane {
    // 声明图片
    private static volatile BufferedImage[] ePlanePic=new BufferedImage[2];
    public static BufferedImage getInstance(int index){
        if(ePlanePic[index]==null){
            synchronized (SingletonePlane.class){
                if(ePlanePic[index]==null){
                    try {
                        System.out.println("只执行一次飞机!!!");
//                        ePlanePic = ;
                        ePlanePic[0] = ImageIO.read(new FileInputStream("./pic/enemyPlane.png"));
                        ePlanePic[1] = ImageIO.read(new FileInputStream("./pic/plane.png"));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return ePlanePic[index];
    }
}
