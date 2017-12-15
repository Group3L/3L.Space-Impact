package ba.spaceimpact.GameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;

import ba.spaceimpact.GameEngine;
import ba.spaceimpact.GameView;
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


    public EnemySpaceship(int health, int damage, float startingX,
                          float startingY, final float speedX, float speedY,
                          Context context){
        this.health = health;
        this.damage = damage;
        x = startingX;
        y = startingY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.context = context;
        /*
        * Bitmap b = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
            profileImage.setImageBitmap(Bitmap.createScaledBitmap(b, 120, 120, false));
        * */
        spaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.rsz_1enemy);
        rect = new RectF();

        width = spaceship.getWidth();
        height = spaceship.getHeight();

        rect.left = x;
        rect.top = y;
        rect.right = x + width;
        rect.bottom = y + height;
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

    @Override
    public void shoot() {

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

        move( x + speedX, y + speedY);
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
