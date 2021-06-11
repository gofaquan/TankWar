package Tank;

import java.io.*;
import java.util.Vector;

/**
 * @title: Records
 * @package Tank
 * @description:
 * @author: kashimashino
 * @date: 2021-06-11 上午7:27
 * @version: V1.0
*/
@SuppressWarnings({"all"})
public class Records {
    //定义变量，记录我方击毁敌人坦克数
    private static int allEnemyTankNum = 0;
    //击毁敌方坦克++数量
    public static void addAllEnemyTankNum(){
        Records.allEnemyTankNum++;
    }
    //获取敌方坦克数量在画板上显示
    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }


//存数据
    //定义IO对象及路径,写数据到文件中
    private static BufferedWriter bw = null;
    private static String recordFilePath = "/home/archLinux/IdeaProjects/TankWar/Tank_war/src/Tank/myRecords.txt";
    //返回记录文件的目录
    public static String getRecordFilePath() {
        return recordFilePath;
    }

    //定义坦克Vector存数量及位置
    private static Vector<EnemyTank> enemyTanks = null;
    //同步数量及位置
    public static void setAllEnemyTank(Vector<EnemyTank> enemyTanks) {
        Records.enemyTanks = enemyTanks;
    }
    /**
     *@title: Save
     *@author: archLinux
     *@date: 2021/6/11 上午8:03
     *@param: []
     *@return: void
     *存数据进txt文件
     */
    public static  void Save(){
        try {
            //存击毁的坦克数量
            bw = new BufferedWriter(new FileWriter(recordFilePath));
            bw.write(allEnemyTankNum+"\r\n");
            //把位置存进去
            for (int i = 0; i < enemyTanks.size(); i++) {
              EnemyTank enemyTank =  enemyTanks.get(i);
                String record = enemyTank.getX() + " "+ enemyTank.getY()+" "+enemyTank.getDirection();
                bw.write(record+"\r\n");
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



//读取信息
    //定义Vector保存坦克信息
    private static Vector<EnemyInfo> enemyInfos = new Vector<>();
    //定义一个输入流
    private static BufferedReader br = null;
    /**
     *@title: getEnemyInfos
     *@author: archLinux
     *@date: 2021/6/11 上午8:07
     *@param: []
     *@return: java.util.Vector<Tank.EnemyInfo>
     *选择继续上局游戏调用的函数，获取敌人信息（位置及数量）
     */

    public static Vector<EnemyInfo> getEnemyInfos(){

        try {
            br = new BufferedReader(new FileReader(recordFilePath));
            //读取数字string转int，确定上次游戏坦克数量
            allEnemyTankNum = Integer.parseInt(br.readLine());
            //确定上次坦克的位置
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
        return enemyInfos;
    }
}
