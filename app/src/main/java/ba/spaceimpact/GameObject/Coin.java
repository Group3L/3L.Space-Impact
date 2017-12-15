package ba.spaceimpact.GameObject;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by pc on 14.12.2017.
 */

public class Coin implements Collectable {

    int amount;

    public Coin( int amount){
        this.amount = amount;
    }

    @Override
    public void draw(Canvas c) {

    }

    @Override
    public void update() {

    }

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }

    @Override
    public boolean getVisible() {
        return false;
    }

    @Override
    public RectF getRect() {
        return null;
    }
}
