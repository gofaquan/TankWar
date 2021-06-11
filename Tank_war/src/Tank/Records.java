package Tank;

import java.io.*;
import java.util.Vector;

public class Records {
    //定义变量，记录我方击毁敌人坦克数
    private static int allEnemyTankNum = 0;

    //定义IO对象及路径,写数据到文件中
    private static BufferedWriter bw = null;
    private static String recordFile = "/home/archLinux/IdeaProjects/TankWar/Tank_war/src/Tank/myRecords.txt";

    //定义坦克Vector存数量及位置
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
    //定义Vector保存坦克信息
    private static Vector<EnemyInfo> enemyInfos = new Vector<>();
    //定义一个输入流
    private static BufferedReader br = null;
    //选择继续上局游戏调用
    public static Vector<EnemyInfo> getEnemyInfos(){
        try {
            br = new BufferedReader(new FileReader(recordFile));
            //读取数字string转int，确定上次游戏坦克数量
            allEnemyTankNum = Integer.parseInt(br.readLine());
            //确实上次坦克的位置
            String Info = "";
            while ((Info = br.readLine()) != null){
                String[] xyd = Info.split(" ");
                EnemyInfo enemyInfo = new EnemyInfo(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                enemyInfos.add(enemyInfo);
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (br != null){
                    br.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }

        }
        return null;
    }
}
