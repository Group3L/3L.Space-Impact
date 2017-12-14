package ba.spaceimpact;

import android.content.Context;

import java.io.Serializable;
import ba.spaceimpact.GameObject.UserSpaceship;

public class GameDataObject implements Serializable {

    private int health;
    private int coin;
    private int bulletCount;
    private int damage;

    public GameDataObject(int health, int coin, int damage, int bulletCount){
        this.health = health;
        this.coin = coin;
        this.bulletCount = bulletCount;
        this.damage = damage;
    }

    public UserSpaceship getUserSpaceship(Context context) {
        return new UserSpaceship(context, health, damage, bulletCount);
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