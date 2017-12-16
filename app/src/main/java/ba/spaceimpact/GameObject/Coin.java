package ba.spaceimpact.GameObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import ba.spaceimpact.R;

/**
 * Created by pc on 14.12.2017.
 */

public class Coin implements Collectable {

    int amount;
    UserSpaceship userSpaceship;
    float x, y, speedY, speedX;
    int width, height;
    RectF rectF;
    boolean visibility;
    private Bitmap coin;


    public Coin(Context context, int amount, UserSpaceship userSpaceship, float x, float y, float speedx, float speedY){
        this.amount = amount;
        this.userSpaceship = userSpaceship;
        this.x = x;
        this.y = y;
        coin = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin);

        width = coin.getWidth();
        height = coin.getHeight();
        this.speedX = speedx;
        this.speedY = speedY;
        visibility = true;
    }

    @Override
    public void draw(Canvas c) {
        c.drawBitmap(coin, x, y, null);

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
