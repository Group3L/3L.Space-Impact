package ba.spaceimpact.GameObject;

/**
 * Created by busraarabaci on 02/11/2017.
 */

import android.graphics.Canvas;
import android.graphics.RectF;

public interface GameObject {

    public void draw(Canvas c);
    public void update();
    public float getX();
    public float getY();
    public boolean getVisible();
    public RectF getRect();

}
