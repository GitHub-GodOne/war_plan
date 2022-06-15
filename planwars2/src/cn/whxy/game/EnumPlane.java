package cn.whxy.game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public enum EnumPlane {
    one(0, ""), two(1, "");
    private int id;
    private BufferedImage ma;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BufferedImage getMa() {
        return ma;
    }

    public void setMa(BufferedImage ma) {
        this.ma = ma;
    }

    EnumPlane(int id, String ma) {
        this.id = id;
        try {
            if(id==1)
                this.ma = ImageIO.read(new FileInputStream("./pic/enemyPlane.png"));
            else{
                this.ma = ImageIO.read(new FileInputStream("./pic/plane.png"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static BufferedImage forEach(int index){
        for (EnumPlane a : EnumPlane.values()) {
            if (a.id==index)
                return a.getMa();
        }
        return null;
    }
}
