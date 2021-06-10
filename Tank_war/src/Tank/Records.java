package Tank;

import java.io.*;
import java.util.Vector;

public class Records {
    //定义变量，记录我方击毁敌人坦克数
    private static int allEnemyTankNum = 0;

    //定义IO对象及路径,写数据到文件中
    private static BufferedWriter bw = null;
    private static String recordFile = "/home/archLinux/IdeaProjects/TankWar/Tank_war/src/Tank/myRecords.txt";


    private static Vector<EnemyTank> enemyTanks = null;

    public static void setAllEnemyTankNum(Vector<EnemyTank> enemyTanks) {
        Records.enemyTanks = enemyTanks;
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void addAllEnemyTankNum(){
        Records.allEnemyTankNum++;
    }
    public static  void Save(){
        try {
            bw= new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum+"\n");
        //把位置存进去
            for (int i = 0; i < enemyTanks.size(); i++) {
              EnemyTank enemyTank =  enemyTanks.get(i);
                String record = enemyTank.getX() + " "+ enemyTank.getY()+" "+enemyTank.getDirection();
                bw.write(record+"\n");
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (bw != null){
                    bw.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }

        }

    }
}
