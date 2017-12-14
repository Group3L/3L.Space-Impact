package ba.spaceimpact.GameObject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;

import java.util.Random;


public class PowerUp implements Collectable {

    public static final int SHIELD = 1;
    public static final int HEALTH_REGEN = 2;
    public static final int INF_BULLET= 3;
    public static final int EXTRA_POINT = 4;

    private int powerupType;
    private float x, y, speedX, speedY, width, height;
    private boolean visibility;
    private RectF rect;
    private Paint paint;

    public PowerUp(int powerupType, UserSpaceship userSpaceship, float x, float y, float speedX, float speedY){
        this.powerupType = powerupType;
        visibility = true;
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        width = 100;
        height = 100;
        paint = new Paint();
        switch (powerupType){
            case SHIELD:
                paint.setColor( Color.BLACK);
                break;
            case HEALTH_REGEN:
                paint.setColor( Color.RED);
                break;
            case INF_BULLET:
                paint.setColor( Color.WHITE);
                break;
            case EXTRA_POINT:
                paint.setColor( Color.CYAN);
                break;

        }


    }

    public void powerUp(final UserSpaceship userSpaceship){
        Random r = new Random();
        switch (powerupType){
            case SHIELD:
                userSpaceship.setInvincible(true);
                System.out.println("SHILED powerup");
                //make user spaceship invinsible for 10 seconds
                final Handler handler = new Handler(Looper.myLooper().getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("setInvincible");
                        userSpaceship.setInvincible(false);
                    }
                }, 5000);
                break;
            case HEALTH_REGEN:
                userSpaceship.setHealth(userSpaceship.getHealth() + 10);
                break;
            case INF_BULLET:
                //TODO
                userSpaceship.setInfiniteShoot(true);

                final Handler handler2 = new Handler(Looper.myLooper().getMainLooper());
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        userSpaceship.setInfiniteShoot(false);
                    }
                }, 2500);
                break;
            case EXTRA_POINT:
                userSpaceship.setExtraScore(1 + r.nextInt(2));

                final Handler handler3 = new Handler(Looper.myLooper().getMainLooper());
                handler3.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        userSpaceship.setExtraScore(0);
                    }
                }, 5000);
                break;


        }
        visibility = false;

    }

    public int getPowerupType(){return powerupType;}

    @Override
    public void draw(Canvas c) {
        //TODO
        c.drawRect(x, y, x + width, y + height, paint);
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public boolean getVisible() {
        return visibility;
    }

    public void setVisibility(boolean visibility){this.visibility = visibility;}

    @Override
    public RectF getRect() {
        return new RectF(x, y, x+30, y+30);
    }
}
