package tankgame;

import java.util.Vector;

/**
 * @author 王俊彪
 * @version 1.0
 */
public class EnemyTank extends Tank implements Runnable {
    Vector<Shot2> shot2s = new Vector<>();
    private boolean isLive = true;
    private int num;

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public void transmit() {
        Shot2 shot2 = null;
        switch (this.getDirect()) {
            case 0://上
                shot2 = new Shot2(getX() + 15, getY(), 0);
                break;
            case 1://右
                shot2 = new Shot2(getX() + 60, getY() + 15, 1);
                break;
            case 2://下
                shot2 = new Shot2(getX() + 15, getY() + 60, 2);
                break;
            case 3://左
                shot2 = new Shot2(getX(), getY() + 15, 3);
                break;
        }
        shot2s.add(shot2);
        Thread thread = new Thread(shot2);
        thread.start();

    }

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    public void tran() {

            switch (num) {
                case 0://上
                    for (int i = 0; i < 30; i++) {

                            move(0);

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1://右
                    for (int i = 0; i < 30; i++) {

                            move(1);

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2://下
                    for (int i = 0; i < 30; i++) {

                            move(2);

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3://左
                    for (int i = 0; i < 30; i++) {

                            move(3);

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }



    }

    @Override
    public void run() {

        while (true) {
            num = (int) (Math.random() * 4);
            this.setDirect(num);
            if (isLive && shot2s.size() < 10) {
                transmit();//发射一颗子弹
                num = getDirect();
            }
            tran();
            if (!this.isLive) {
                break;
            }
        }
    }
}
