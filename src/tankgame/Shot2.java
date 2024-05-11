package tankgame;

import java.util.Vector;

/**
 * @author 王俊彪
 * @version 1.0
 */
public class Shot2 extends ShotFather implements Runnable{
    private int x;//横坐标
    private int y;//纵坐标
    private int direct;//方向
    private int speed=10;//速度
    private boolean isLive=true;//是否存活
    private static Vector<Bomb> bombs = null;
    public Shot2(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    public static void setBombs(Vector<Bomb> bombs) {
        Shot2.bombs = bombs;
    }

    @Override
    public void run() {
        while(this.isLive){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (direct){
                case 0://上
                    y-=speed;
                    break;
                case 1://右
                    x+=speed;
                    break;
                case 2://下
                    y+=speed;
                    break;
                case 3://左
                    x-=speed;
                    break;
                default:
                    System.out.println("方向有错误");
            }
//            g.fillRect(200, 350, 550, 50);
//            System.out.println("子弹的坐标："+x+"，"+y);
            if(!(x>0&&x<1000&&y>0&&y<750&&isLive)){
                isLive=false;
            }
            if(x>200&&x<750&&y>350&&y<400){
                bombs.add(new Bomb(getX(),getY()-100));
                isLive=false;
            }
        }
//        System.out.println(Thread.currentThread().getName()+"子弹线程结束 ");
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
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

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
