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

        import java.io.Serializable;

        import ba.spaceimpact.R;


public class UserSpaceship implements Serializable, GameObject{

    private static final long serialVersionUID = 1;
    private float x, y;
    private Bitmap spaceship;
    private Context context;
    private int health, damage;
    private int height, width;
    private boolean visible=true;
    private RectF rect;
    private int bulletCount;
    private boolean invincible, infiniteShoot;
    private int extraScore;
    private int score;
    private int coin;

    public UserSpaceship(Context context, int health, int damage, int bulletCount){
        this.score = 0;
        this.invincible = false;
        this.health = health;
        this.damage = damage;
        this.context = context;
        this.coin = 0;
        spaceship = BitmapFactory.decodeResource( context.getResources(), R.drawable.player);
        height = spaceship.getHeight();
        width = spaceship.getWidth();
        x = 0;
        y = 0;
        rect = new RectF();
        this.bulletCount = bulletCount;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public boolean isInfiniteShoot() {
        return infiniteShoot;
    }

    public void setBulletCount( int bulletCount){
        this.bulletCount = bulletCount;
    }

    public void setInfiniteShoot(boolean infiniteShoot) {
        this.infiniteShoot = infiniteShoot;
    }

    public void setExtraScore(int extraAmount ){
        this.extraScore = extraAmount;
    }

    public void increaseScore( int score){
        //if the powerup is on extrascore will be more than 0
        this.score += score * (1+ extraScore);
    }

    public int getScore(){return score;}

    public void setInvincible(boolean i){ invincible = i;}

    public boolean isInvincible(){return invincible;}

    public int getDamage(){return damage;}

    public void getHit( int damage){ health -= damage;}

    public int getHealth(){return health;}

    public void setHealth( int health){this.health = health;}

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

    public boolean shoot(boolean useBullet){
            if(useBullet)bulletCount--;
            return true;
    }

    public String toString(){
        return "Health : " + health + "\nBullet Count : " + bulletCount + "\n";
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
