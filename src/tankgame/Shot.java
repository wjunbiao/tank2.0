package tankgame;

import java.util.Vector;

/**
 * @author 王俊彪
 * @version 1.0
 */
public class Shot extends ShotFather implements Runnable {
    private int x;//横坐标
    private int y;//纵坐标
    private int direct;//方向
    private int speed=20;//速度
    private boolean isLive=true;//是否存活
    private static Vector<Bomb> bombs=null;
    int xx;//真正子弹头的横坐标
    int yy;//真正子弹头的纵坐标
    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    public static void setBombs(Vector<Bomb> bombs) {
        Shot.bombs = bombs;
    }

    @Override
    public void run() {
        while(isLive){
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

//            System.out.println("子弹的坐标："+x+"，"+y);
            switch (direct){
                case 0:
                    xx=x+50;
                    yy=y;
                    break;
                case 1:
                     xx=x+80;
                    yy=y+50;
                    break;
                case 2:
                     xx=x+50;
                     yy=y+60;
                    break;
                case 3:
                     xx=x+20;
                     yy=y+50;
                    break;
            }
            if(!(xx>0&&xx<1000&&yy+30>0&&yy+30<750)&&isLive){
//                System.out.println(x+","+y);
                isLive=false;
            }
            if(xx>200&&xx<750&&yy>350&&yy<400){
                bombs.add(new Bomb(getX(),getY()));
                isLive=false;
            }
        }

        System.out.println(Thread.currentThread().getName()+ "子弹线程结束 ");
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
