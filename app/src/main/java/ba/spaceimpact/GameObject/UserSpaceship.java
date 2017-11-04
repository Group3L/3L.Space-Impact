package ba.spaceimpact.GameObject;

/**
 * Created by busraarabaci on 02/11/2017.
 */

        import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.graphics.RectF;
        import android.view.Display;
        import android.view.WindowManager;

        import ba.spaceimpact.R;


public class UserSpaceship implements GameObject{

    private float x, y;
    private Bitmap spaceship;
    private Context context;
    private int health, damage;
    private int height, width;
    private boolean visible=true;
    private RectF rect;
    private int bulletCount;

    public UserSpaceship(Context context, int health, int damage){

        this.health = health;
        this.damage = damage;
        this.context = context;
        spaceship = BitmapFactory.decodeResource( context.getResources(), R.drawable.player);
        height = spaceship.getHeight();
        width = spaceship.getWidth();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        x = (width + display.getWidth()) / 2;
        y = (float)0.6 * display.getHeight() ;
        rect = new RectF();
        bulletCount = 200;

    }

    public void getHit( int damage){ health -= damage;}

    public int getHealth(){return health;}

    public int getBulletCount(){return bulletCount;}

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean contains(float x, float y){

        int height = spaceship.getHeight();
        int width = spaceship.getWidth();

        if( x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height)
            return true;

        return false;
    }

    public boolean shoot(){
        if( bulletCount > 0) {
            bulletCount--;
            return true;
        }
        return false;
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
    public RectF getRect(){ return rect; }

    @Override
    public boolean getVisible() { return visible; }

    @Override
    public void draw(Canvas c) {
        c.drawBitmap(spaceship, x, y, null);
    }

    public void move( float x, float y){
        this.x = x;
        this.y = y;

        rect.left = x;
        rect.top = y;
        rect.right = x + width;
        rect.bottom = y + height;
    }

    @Override
    public void update() {

    }


}
