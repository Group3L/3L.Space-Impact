package ba.spaceimpact.GameObject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by pc on 14.12.2017.
 */

public class Coin implements Collectable {

    int amount;
    UserSpaceship userSpaceship;
    float x, y, speedY, speedX;
    int width, height;
    RectF rectF;
    Paint paint;
    boolean visibility;

    public Coin( int amount, UserSpaceship userSpaceship, float speedx, float speedY){
        this.amount = amount;
        this.userSpaceship = userSpaceship;
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        width = 100;
        height = 100;
        this.speedX = speedx;
        this.speedY = speedY;
        visibility = true;
    }

    @Override
    public void draw(Canvas c) {
        c.drawRect(x, y, x + width, y + height, paint);
    }

    @Override
    public void update() {
        y += speedY;
        x += speedX;
    }

    public void activateCoin(){
        userSpaceship.setCoin( userSpaceship.getCoin() + amount);
        visibility = false;
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

    @Override
    public RectF getRect() {
        return new RectF(x, y, x+width, y+height);
    }
}
