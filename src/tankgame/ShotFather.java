package tankgame;

/**
 * @author 王俊彪
 * @version 1.0
 */
public class ShotFather {
    private int x;//横坐标
    private int y;//纵坐标
    private int direct;//方向
    private int speed=20;//速度
    private boolean isLive=true;//是否存活

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

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
