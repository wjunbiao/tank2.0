package tankgame;

import java.util.Vector;

/**
 * @author 王俊彪
 * @version 1.0
 * 自己的坦克
 */
public class Hero extends Tank {
//    private Shot shot =null;
    Vector<Shot> shots = new Vector<>();
    private boolean isLive=true;
    Shot shot = null;
    public Shot getShot() {
        return shot;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public void shotEnemyTank(){
        switch (getDirect()){
            case 0://上
                shot=new Shot(getX()-30,getY()-100,0);
                break;
            case 1://右
                shot=new Shot(getX()+60,getY()-30,1);
                break;
            case 2://下
                shot=new Shot(getX()-30,getY()+60,2);
                break;
            case 3://左
                shot=new Shot(getX()-100,getY()-30,3);
                break;
        }
        shots.add(shot);
        Thread thread = new Thread(shot);
        thread.start();
    }


    public void setShot(Shot shot) {
        this.shot = shot;
    }

    public Hero(int x, int y) {
        super(x, y);
    }


}
