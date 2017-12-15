package ba.spaceimpact.GameObject;

import android.graphics.RectF;


public class Bullet{

    private float x;
    private float y;

    private RectF rect;
    private boolean userBullet;
    public final int UP = 0;
    private int damage;

    int heading = -1;
    float speed =  1500;

    private int width = 5;
    private int height;

    private boolean isActive;

    public Bullet(int screenY, boolean userBullet, int damage) {
        this.damage = damage;
        this.userBullet = userBullet;
        height = screenY / 100;
        isActive = false;

        rect = new RectF();
    }

    public int getDamage(){return damage;}

    public boolean isUserBullet() {
        return userBullet;
    }

    public float getImpactPointY(){
        return  y;
    }

    public RectF getRect(){
        return  rect;
    }

    public boolean getStatus(){
        return isActive;
    }

    public void setInactive(){
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean shoot(float startX, float startY, int direction) {
        if (!isActive) {
            x = startX;
            y = startY;
            heading = direction;    // Direction will be needed for the enemies bullets, when we implement it
            isActive = true;
            return true;
        }

        // Bullet already active
        return false;
    }
    public void update(long fps){

        //if(heading == UP){                // The commented parts will be needed when we implement
        y = y - speed / fps;            //  the shooting part for the enemies
        //}else{
        //    y = y + speed / fps;
        // }

        // Update rect
        rect.left = x;
        rect.right = x + width;
        rect.top = y;
        rect.bottom = y + height;

    }


}
