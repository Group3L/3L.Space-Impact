package ba.spaceimpact;

import android.content.Context;

import java.io.Serializable;
import ba.spaceimpact.GameObject.UserSpaceship;

public class GameDataObject implements Serializable {

    private int health;
    private int coin;
    private int bulletCount;
    private int damage;
    private boolean[] unLockedLevels;
    static final long serialVersionUID =512501357697043170L;
    private boolean lv1, lv2,lv3, lv4; //apparently, boolean arrays cannot be serialized

    public GameDataObject(int health, int coin, int damage, int bulletCount, boolean[] unLockedLevels, boolean lv1, boolean lv2, boolean lv3, boolean lv4){
        this.health = health;
        this.coin = coin;
        this.unLockedLevels = unLockedLevels;
        this.bulletCount = bulletCount;
        this.damage = damage;
        this.lv1 = lv1;
        this.lv2 = lv2;
        this.lv3 = lv3;
        this.lv4 = lv4;

    }

    public UserSpaceship getUserSpaceship(Context context) {
        UserSpaceship userSpaceship = new UserSpaceship(context, health, damage, bulletCount);
        userSpaceship.setCoin( coin );
        return userSpaceship;
    }

    public void setUnLockedLevels(boolean[] unLockedLevels) {
        this.unLockedLevels = unLockedLevels;
    }

    public boolean[] getUnLockedLevels() {
        return unLockedLevels;
    }

    public void unlockLevel(int levelNo) {
        //this.unLockedLevels[levelNo-1] = true;
        switch (levelNo){
            case 1: lv1 = true; break;
            case 2: lv2 = true; break;
            case 3: lv3 = true; break;
            case 4: lv4 = true; break;
        }
    }

    public boolean isLevelUnlocked(int levelNo){
        switch (levelNo){
            case 1: return lv1;
            case 2: return lv2;
            case 3: return lv3;
            case 4: return lv4;
        }
        return false;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getBulletCount() {
        return bulletCount;
    }

    public void setBulletCount(int bulletCount) {
        this.bulletCount = bulletCount;
    }
}