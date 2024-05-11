package tankgame;

/**
 * @author 王俊彪
 * @version 1.0
 */
public class Bomb {
    private int x;
    private int y;
    private int life =20;
    private  boolean isLive=true;

    public void liveDown(){
        if(life>0){
            life--;
        }else{
            isLive=false;
        }
    }

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
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

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
