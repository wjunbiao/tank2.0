package tankgame;

import java.io.*;
import java.util.Vector;

/**
 * @author 王俊彪
 * @version 1.0
 */
public class Recorder {
    private static int allEnemyTankNum=0;

    //定义IO对象，准备写数据到文件中
    private static BufferedWriter bw =null;
    private static BufferedReader br =null;
    private static String recordFile="src\\myRecord.txt";
    private static Vector<EnemyTank> enemyTanks = null;
    private static Hero hero=null;//准备把自己的坦克读进来
    private static Vector<Block> blocks=null;//准备把障碍物的数据读进来
    private static Vector<BlockNode> blockNodes= new Vector<>();
    private static Vector<Node> nodes = new Vector<>();//注意这里不能和前面一样，为了方便写一个空，不然下面用add的时候用不了，而且还不报错
    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static void setHero(Hero hero) {
        Recorder.hero = hero;
    }

    public static Vector<BlockNode> getBlockNodes() {
        return blockNodes;
    }

    public static String getRecordFile() {
        return recordFile;
    }

    public static void setBlocks(Vector<Block> blocks) {
        Recorder.blocks = blocks;
    }

    //编写方法，用于读取recordFile 中的游戏记录
    public static Vector<Node> getNodesAndEnemyTank(){
        try {
            br= new BufferedReader(new FileReader(recordFile));
            String count =br.readLine();
            if(count !=null) {
                allEnemyTankNum = Integer.parseInt(count);
            }else{
                return null;
            }
            String line ="";
            while((line =br.readLine())!=null){
                String [] syd = line.split(" ");
                if(syd.length==3){
                    Node node =new Node(Integer.parseInt(syd[0]),
                            Integer.parseInt(syd[1]),
                            Integer.parseInt(syd[2]));
                    nodes.add(node);
                }else{
                    BlockNode blockNode = new BlockNode(Integer.parseInt(syd[0]),
                            Integer.parseInt(syd[1]));
                    blockNodes.add(blockNode);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return  nodes;
    }




    public static void keepRecord(){
        try {
            bw=new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum+"\r\n");
            bw.write(hero.getX()+" "+hero.getY()+" "+hero.getDirect()+"\r\n");
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if(enemyTank.isLive()){
                    bw.write(enemyTank.getX()+" "+enemyTank.getY()+" "+enemyTank.getDirect()+"\r\n");
                }
            }
            for (int i = 0; i < blocks.size(); i++) {
                Block block = blocks.get(i);
                bw.write(block.getX()+" "+block.getY()+"\r\n");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        allEnemyTankNum = allEnemyTankNum;
    }
    public static int addAllEnemyTankNum(){
        return allEnemyTankNum++;
    }
}
