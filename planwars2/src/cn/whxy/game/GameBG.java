package cn.whxy.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

public class GameBG extends JPanel implements Runnable{

    // 声明背景图片对象
    BufferedImage bgImg = null;
    Plane plane = null;
//    Plane plane2 = null;

    MyKeyListener mk1 = null;

    // 声明敌机集合对象
    ArrayList<EnemyPlane> ePlaneList = null;

    // 定义用于判断飞机移动方向的boolean值
    boolean U=false, D=false, L=false, R=false, H=false;
    volatile Boolean flag=false, P=true;
    //    enemy_airplane enemy_airplane = null;
    EnemyPlane ePlane = null;
    // 声明
    ArrayList<Effect> effList = null;
    FireDemo fire=null;


    // 构造方法
    public GameBG(){
        // 导入图片, 创建图片对象
        try {
            bgImg = ImageIO.read(new FileInputStream("./pic/bg1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 创建飞机对象
        plane = new Plane(bgImg);

//        plane2 = new Plane(bgImg);
//        plane2.x = plane2.x + 20;
//        plane2.y = plane2.x + 30;
        //
        effList = new ArrayList<>();

        // 创建键盘监听器
        mk1 = new MyKeyListener();

//        enemy_airplane = new enemy_airplane();
        ePlane = new EnemyPlane((int)(Math.random()*2));
        // 给当前的画布类添加键盘监听
        this.addKeyListener(mk1);

        // 创建炮弹对象
//        shell = new Shell(plane);
        ePlaneList = new ArrayList<>();

        fire = new FireDemo();
        // 启动线程
        new Thread(this).start();
        new Thread(new CreateEPlane()).start();
        new Thread(fire).start();
    }

    //创建内部类: 实现线程, 用于控制敌机的开关频率
    class WD implements Runnable{
        // 重写线程体
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            H=false;
            plane.isLife = true;
        }
    }


    // 重写画方法: paint(Graphics g)

    @Override
    public void paint(Graphics g) {
        // 画背景颜色
        g.drawImage(bgImg, 0, 0, bgImg.getWidth(), bgImg.getHeight(), null);

        if(plane.isLife || H){
            // 画飞机
            if(H&&P){
                System.out.println("========>" + P + flag);
                if(flag){
                    flag = false;
                    new Thread(()->{
                        int i=0;
                        while(i<30){
                            System.out.println("画出!!!" + P);
                            P=false;
//                            try{ TimeUnit.MILLISECONDS.sleep(50);} catch (InterruptedException e) { e.printStackTrace(); }
                            try {
                                sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            P=true;
                            try {
                                sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            i++;
                        }

                    }, "AA").start();

                }

            }else {
                plane.draw(g);
            }
            if(plane.isFire){
                fire.setX(plane.x + plane.planePic.getWidth()/2-fire.Image[0].getWidth()/2);
                fire.setY(plane.y - fire.Image[0].getHeight());
                fire.draw(g);
            }else{
                fire.isLife = false;
            }
//            System.out.println("飞机活着");
        }

        if(U && !D && !L && !R){ //向上移动
            plane.moveUp();
            // 遍历plane对象的炮弹集合, 画炮弹
//            this.set_false();
        }else if(!U && D && !L && !R){ //向下移动
            plane.moveDown();
//            this.set_false();
        }else if(!U && !D && L && !R){ //向左移动
            plane.moveLeft();
//            this.set_false();
        }else if(!U && !D && !L && R){ //向右移动
            plane.moveRight();
//            this.set_false();
        }else if(U && !D && L && !R){ //向左上移动
            plane.moveUp();
            plane.moveLeft();
//            this.set_false();
        }else if(U && !D && !L && R){ //向右上移动
            plane.moveUp();
            plane.moveRight();
//            this.set_false();
        }else if(!U && D && L && !R){ //向左下移动
            plane.moveDown();
            plane.moveLeft();
//            this.set_false();
        }else if(!U && D && !L && R){ //向右下移动
            plane.moveDown();
            plane.moveRight();
//            this.set_false();
        }


        // 画敌机
//        ePlane.draw(g);
        // 遍历敌机集合, 画敌机
        for (int x = 0; x < ePlaneList.size(); x++) {
            // 从集合中取出一个敌机对象
            EnemyPlane eP = ePlaneList.get(x);
            if(eP.isLife) {
                // 画敌机
                eP.draw(g);
                if (eP.isHitPlane(plane) && H==false) {
                    // 打中了, 爆炸效果
//                    System.out.println("敌机与玩家飞机碰撞了");
                    Effect ef = new Effect();
                    // 根据玩家飞机的坐标, 设置爆炸效果的坐标
                    ef.x = plane.x
                            + plane.planePic.getWidth()/2
                            - ef.effPics[3].getWidth()/2;
                    ef.y = plane.y
                            + plane.planePic.getHeight()/2
                            - ef.effPics[3].getHeight();
                    // 启动线程
                    new Thread(ef).start();
                    // 画爆炸效果图
//                    ef.isLife=false;
                    effList.add(ef);
                }
            }
//            }else{
//                ePlaneList.remove(eP);
//            }



            // 画敌机炮弹(遍历敌机炮弹集合)
            for (int index = 0; index < eP.eShellList.size(); index++) {
                EnemyShell eShell = eP.eShellList.get(index);
                if(eShell.isLife){
                    // 检测炮弹是否打中玩家飞机
                    if(eShell.isHitPlane(plane) && H == false){
                        // 打中了爆炸效果
                        Effect ef = new Effect();
                        // 根据玩家飞机的坐标, 设置爆炸效果的坐标
                        ef.x = plane.x
                                + plane.planePic.getWidth()/2
                                - ef.effPics[3].getWidth()/2;
                        ef.y = plane.y
                                + plane.planePic.getHeight()/2
                                - ef.effPics[3].getHeight();
                        // 启动线程
                        new Thread(ef).start();
                        // 画爆炸效果图
                        effList.add(ef);
                    }else{
                        eShell.draw(g);
                    }
                }else{
                    eP.eShellList.remove(eShell);
                }
            }
        }

        // 遍历plane对象的炮弹集合, 画炮弹
        for (int index = 0; index < plane.shellList.size(); index++) {
            Shell shell = plane.shellList.get(index);
              if(shell.isLife){
                shell.draw(g);
            }
            // 画炮弹
            for(int x = 0; x < ePlaneList.size(); x++){
                if(shell.isHitPlane(ePlaneList.get(x)) && H==false){
                    Effect ef = new Effect();
                    // 根据玩家飞机的坐标, 设置爆炸效果的坐标
                    ef.x = ePlaneList.get(x).x
                            + plane.planePic.getWidth()/2
                            - ef.effPics[3].getWidth()/2;
                    ef.y = ePlaneList.get(x).y
                            + plane.planePic.getHeight()/2
                            - ef.effPics[3].getHeight();
                    // 启动线程
                    new Thread(ef).start();
                    // 画爆炸效果图
                    effList.add(ef);
                }
                if(!shell.isLife){
                    plane.shellList.remove(shell);
                }
            }
        }

        // 遍历plane对象的炮弹集合, 画炮弹
//        for (int index = 0; index < plane.fireList.size(); index++) {
//            fire fire = plane.fireList.get(index);
//            if(fire.isLife){
//                fire.draw(g);
//            }else{
//                plane.fireList.remove(fire);
//
//            }
//        }


        for(int x = 0; x < effList.size(); x++){
            Effect ef = effList.get(x);
            // 画爆炸
            if(ef.isLife){
                ef.draw(g);
            }else{
                effList.remove(ef);
            }
        }


        // 销毁画笔
        g.dispose();
    }


    // 重写线程体, 实现画面重绘
    class CreateEPlane implements Runnable{
        // 重写线程体
        @Override
        public void run() {
            while(true){
                ePlane = new EnemyPlane((int)(Math.random()*2));
                ePlaneList.add(ePlane);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run() {
        while(true){
            this.repaint(); // 自动调用paint()方法, 重绘整个画面
            try {
                sleep(10); // 休眠20毫秒, 每秒50次
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    // 自定义内部类; 实现键盘监听
    class MyKeyListener extends KeyAdapter{
        // 重写键盘按下方法
        @Override
        public void keyPressed(KeyEvent e){
            System.out.println(e.getKeyCode());
            if(e.getKeyCode() == KeyEvent.VK_W){ //W键:上
                U = true;
            }
            if(e.getKeyCode() == KeyEvent.VK_A){ //A键:左
//                plane.moveLeft();
                L = true;
            }
            if(e.getKeyCode() == KeyEvent.VK_S){ //S键:下
                D = true;
            }
            if(e.getKeyCode() == KeyEvent.VK_D){ //D键:右
                R = true;
            }
            if(e.getKeyCode() == KeyEvent.VK_H){ //D键:复活
                H = true;
//                new Thread(()->{
//                    flag=true;
//                    new Thread(()->{
//                        try {
//                            TimeUnit.SECONDS.sleep(3);
//                        } catch (InterruptedException interruptedException) {
//                            interruptedException.printStackTrace();
//                        }
//                        flag=false;
//                    }, "B").start();
//                    while(flag){
//                        plane.isLife=true;
//                    }
//                }, "A").start();
                flag = true;
                new Thread(new WD()).start();
            }
            if(e.getKeyCode() == KeyEvent.VK_J){ //D键:开炮
                plane.isFire = true;
                fire.isLife=true;
            }
        }
        // 重写按键释放方法
        @Override
        public void keyReleased(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_W){ //W键:上
                U = false;
            }
            if(e.getKeyCode() == KeyEvent.VK_A){ //A键:左
                L = false;
            }
            if(e.getKeyCode() == KeyEvent.VK_S){ //S键:下
                D = false;
            }
            if(e.getKeyCode() == KeyEvent.VK_D){ //D键:右
                R = false;
            }
            if(e.getKeyCode() == KeyEvent.VK_J){ //J键:攻击
                plane.isFire = false;
                fire.isLife=false;
//                set_false();
            }
        }
    }
//
//    public void set_false(){
//        for (int index = 0; index < plane.fireList.size(); index++) {
//            fire fire = plane.fireList.get(index);
//            fire.isLife = false;
//        }
//    }
}
