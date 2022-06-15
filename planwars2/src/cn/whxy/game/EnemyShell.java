package cn.whxy.game;

import java.awt.*;
import java.awt.image.BufferedImage;



public class EnemyShell implements Runnable{
    // 声明坐标
    int x,y;
    BufferedImage eShellPic = null;

    // 声明三个
    boolean D=false, LD=false, RD=false, isLife=true, isWD=false;
    public EnemyShell(EnemyPlane ePlane, int index) {
        eShellPic = SingletoneShell.getInstance(index);
        y = ePlane.y + ePlane.ePlanePic.getHeight() + 5;
        x = ePlane.x + ePlane.ePlanePic.getWidth()/2 - eShellPic.getWidth()/2;
        new Thread(this).start();
    }

    public void draw(Graphics g) {
        g.drawImage(eShellPic, x,y, null);
    }

    @Override
    public void run() {
        while(isLife){
            this.move();
            try{
                Thread.sleep(35);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void move() {
        if(D){
            y += 4;
        }else if(LD){
            x -=5;
            y += 5;
        }else if(RD){
            x += 5;
            y += 5;
        }
        if(y > 580 || x>450 || x<0 || y<0){
            isLife=false;
//            System.out.println("子弹销毁了.....");
        }
    }

    public Rectangle getRect(){
        return new Rectangle(x, y, eShellPic.getWidth(), eShellPic.getHeight());
    }

    // 碰撞检测方法: 检测敌机子弹与飞机子弹碰撞
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
