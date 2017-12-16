package ba.spaceimpact;

import android.content.Context;

import ba.spaceimpact.GameObject.UserSpaceship;


public class Store {

    public final static int HEALTH_UPGRADE_PRICE = 80;
    public static final int BULLET_UPGRADE_PRICE = 50;


    public static void addBulletUpgrade(Context context){
        GameData.load(context);
        GameData.setBulletCount( GameData.getBulletCount() + 10);
        GameData.save(context);
    }

    public static void healthUpgrade(Context context){
        GameData.load(context);
        GameData.setHealth(GameData.getHealth() + 3);
        GameData.setCoin(GameData.getCoin() - HEALTH_UPGRADE_PRICE);
        GameData.save(context);
    }

    public static void shootingStyleUpgrade(Context context){

    }


}
