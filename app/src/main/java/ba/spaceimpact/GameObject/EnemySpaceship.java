package ba.spaceimpact.GameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;

import java.util.Random;

import ba.spaceimpact.EasyBehaviour;
import ba.spaceimpact.EnemyBehaviour;
import ba.spaceimpact.GameEngine;
import ba.spaceimpact.GameView;
import ba.spaceimpact.HardBehaviour;
import ba.spaceimpact.MediumBehaviour;
import ba.spaceimpact.R;


public class EnemySpaceship implements NPC{

    private int health, damage;
    private float x, y;
    private float speedX, speedY;
    private Bitmap spaceship;
    private Context context;
    private int turnDelay;
    private boolean visible = true;
    private RectF rect;
    private int width;
    private int height;
    private EnemyBehaviour enemyStrategy;
    public enum enemyType {EASY, MEDIUM, HARD;}




    public EnemySpaceship(int health, int damage, float startingX,
                          float startingY, final float speedX, float speedY,
                          Context context, enemyType type){
        this.health = health;
        this.damage = damage;
        x = startingX;
        y = startingY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.context = context;
        spaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.rsz_1enemy);
        rect = new RectF();
        width = spaceship.getWidth();
        height = spaceship.getHeight();

        rect.left = x;
        rect.top = y;
        rect.right = x + width;
        rect.bottom = y + height;

        if(type == enemyType.EASY){
            enemyStrategy = new EasyBehaviour();
        }

        else if(type == enemyType.MEDIUM){
            enemyStrategy = new MediumBehaviour();


        }
        else if(type == enemyType.HARD){
            enemyStrategy = new HardBehaviour();


        }
        else
            enemyStrategy = new MediumBehaviour();
    }

    @Override
    public RectF getRect(){
        return rect;
    }



    @Override
    public void move(float x, float y) {
        this.x = x;
        this.y = y;

    }

    public Bitmap getSpaceship() {
        return spaceship;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSpaceship(Bitmap spaceship) {
        this.spaceship = spaceship;
    }

    @Override
    public Bullet shoot() {
        return new Bullet( GameView.screenY, false, 1);
    }

    //@Override
    public void draw(Canvas c) {
        if(visible){
            try{
                c.drawBitmap(spaceship, x, y, null);
            }catch (Exception e){}
        }

    }

    //@Override
    public void update() {
      //  checkCollisionWithBullets();
        float[] positions = enemyStrategy.move(x,y,speedY);
        //move( x + speedX, y + speedY);
        this.x = positions[0];
        this.y = positions[1];
        rect.left = x;
        rect.top = y;
        rect.right = x + width;
        rect.bottom = y + height;
        if( x > GameView.screenX) {
            visible = false;
        }
        if( y > GameView.screenY) {
            visible = false;
        }
        if(health<=0)
            visible=false;
    }

    public int getHealth(){return health;}

    //@Override
    public float getX() {
        return 0;
    }

    //@Override
    public float getY() {
        return 0;
    }

    public boolean getVisible() { return visible; }

    /*public void checkCollisionWithUser(){
        if (this.getRect().intersect(GameEngine.userSpaceship.getRect())){
            Log.d("Collision","Between Enemy and User");
        }
    }*/

    @Override
    public void getHit(int amount) {
        health -= amount;
    }

    /*
    public void checkCollisionWithBullets(){
        for(int i=0; i<GameView.bullet.length; i++){
            if(GameView.bullet[i].getStatus() && this.getRect().intersect(GameView.bullet[i].getRect())){
                Log.d("Collision","Between Enemy and Bullet");
                health--;
                GameView.bullet[i].setInactive();
            }
        }
    }*/

}
