package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * @author 王俊彪
 * @version 1.0
 */
//keyListener ：监听键盘的监听器
public class MyPanel extends JPanel implements KeyListener, Runnable {
    private static Hero hero = null;
    private int enemyTankNum = 8;
    private static Vector<EnemyTank> enemyTanks = new Vector<>();
    private static Vector<Tank> tanks = new Vector<>();
    private static Vector<Node> nodes =null;
    private static Vector<BlockNode> blockNodes=null;
    private static Vector<Bomb> bombs = new Vector<>();
    private static Vector<Block> blocks = new Vector<>();
    private Image image = null;
    private Image image1 = null;
    private Image image2 = null;
    private Image image3 = null;
    private Image image4 = null;

    //创建了个Vector 集合用于存放 爆炸集合
    public MyPanel(String key) {

        Recorder.setEnemyTanks(enemyTanks);//使Recorder中可以获取到敌人的坦克
        //判断记录文件是否存在，如果存在就继续执行，如果不存在就创建一个新文件
        File file = new File(Recorder.getRecordFile());
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        switch (key){
            case "1":
                hero = new Hero(500, 600);//初始化自己坦克
                hero.setSpeed(10);
                for (int i = 0; i < enemyTankNum; i++) {//初始化敌人坦克
                    EnemyTank enemyTank = new EnemyTank((i + 1) * 100, 100);
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirect((int)(Math.random()*4));//设置坦克方向
                    enemyTank.transmit();//初始化子弹
                    Thread thread = new Thread(enemyTank);
                    thread.start();
                    enemyTanks.add(enemyTank);
                    enemyTank.tanks.add(enemyTank);
                }
                for (int i = 0; i < 55; i++) {
                    blocks.add(new Block(200+i*10,250));//初始化10个方块
                }
                break;
            case"2":
                nodes=Recorder.getNodesAndEnemyTank();
                blockNodes=Recorder.getBlockNodes();
                if(nodes!=null){
                Node node=nodes.get(0);
                hero = new Hero(node.getX(),node.getY());
                hero.setSpeed(10);
                    for (int i = 1; i < nodes.size(); i++) {
                        node = nodes.get(i);
                        EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                        enemyTank.setDirect(node.getDirect());
                        enemyTank.setEnemyTanks(enemyTanks);
                        enemyTank.transmit();//初始化子弹
                        Thread thread = new Thread(enemyTank);
                        thread.start();
                        enemyTanks.add(enemyTank);
                        enemyTank.tanks.add(enemyTank);
                    }
                    BlockNode blockNode=null;
                    for (int i = 0; i < blockNodes.size(); i++) {
                         blockNode = blockNodes.get(i);
                        blocks.add(new Block(blockNode.getX(),blockNode.getY()));
                    }
                }else{
                    System.out.println("没有上局记录");
                    System.exit(0);
                }
                break;
            default:
                System.out.println("您输入的有误");
        }
        Recorder.setHero(hero);
        hero.tanks.add(hero);
        Recorder.setBlocks(blocks);
        //初始化爆炸图片
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
        image4 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bg.png"));
        //播放指定音乐
        new AePlayWave("src/111.wav").start();
        Shot2.setBombs(bombs);
        Shot.setBombs(bombs);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);
        g.setColor(Color.blue);
        g.fillRect(1000,0,300,200);
        {//绘制河流
            g.setFont(new Font("宋体",Font.BOLD,25));
            g.setColor(Color.MAGENTA);
            g.fillRect(200, 500, 550, 50);
            g.setColor(Color.black);
            g.drawString("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 200, 525);
            g.drawString("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 200, 550);
            g.drawString("河流", 650, 530);
        }
        //防止别的地方在画坦克就不好用了
        //绘制坦克-封装方法
       if(hero.isLive()){
           drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
       }
        showInfo(g);//显示成绩

        //敌人的坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive()) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);

            }
            //绘制敌人坦克的子弹
            for (int j = 0; j < enemyTank.shot2s.size(); j++) {
                Shot2 shot2 = enemyTank.shot2s.get(j);
                if (shot2.isLive()) {
                    g.setColor(Color.blue);
                    g.fillOval(shot2.getX(), shot2.getY(), 10, 10);
                } else {
                    enemyTank.shot2s.remove(shot2);
                }
            }
        }

        if (hero.shots.size() != 0) {//绘制自己坦克子弹
//            g.fillOval(hero.getShot().getX()+2,hero.getShot().getY(),10,10);
            drawZd(g);
        }

        //击中时出现爆炸效果
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.getLife() > 17) {
                g.drawImage(image1, bomb.getX(), bomb.getY(), 100, 100, this);
            } else if (bomb.getLife() > 14) {
                g.drawImage(image2, bomb.getX(), bomb.getY(), 100, 100, this);
            } else if (bomb.getLife() > 11) {
                g.drawImage(image3, bomb.getX(), bomb.getY(), 100, 100, this);
                if (bomb.getLife() == 12) {
                    int num;
                    if(!(bomb.getX()>=200&&bomb.getX()<=750&&bomb.getY()+100>=350&&bomb.getY()+100<=400)
                        &&!(bomb.getX()>=200&&bomb.getX()<=750&&bomb.getY()>=350&&bomb.getY()<=400)) {
                        num = (int) (Math.random() * 3 + 1);//随机出现马老师
                    }else{
                        num=3;//只要不是1就行
                    }

//                    System.out.println(num);
                    if (num != 1) {//出现1的时候出现马老师
                        bomb.setLife(1);
                    }
                }
            } else {
                g.drawImage(image4, bomb.getX(), bomb.getY(), 100, 100, this);
            }
            bomb.liveDown();
            if (bomb.getLife() == 0) {
                bombs.remove(bomb);
            }
        }
        //先加载一个子弹图片，不然当第一次使用的时候加载比较慢
        image = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/zd0.png"));
        g.drawImage(image, -100, -100, 100, 100, this);
        image = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/zd1.png"));
        g.drawImage(image, -100, -100, 100, 100, this);
        image = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/zd2.png"));
        g.drawImage(image, -100, -100, 100, 100, this);
        image = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/zd3.png"));
        g.drawImage(image, -100, -100, 100, 100, this);


        {//绘制钢板
            g.setColor(Color.white);
            g.fillRect(200, 350, 550, 50);
            g.setColor(Color.black);
            g.drawString("钢板", 650, 380);
        }
        {//绘制可以消失的障碍物
            for (int i = 0; i < blocks.size(); i++) {
                Block block = blocks.get(i);
                g.setColor(Color.green);
                g.fill3DRect(block.getX(),block.getY(),block.getWidth(),block.getHeight(),false);
            }
        }
        hitBlock();
    }
    //编写方法，判断敌人坦克是否击中了方块
    public static void hitBlock(){
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int k = 0; k < enemyTank.shot2s.size(); k++) {
                Shot2 shot2 = enemyTank.shot2s.get(k);
                for (int j = 0; j < blocks.size(); j++) {
                    Block block = blocks.get(j);
                    switch (enemyTank.getDirect())//判断坦克的方向
                    {
                        case 0:
                            if(       shot2.getX()+5>=block.getX()
                                    &&shot2.getX()+5<=block.getX()+10
                                    &&shot2.getY()>=block.getY()
                                    &&shot2.getY()<=block.getY()+10)
                            {
                                enemyTank.shot2s.remove(shot2);
                                bombs.add(new Bomb(block.getX()-50,block.getY()-50));
                                blocks.remove(block);
                            }
                            break;
                        case 1:
                            if(       shot2.getX()+10>=block.getX()
                                    &&shot2.getX()+10<=block.getX()+10
                                    &&shot2.getY()+5>=block.getY()
                                    &&shot2.getY()+5<=block.getY()+10)
                            {
                                enemyTank.shot2s.remove(shot2);
                                bombs.add(new Bomb(block.getX()-50,block.getY()-50));
                                blocks.remove(block);
                            }
                            break;
                        case 2:
                            if(       shot2.getX()+5>=block.getX()
                                    &&shot2.getX()+5<=block.getX()+10
                                    &&shot2.getY()+10>=block.getY()
                                    &&shot2.getY()+10<=block.getY()+10)
                            {
                                enemyTank.shot2s.remove(shot2);
                                bombs.add(new Bomb(block.getX()-50,block.getY()-50));
                                blocks.remove(block);
                            }
                            break;
                        case 3:

                            if(       shot2.getX()>=block.getX()
                                    &&shot2.getX()<=block.getX()+10
                                    &&shot2.getY()+5>=block.getY()
                                    &&shot2.getY()+5<=block.getY()+10)
                            {
                                enemyTank.shot2s.remove(shot2);
                                bombs.add(new Bomb(block.getX()-50,block.getY()-50));
                                blocks.remove(block);
                            }
                            break;

                    }

                }
            }

        }
    }
    //编写方法，显示我击毁敌人坦克的数量
    public void showInfo(Graphics g){
        g.setColor(Color.black);
        g.setFont(new Font("宋体",Font.BOLD,25));
        g.drawString("您累计击毁敌人坦克：",1020,30);
        drawTank(1020, 50, g, 0, 0);
        g.setColor(Color.black);
        g.drawString(Recorder.getAllEnemyTankNum()+"",1080,100);
    }

    //编写方法，判断我方子弹是否击中敌人坦克
    public static boolean hitTank(Shot shot, EnemyTank enemyTank) {
        switch (enemyTank.getDirect()) {
            case 0:
            case 2:
                switch (shot.getDirect()) {
                    case 0:
                        if (shot.getX() + 50 >= enemyTank.getX() && shot.getX() + 50 <= enemyTank.getX() + 40
                                && shot.getY() >= enemyTank.getY() && shot.getY() <= enemyTank.getY() + 60
                                || shot.getX() + 50 >=enemyTank.getX()&& shot.getX() + 50 <= enemyTank.getX() + 40
                                && shot.getY()+120 > enemyTank.getY()+60
                                && shot.getY() < enemyTank.getY()
                        ) {
                            enemyTank.setLive(false);
                            shot.setLive(false);
                            hero.shots.remove(shot);
                            Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                            bombs.add(bomb);
                            Tank.tanks.remove(enemyTank);
                            Recorder.addAllEnemyTankNum();
                            return true;
                        }
                        break;
                    case 1:
                        if (shot.getX() + 100 >= enemyTank.getX() && shot.getX() + 100 <= enemyTank.getX() + 40
                                && shot.getY() + 50 >= enemyTank.getY() && shot.getY() + 50 <= enemyTank.getY() + 60
                                || shot.getY() + 50 >= enemyTank.getY() && shot.getY() + 50 <= enemyTank.getY() + 60
                                && shot.getX()+100 > enemyTank.getX()
                                && shot.getX()-20< enemyTank.getX()) {
                            enemyTank.setLive(false);
                            shot.setLive(false);
                            hero.shots.remove(shot);
                            Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                            bombs.add(bomb);
                            Tank.tanks.remove(enemyTank);
                            Recorder.addAllEnemyTankNum();
                            return true;
                        }
                        break;
                    case 2:
                        if (shot.getX() + 50 >= enemyTank.getX() && shot.getX() + 50 <= enemyTank.getX() + 40
                                && shot.getY() + 100 >= enemyTank.getY() && shot.getY() + 100 <= enemyTank.getY() + 60
                                || shot.getX() + 50 >=enemyTank.getX()&& shot.getX() + 50 <= enemyTank.getX() + 40
                                && shot.getY()+100 > enemyTank.getY()+60
                                && shot.getY()-20 < enemyTank.getY()) {
                            enemyTank.setLive(false);
                            shot.setLive(false);
                            hero.shots.remove(shot);
                            Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                            bombs.add(bomb);
                            Tank.tanks.remove(enemyTank);
                            Recorder.addAllEnemyTankNum();
                            return true;
                        }
                        break;
                    case 3:
                        if (shot.getX() >= enemyTank.getX() && shot.getX() <= enemyTank.getX() + 40
                                && shot.getY() + 50 >= enemyTank.getY() && shot.getY() + 50 <= enemyTank.getY() + 60
                                || shot.getY() + 50 >= enemyTank.getY() && shot.getY() + 50 <= enemyTank.getY() + 60
                                && shot.getX()+120 > enemyTank.getX()+40
                                && shot.getX() < enemyTank.getX()) {
                            enemyTank.setLive(false);
                            shot.setLive(false);
                            hero.shots.remove(shot);
                            Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                            bombs.add(bomb);
                            Tank.tanks.remove(enemyTank);
                            Recorder.addAllEnemyTankNum();
                            return true;
                        }
                        break;
                }
                break;
            case 1:
            case 3:
                switch (shot.getDirect()) {
                    case 0:
                        if (shot.getX() + 50 >= enemyTank.getX() && shot.getX() + 50 <= enemyTank.getX() + 60
                                && shot.getY() >= enemyTank.getY() && shot.getY() <= enemyTank.getY() + 40
                                || shot.getX() + 50 >=enemyTank.getX()&& shot.getX() + 50 <= enemyTank.getX() + 60
                                && shot.getY()+120 > enemyTank.getY()+40
                                && shot.getY() < enemyTank.getY()) {
                            enemyTank.setLive(false);
                            shot.setLive(false);
                            hero.shots.remove(shot);
                            Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                            bombs.add(bomb);
                            Tank.tanks.remove(enemyTank);
                            Recorder.addAllEnemyTankNum();
                            return true;
                        }
                        break;
                    case 1:
                        if (shot.getX() + 100 >= enemyTank.getX() && shot.getX() + 100 <= enemyTank.getX() + 60
                                && shot.getY() + 50 >= enemyTank.getY() && shot.getY() + 50 <= enemyTank.getY() + 40
                                || shot.getY() + 50 >= enemyTank.getY() && shot.getY() + 50 <= enemyTank.getY() + 40
                                && shot.getX()+100 > enemyTank.getX()
                                && shot.getX()-20< enemyTank.getX()) {
                            enemyTank.setLive(false);
                            shot.setLive(false);
                            hero.shots.remove(shot);
                            Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                            bombs.add(bomb);
                            Tank.tanks.remove(enemyTank);
                            Recorder.addAllEnemyTankNum();
                            return true;
                        }
                        break;
                    case 2:
                        if (shot.getX() + 50 >= enemyTank.getX() && shot.getX() + 50 <= enemyTank.getX() + 60
                                && shot.getY() + 100 >= enemyTank.getY() && shot.getY() + 100 <= enemyTank.getY() + 40
                                || shot.getX() + 50 >=enemyTank.getX()&& shot.getX() + 50 <= enemyTank.getX() + 60
                                && shot.getY()+100 > enemyTank.getY()+40
                                && shot.getY()-20 < enemyTank.getY()) {
                            enemyTank.setLive(false);
                            shot.setLive(false);
                            hero.shots.remove(shot);
                            Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                            bombs.add(bomb);
                            Tank.tanks.remove(enemyTank);
                            Recorder.addAllEnemyTankNum();
                            return true;
                        }
                        break;
                    case 3:
                        if (shot.getX() >= enemyTank.getX() && shot.getX() <= enemyTank.getX() + 60
                                && shot.getY() + 50 >= enemyTank.getY() && shot.getY() + 50 <= enemyTank.getY() + 40
                                || shot.getY() + 50 >= enemyTank.getY() && shot.getY() + 50 <= enemyTank.getY() + 40
                                && shot.getX()+120 > enemyTank.getX()+40
                                && shot.getX() < enemyTank.getX()) {
                            enemyTank.setLive(false);
                            shot.setLive(false);
                            hero.shots.remove(shot);
                            Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                            bombs.add(bomb);
                            Tank.tanks.remove(enemyTank);
                            Recorder.addAllEnemyTankNum();
                            return true;
                        }
                        break;
                }
                break;
        }
        return false;
    }


    public void drawZd(Graphics g) {
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);

            if (shot.isLive()) {
                switch (shot.getDirect()) {
                    case 0:
                        image = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/zd0.png"));
                        g.drawImage(image, shot.getX(), shot.getY(), 100, 100, this);
                        break;
                    case 1:
                        image = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/zd1.png"));
                        g.drawImage(image, shot.getX(), shot.getY(), 100, 100, this);
                        break;
                    case 2:
                        image = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/zd2.png"));
                        g.drawImage(image, shot.getX(), shot.getY(), 100, 100, this);
                        break;
                    case 3:
                        image = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/zd3.png"));
                        g.drawImage(image, shot.getX(), shot.getY(), 100, 100, this);
                        break;
                }
            } else {
                hero.shots.remove(shot);
            }
        }
    }


    //public void drawHero (Graphics g,Hero hero)不如下面的这种方式，下面这种可以画各种各样的坦克
    //不止画自己的坦克，而且可以改变方向
    //如果不考虑全面属性以后画坦克就显的笨拙了

    /**
     * //输入/**回车快捷键表示属性
     *
     * @param x      坦克的左上角横坐标
     * @param y      坦克的左上角纵坐标
     * @param g      笔画
     * @param direct 坦克方向
     * @param type   坦克类型（0：自己坦克，1：敌人坦克）
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        //根据不同类型的坦克，设置不同颜色
        switch (type) {
            case 0://自己坦克
                g.setColor(Color.CYAN);
                break;
            case 1://敌人坦克
                g.setColor(Color.green);
                break;
            default:
                System.out.println("暂不处理");
        }
        //根据坦克方向，来绘制坦克
        switch (direct) {
            case 0://上
                g.fillRect(x, y, 10, 60);//画出坦克左边的轮子
                g.fillRect(x + 30, y, 10, 60);//画出坦克右边的轮子
                g.fillRect(x + 10, y + 10, 20, 40);//画出坦克中间的矩形
                g.setColor(Color.white);//设置中间盖子，和炮管的颜色
                g.fillOval(x + 10, y + 20, 20, 20);//画出坦克的盖子
                g.drawLine(x + 20, y + 30, x + 20, y);//画出坦克的炮管
                break;
            case 1://右
                g.fillRect(x, y, 60, 10);//画出坦克左边的轮子
                g.fillRect(x, y + 30, 60, 10);//画出坦克右边的轮子
                g.fillRect(x + 10, y + 10, 40, 20);//画出坦克中间的矩形
                g.setColor(Color.white);//设置中间盖子，和炮管的颜色
                g.fillOval(x + 20, y + 10, 20, 20);//画出坦克的盖子
                g.drawLine(x + 30, y + 20, x + 60, y + 20);//画出坦克的炮管
                break;
            case 2://下
                g.fillRect(x, y, 10, 60);//画出坦克左边的轮子
                g.fillRect(x + 30, y, 10, 60);//画出坦克右边的轮子
                g.fillRect(x + 10, y + 10, 20, 40);//画出坦克中间的矩形
                g.setColor(Color.white);//设置中间盖子，和炮管的颜色
                g.fillOval(x + 10, y + 20, 20, 20);//画出坦克的盖子
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//画出坦克的炮管
                break;
            case 3://左
                g.fillRect(x, y, 60, 10);//画出坦克左边的轮子
                g.fillRect(x, y + 30, 60, 10);//画出坦克右边的轮子
                g.fillRect(x + 10, y + 10, 40, 20);//画出坦克中间的矩形
                g.setColor(Color.white);//设置中间盖子，和炮管的颜色
                g.fillOval(x + 20, y + 10, 20, 20);//画出坦克的盖子
                g.drawLine(x + 30, y + 20, x, y + 20);//画出坦克的炮管
                break;
            default:
                System.out.println("方向错误");
        }
    }

    //当键盘输入字符时触发
    @Override
    public void keyTyped(KeyEvent e) {

    }

    //当键盘按下时触发
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirect(0);
            hero.move(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirect(1);
            hero.move(1);
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirect(2);
            hero.move(2);
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirect(3);
            hero.move(3);
        }
        if (e.getKeyCode() == KeyEvent.VK_J) {
            if(hero.shots.size()<5){
                hero.shotEnemyTank();

            }
        }
        this.repaint();


    }

    //当键盘释放时触发
    @Override
    public void keyReleased(KeyEvent e) {

    }
    public void hitEnemyTank(){
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < hero.shots.size(); j++) {
                Shot shot = hero.shots.get(j);
                if (hitTank(shot, enemyTank)) {
                    enemyTanks.remove(i);
                }
            }
        }
    }
    public static void hitBomb( Shot2 shot2){
            bombs.add(new Bomb(hero.getX() - 20, hero.getY() - 20));
//          System.out.println(hero.getX()+","+hero.getY());
            shot2.setLive(false);
//          hero.setLive(false);
            enemyTanks.remove(shot2);

    }
    //编写方法判断敌人坦克子弹击中我们
    public static void hitHero(){
        if(hero.isLive()) {
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                for (int j = 0; j < enemyTank.shot2s.size(); j++) {
                    Shot2 shot2 = enemyTank.shot2s.get(j);
                    switch (hero.getDirect()) {
                        case 0:
                        case 2:
                            switch (shot2.getDirect()){
                                case 0:
                                    if (shot2.getX() > hero.getX() && shot2.getX() < hero.getX() + 40
                                            && shot2.getY() > hero.getY() && shot2.getY() < hero.getY() + 60) {
                                        hitBomb(shot2);
                                    }
                                case 1:
                                    if (shot2.getX() > hero.getX() && shot2.getX() < hero.getX() + 40
                                            && shot2.getY() > hero.getY() && shot2.getY() < hero.getY() + 60) {
                                        hitBomb(shot2);
                                    }
                                case 2:
                                    if (shot2.getX()+10 > hero.getX() && shot2.getX()+10 < hero.getX() + 40
                                            && shot2.getY()+10 > hero.getY() && shot2.getY()+10 < hero.getY() + 60) {
                                        hitBomb(shot2);
                                    }
                                case 3:
                                    if (shot2.getX()+10 > hero.getX() && shot2.getX()+10 < hero.getX() + 40
                                            && shot2.getY() > hero.getY() && shot2.getY() < hero.getY() + 60) {
                                        hitBomb(shot2);
                                    }
                            }
                            break;
                        case 1:
                        case 3:
                            switch (shot2.getDirect()){
                                case 0:
                                    if (shot2.getX() > hero.getX() && shot2.getX() < hero.getX() + 60
                                            && shot2.getY() > hero.getY() && shot2.getY() < hero.getY() + 40) {
                                        hitBomb(shot2);
                                    }
                                case 1:
                                    if (shot2.getX() > hero.getX() && shot2.getX() < hero.getX() + 60
                                            && shot2.getY() > hero.getY() && shot2.getY() < hero.getY() + 40) {
                                        hitBomb(shot2);
                                    }
                                case 2:
                                    if (shot2.getX()+10 > hero.getX() && shot2.getX()+10 < hero.getX() + 60
                                            && shot2.getY()+10 > hero.getY() && shot2.getY()+10 < hero.getY() + 40) {
                                        hitBomb(shot2);
                                    }
                                case 3:
                                    if (shot2.getX()+10 > hero.getX() && shot2.getX()+10 < hero.getX() + 60
                                            && shot2.getY() > hero.getY() && shot2.getY() < hero.getY() + 40) {
                                        hitBomb(shot2);
                                    }
                            }
                            break;

                    }
                }
            }
        }
    }
    @Override
    public void run() {

//        System.out.println(Thread.currentThread().getName());
        while (true) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hitHero();
            if (hero.getShot() != null && hero.getShot().isLive()) {
                hitEnemyTank();
            }
            this.repaint();
        }
    }
}