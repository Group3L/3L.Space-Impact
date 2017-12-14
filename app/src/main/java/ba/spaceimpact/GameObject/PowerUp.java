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

    public PowerUp(int powerupType, UserSpaceship userSpaceship, float x, float y, float speedX, float speedY){
        this.powerupType = powerupType;
        visibility = true;
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        width = 100;
        height = 100;
    }

    public void powerUp(final UserSpaceship userSpaceship){
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
                }, 10000);
                break;
            case HEALTH_REGEN:
                userSpaceship.setHealth(userSpaceship.getHealth() + 10);
                break;
            case INF_BULLET:
                //TODO
                break;
            case EXTRA_POINT:
                //TODO
                Random r = new Random();
                userSpaceship.setExtraScore(1 + r.nextInt(2));

                final Handler handler2 = new Handler(Looper.myLooper().getMainLooper());
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        userSpaceship.setExtraScore(0);
                    }
                }, 10000);
                break;


        }

    }

    public int getPowerupType(){return powerupType;}

    @Override
    public void draw(Canvas c) {
        //TODO
        Paint p = new Paint();
        p.setColor(Color.RED);
        c.drawRect(x, y, x + width, y + height, p);
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
