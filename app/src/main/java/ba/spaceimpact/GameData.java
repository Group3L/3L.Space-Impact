package ba.spaceimpact;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import ba.spaceimpact.GameObject.UserSpaceship;

import static ba.spaceimpact.GameEngine.userSpaceship;

/**
 * Created by pc on 12.12.2017.
 */

public class GameData implements Serializable{
    public static final String FILE_NAME = "gameData.bin";
    public static final int COIN = 23848247;
    public static final int BULLET_COUNT = 47623647;
    public static final int USER_SPACESHIP = 725838;

    private static GameDataObject gameDataObject;

   public static void save(Context context){
       System.out.println("Inside save...");
       String fileName = "spaceimpact.bin";
       File file = new File( context.getFilesDir(), fileName);
       try {
           ObjectOutputStream os = new ObjectOutputStream ( new FileOutputStream(file));
           os.writeObject(GameData.gameDataObject);
           System.out.println(GameData.getUserSpaceship(context));
           os.close();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    public static void load(Context context){
        System.out.println("Inside load...");
        // Initialize a new csAssist object if the file does not exist
        int health = 10;
        int damage = 1;
        int bulletCount = 50;
        int coin = 30;

        boolean lv1 = true, lv2 = false, lv3 = false, lv4 = false;

        GameData.gameDataObject = new GameDataObject( health, coin, damage, bulletCount, lv1, lv2, lv3, lv4);
        // Try to find the file from directory and reload the object
        try {
            String fileName = "spaceimpact.bin";
            File file = new File(context.getFilesDir(), fileName);
            ObjectInputStream is = new ObjectInputStream( new FileInputStream(file));
            GameData.gameDataObject = (GameDataObject) is.readObject();
            save(context);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void setHealth(int health){
        gameDataObject.setHealth( health);
    }

    public static int getHealth(){
        return gameDataObject.getHealth();
    }

    public static UserSpaceship getUserSpaceship(Context context){
        return gameDataObject.getUserSpaceship(context);
    }

    public static void setUserSpaceship(UserSpaceship userSpaceship){
        gameDataObject.setHealth(userSpaceship.getHealth());
        gameDataObject.setBulletCount(userSpaceship.getBulletCount());
        gameDataObject.setDamage(userSpaceship.getDamage());
    }

    public static int getCoin() {
        return gameDataObject.getCoin();
    }

    public static void setCoin(int coin) {
        gameDataObject.setCoin(coin);
    }

    public static int getBulletCount() {
        return gameDataObject.getBulletCount();
    }

    public static void setBulletCount(int bulletCount) {
        gameDataObject.setBulletCount(bulletCount);
    }

    public static void unlockLevel(int levelNo){
       // if( levelNo -1 >= 1 && levelNo-1 < gameDataObject.getUnLockedLevels().length && isLevelUnlocked(levelNo) != true && isLevelUnlocked(levelNo-1))
            gameDataObject.unlockLevel(levelNo);
    }

    public static boolean isLevelUnlocked(int levelNo){
        //if( levelNo -1 >= 1 && levelNo-1 < gameDataObject.getUnLockedLevels().length )return gameDataObject.getUnLockedLevels()[levelNo-1];
        //else return false;
        return gameDataObject.isLevelUnlocked(levelNo);
    }
}
