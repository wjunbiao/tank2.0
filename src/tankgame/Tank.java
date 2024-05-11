package tankgame;

import java.util.Vector;

/**
 * @author 王俊彪
 * @version 1.0
 */
public class Tank {
    private int x; //坦克的横坐标
    private int y; //坦克的纵坐标
    private  int speed=1;//坦克速度
    private int direct=0;//坦克方向
    private boolean isLive=true;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    static Vector<Tank> tanks = new Vector<>();

    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    public void setTanks(Vector<Tank> tanks) {
        tanks = tanks;
    }

    //碰撞
    public boolean crash(Tank enemyTank){
        //System.out.println(this+"  ,  "+enemyTank);
        switch (this.getDirect()) {
            case 0://上 自己坦克的方向
                if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {//敌人坦克的方向
                    if (getX() > enemyTank.getX() && getX() < enemyTank.getX() + 40
                            && getY() > enemyTank.getY() && getY() < enemyTank.getY() + 60
                            || getX() + 40 > enemyTank.getX() && getX() + 40 < enemyTank.getX() + 40
                            && getY() > enemyTank.getY() && getY() < enemyTank.getY() + 60
                    ) {
                        return true;
                    }
                }
                if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                    if (getX() > enemyTank.getX() && getX() < enemyTank.getX() + 60
                            && getY() > enemyTank.getY() && getY() < enemyTank.getY() + 40
                            || getX() + 40 > enemyTank.getX() && getX() + 40 < enemyTank.getX() + 60
                            && getY() > enemyTank.getY() && getY() < enemyTank.getY() + 40
                    ) {
                        return true;
                    }
                }
                break;
            case 1://右
                if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                    if (getX() + 60 > enemyTank.getX() && getX() + 60 < enemyTank.getX() + 40
                            && getY() > enemyTank.getY() && getY() < enemyTank.getY() + 60
                            || getX() + 60 > enemyTank.getX() && getX() + 60 < enemyTank.getX() + 40
                            && getY() + 40 > enemyTank.getY() && getY() + 40 < enemyTank.getY() + 60
                    ) {
                        return true;
                    }
                }
                if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3){
                    if (getX() + 60 > enemyTank.getX() && getX() + 60 < enemyTank.getX() + 60
                            && getY() > enemyTank.getY() && getY() < enemyTank.getY() + 40
                            || getX() + 60 > enemyTank.getX() && getX() + 60 < enemyTank.getX() + 60
                            && getY() + 40 > enemyTank.getY() && getY() + 40 < enemyTank.getY() + 40
                    ) {
                        return true;
                    }
                }
                break;
            case 2://下
                if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                    if (getX() > enemyTank.getX() && getX() < enemyTank.getX() + 40
                            && getY() + 60 > enemyTank.getY() && getY() + 60 < enemyTank.getY() + 60
                            || getX() + 40 > enemyTank.getX() && getX() + 40 < enemyTank.getX() + 40
                            && getY() + 60 > enemyTank.getY() && getY() + 60 < enemyTank.getY() + 60
                    ) {
                        return true;
                    }
                }
                if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                    if (getX() > enemyTank.getX() && getX() < enemyTank.getX() + 60
                            && getY() + 60 > enemyTank.getY() && getY() + 60 < enemyTank.getY() + 40
                            || getX() + 40 > enemyTank.getX() && getX() + 40 < enemyTank.getX() + 60
                            && getY() + 60 > enemyTank.getY() && getY() + 60 < enemyTank.getY() + 40
                    ) {
                        return true;
                    }
                }
                break;
            case 3://左
                if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                    if (getX() > enemyTank.getX() && getX() < enemyTank.getX() + 40
                            && getY() > enemyTank.getY() && getY() < enemyTank.getY() + 60
                            || getX() > enemyTank.getX() && getX() < enemyTank.getX() + 40
                            && getY() + 40 > enemyTank.getY() && getY() + 40 < enemyTank.getY() + 60
                    ) {
                        return true;
                    }
                }
                if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                    if (getX() > enemyTank.getX() && getX() < enemyTank.getX() + 60
                            && getY() > enemyTank.getY() && getY() < enemyTank.getY() + 40
                            || getX() > enemyTank.getX() && getX() < enemyTank.getX() + 60
                            && getY() + 40 > enemyTank.getY() && getY() + 40 < enemyTank.getY() + 40
                    ) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }
    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isTouchEnemyTank() {
        boolean loop =false;
        //当前坦克的方向
//        System.out.println(tanks.size());
        for (int i = 0; i < tanks.size(); i++) {
            Tank enemyTank = tanks.get(i);
//            System.out.println(this+"  ////  "+ enemyTank + " , "+i);
            if (this != enemyTank) {

                loop=crash(enemyTank);
                if(loop){
                    return true;
                }
            }
        }
        return false;
    }

    public void move(int direct){
//        if( this instanceof Hero){
//            speed=15;//给自己坦克设置速度
//        }

        switch (direct){
            case 0:
                if(y>0&&!isTouchEnemyTank()){
                    if(!(x+40>=200&&x+40<=750&&y>=350&&y<=400)
                            &&!(x>=200&&x<=750&&y>=350&&y<=400)
                            && !(x+40>=200&&x+40<=750&&y>=250&&y<=260)
                            &&!(x>=200&&x<=750&&y>=250&&y<=260)){
                        y-=speed;
                    }else if((x==750||x+40==200)&&y>=350&&y<=400){
                        y-=speed;
                    }else if((x==750||x+40==200)&&y>=250 && y<=260){
                        y-=speed;

                    }
                }
                break;
            case 1:
                if(x+60<1000&&!isTouchEnemyTank()){
                    if(!(x+60>=200&&x+60<=750&&y+40>=350&&y+40<=400)
                            &&!(x+60>=200&&x+60<=750&&y>=350&&y<=400)
                            &&!(x+60>=200&&x+60<=750&&y+40>=250&&y+40<=260)
                            &&!(x+60>=200&&x+60<=750&&y>=250&&y<=260)
                            &&!(y<250&&y+40>260)){
                        x+=speed;
                    }else if((y+40==350||y==400)){
                        x+=speed;
                    }else if(!(y<250&&y+40>260&&x+60>=200&&x+60<=750)){
                        x+=speed;
                    }
                }
                break;
            case 2:
                if(y+60<750&&!isTouchEnemyTank()){
                    if(!(x+40>=200&&x+40<=750&&y+60>=350&&y+60<=400)
                            &&!(x>=200&&x<=750&&y+60>=350&&y+60<=400)
                            &&!(x+40>=200&&x+40<=750&&y+60>=250&&y+60<=260)
                            &&!(x>=200&&x<=750&&y+60>=250&&y+60<=260)) {
                        y += speed;
                    }else if((x==750||x+40==200)&&y+60>=350&&y+60<=400){
                        y+=speed;
                    }else if((x==750||x+40==200)&&y+60>=250&&y+60<=260){
                        y+=speed;
                    }
                }
                break;
            case 3:
                if(x>0&&!isTouchEnemyTank()){
                    if(!(x>=200&&x<=750&&y+40>=350&&y+40<=400)
                            &&!(x>=200&&x<=750&&y>=350&&y<=400)
                            &&!(x>=200&&x<=750&&y+40>=250&&y+40<=260)
                            &&!(x>=200&&x<=750&&y>=250&&y<=260)
                            &&!(y<250&&y+40>260)) {
                        x-=speed;
                    }else if((y+40==350||y==400)){
                        x-=speed;
                    }else if(!(y<250&&y+40>260&&x>=200&&x<=750)){
                        x-=speed;
                    }
                }
                break;
        }
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
}

