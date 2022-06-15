package cn.whxy.game;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class EnemyPlane implements Runnable {
    //声明图片
    //声明坐标
    //构造方法
    //自定义方法:画敌机
    BufferedImage ePlanePic=null;
    boolean isLife = true;
//    boolean isfire = true;
    int x,y, index=0;
    EnemyShell eShell = null;
    boolean isWD = false;

    // 声明敌机炮弹集合对象
    ArrayList<EnemyShell> eShellList = null;
    public EnemyPlane(int index) {
//        ePlanePic = SingletonePlane.getInstance(index);
        System.out.println(EnumPlane.forEach(index));
        ePlanePic = EnumPlane.forEach(index);
        x=new Random().nextInt(410);
        y=-100;
        eShellList = new ArrayList<EnemyShell>();
        new Thread(this).start();
        new Thread(new FireSpeed()).start();
    }

    public void draw(Graphics g) {
        g.drawImage(ePlanePic, x,y, null);
    }

    public void move() {
        y+=4;
        if(y > 522){
            isLife=false;
        }
    }

    // 开炮方法
    public void fire(){
        int rand = (int)(Math.random()*10);
        // 创建炮弹方法
        eShell = new EnemyShell(this, rand);
        eShell.D = true;
        eShellList.add(eShell);

        // 创建炮弹方法
        eShell = new EnemyShell(this, rand);
        eShell.LD = true;
        eShellList.add(eShell);

        // 创建炮弹方法
        eShell = new EnemyShell(this, rand);
        eShell.RD = true;
        eShellList.add(eShell);
    }

    //创建内部类: 实现线程, 用于控制敌机的开关频率
    class FireSpeed implements Runnable{
        // 重写线程体
        @Override
        public void run() {
            // 控制开火频率
            while(isLife){
                fire(); // 调用开炮方法
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(isLife) {
            this.move(); // 调用移动方法
//            this.fire(); // 调用开炮方法
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    // 自定义方法, 获取敌机的矩形区域: 用于碰撞检测
    // 使用Rectangle类
    public Rectangle getRect(){
        return new Rectangle(x, y, ePlanePic.getWidth(), ePlanePic.getHeight());
    }

    // 碰撞检测方法: 检测敌机与友机碰撞
    public boolean isHitPlane(Plane plane){
        if(this.isLife && plane.isLife && this.getRect().intersects(plane.getRect())){
            // 碰撞了, 都死掉了
            this.isLife = false;
            plane.isLife = false;
            plane.isFire = false;
            return true;
        }
        return false;
    }
}
