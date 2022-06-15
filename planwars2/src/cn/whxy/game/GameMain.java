package cn.whxy.game;
import javax.swing.*;

public class GameMain extends JFrame {
    GameBG gameBG = null;
    byte[] b = new byte[1024];
    int num = 0;

    public GameMain() throws Exception {
        // 创建对象
        gameBG = new GameBG();
        this.addKeyListener(gameBG.mk1);
        //添加背景到窗口
        this.add(gameBG);
        this.setTitle("PlaneGame"); // 窗口标题
        this.setSize(480, 640); // 窗口大小
        this.setLocation(400, 100); // 窗口在屏幕上的显示位置
        this.setResizable(false); // 窗口大小不可改变
        this.setVisible(true); // 窗口可见
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws Exception {
        new GameMain();
    }
}
