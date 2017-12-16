package ba.spaceimpact;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import ba.spaceimpact.GameObject.Bullet;
import ba.spaceimpact.GameObject.Coin;
import ba.spaceimpact.GameObject.EnemySpaceship;
import ba.spaceimpact.GameObject.GameObject;
import ba.spaceimpact.GameObject.PowerUp;
import ba.spaceimpact.GameObject.UserSpaceship;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import static ba.spaceimpact.GameView.screenY;

public class GameEngine implements Runnable, Serializable {

    private final int ENEMY_KILL_SCORE = 50;

    private boolean playing;
    private Thread gameThread;
    private Canvas canvas;
    public static ArrayList<GameObject> gameObjects;
    private Context context;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    public static UserSpaceship userSpaceship;
    private Bitmap background;
    private int pixelX, pixelY;
    private float dockHeight;
    public static final double DOCK_RATIO = 0.2;
    private Rect shootButtonRect;
    private GameActivity gameActivity;
   // private Bullet[] bullet = new Bullet[150];
    ArrayList<Bullet> bullet;
    private int shootingCounter;
    private int ilo = 0;//?
    int killedEnemyCount;

    public GameEngine(Context context, SurfaceView surfaceView, UserSpaceship userSpaceship, GameActivity gameActivity, int level){
        this.context = context;
        this.surfaceView = surfaceView;
        surfaceHolder = surfaceView.getHolder();
        shootingCounter = 0;
        this.userSpaceship = userSpaceship;


        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        pixelX = display.getWidth();
        pixelY = display.getHeight();

        gameObjects = LevelCreator.setGameObjects(context, userSpaceship, pixelX, pixelY, level);

        this.userSpaceship.move((this.userSpaceship.getWidth() + pixelX) / 2, (float)0.6 * pixelY);

        /*for(int i = 0; i < bullet.length; i++){
            bullet[i] = new Bullet(screenY, true, 1);
        } */
        bullet = new ArrayList<>();

        System.out.println("X: " + (this.userSpaceship.getWidth() + pixelX) / 2);
        System.out.println("Y: " + (float)0.6 * pixelY);

        dockHeight = (float)(pixelY * DOCK_RATIO);
        shootButtonRect = new Rect(pixelX / 2, (int)(pixelY), (int)(pixelX ), (int)(pixelY - (0.5 * dockHeight)));
        this.gameActivity = gameActivity;
    }

    @Override
    public void run() {
        while (playing) {

            //to update the frame
            update();

            //to draw the frame
            draw();

            //to control
            control();
        }
    }


    public void setBackgroundImage(Bitmap backgroundImage){
        background = backgroundImage;
    }

 /*   public void shoot( boolean infiniteShoot){
        if (userSpaceship.isInfiniteShoot() == infiniteShoot && userSpaceship.getBulletCount() > 0 ){// && bullet[ilo].shoot(userSpaceship.getX() + userSpaceship.getWidth() / 2, userSpaceship.getY(), Bullet.UP)) {

            ilo++;
            userSpaceship.shoot(!userSpaceship.isInfiniteShoot(), pixelY).shoot(userSpaceship.getX() + userSpaceship.getWidth() / 2, userSpaceship.getY(), Bullet.UP);
            if (ilo == bullet.length) {
                ilo = 0;
            }
        }

    }*/

    public void shoot( boolean infiniteShoot){
        if (userSpaceship.isInfiniteShoot() == infiniteShoot && userSpaceship.getBulletCount() > 0 ){// && bullet[ilo].shoot(userSpaceship.getX() + userSpaceship.getWidth() / 2, userSpaceship.getY(), Bullet.UP)) {
            Bullet b = userSpaceship.shoot(!userSpaceship.isInfiniteShoot(), pixelY);
            bullet.add(b);
            b.shoot(userSpaceship.getX() + userSpaceship.getWidth() / 2, userSpaceship.getY());
        }

    }


    private boolean isEnemyLeft(){
        boolean result = false;
        for (int i = 0; i < gameObjects.size(); i++) {
            if(gameObjects.get(i) instanceof EnemySpaceship)
                result = true;
        }
        return result;
    }




    public void draw(){
        String scoreStr;
        Paint p;
        try{
            if(surfaceHolder.getSurface().isValid()){
                canvas = surfaceHolder.lockCanvas();

                if(background != null){
                        canvas.drawBitmap(background, 0, 0, null);
                }

                scoreStr = "Score: " + userSpaceship.getScore() + "\nCoin : " + userSpaceship.getCoin();
                p = new Paint();
                p.setTextSize(48f);
                p.setColor(Color.WHITE);


                canvas.drawText(scoreStr,50,50,p);
                userSpaceship.draw(canvas);


                for (int i = 0; i < gameObjects.size(); i++){
                    gameObjects.get(i).draw(canvas);
                }


                Paint paint = new Paint();
                paint.setColor(Color.YELLOW);

                /*for(int i = 0; i < bullet.length; i++){
                    if(bullet[i].getStatus()) {
                        canvas.drawRect(bullet[i].getRect(), paint);
                    }
                } */

                for(int i = 0; i < bullet.size(); i++){
                    bullet.get(i).draw(canvas);
                }

                drawBottomDock( canvas);


                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }catch (Exception e){}
    }

    private void drawBottomDock( Canvas canvas){
        canvas.drawRect( 0, (int)(pixelY - dockHeight), (int)(pixelX), (int)pixelY,
                         new Paint( Color.BLACK));



        //canvas.drawRect( (int)(pixelX * 0.5) , (int)(pixelY - (dockHeight * 0.9)), 0, 0,
        //                 new Paint(Color.RED));
        Paint shootButton = new Paint();
        shootButton.setColor( Color.RED);
        canvas.drawRect( shootButtonRect, shootButton);

        Paint textColor = new Paint();
        textColor.setColor(Color.BLACK);
        textColor.setTextSize(80);
        canvas.drawText("SHOOT", (float)(((shootButtonRect.left + shootButtonRect.right)*(0.43)) ), ((float)((shootButtonRect.top+ shootButtonRect.bottom)*(0.51))), textColor);

        Paint healthText= new Paint();
        textColor.setColor(Color.WHITE);
        textColor.setTextSize(80);

        canvas.drawText("Health: " + userSpaceship.getHealth(), 10, pixelY - dockHeight + 70, textColor);
        canvas.drawText("Ammo: " + userSpaceship.getBulletCount(), 10, pixelY - 10, textColor);

    }

    public Rect getShootButtonRect() {
        return shootButtonRect;
    }

    public double getDockHeight() {
        return dockHeight;
    }

    public void update(){
        shootingCounter++;
        if(shootingCounter > 2000000)shootingCounter = 0;

        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).update();
            //if the object gets out of the frame dispose it
            if (!gameObjects.get(i).getVisible()) {
                System.out.println("Gameobjects: " + gameObjects.size());
                if( gameObjects.get(i) instanceof EnemySpaceship){
                    killedEnemyCount++;
                    userSpaceship.increaseScore(ENEMY_KILL_SCORE);
                }
                gameObjects.remove(i);
                userSpaceship.increaseScore(ENEMY_KILL_SCORE);
                i--;
            }

        }

        //if the infinite shoot powerup is active, then the spacship will shoot automatically
        //and it won't use user's bullets
        shoot(true);

        for(int i = 0; i < bullet.size(); i++){
            if(bullet.get(i).getStatus()) {
                bullet.get(i).update(60);
            }
        }

        // Has the player's bullet hit the top of the screen
        for(int i = 0; i < bullet.size(); i++){
            if(bullet.get(i).getImpactPointY() < 0){
                bullet.get(i).setInactive();
            }
        }

        //collision between bullets and spaceships
        for( int j = 0; j < gameObjects.size(); j++){
            if( gameObjects.get(j) instanceof EnemySpaceship){
                for( int i = bullet.size()-1; i >= 0; i--){
                    if(bullet.get(i).isUserBullet() && bullet.get(i).getStatus() && gameObjects.get(j).getRect().intersect(bullet.get(i).getRect())){
                        Log.d("Collision","Between Enemy and Bullet");
                        ((EnemySpaceship) gameObjects.get(j)).getHit(bullet.get(i).getDamage());
                        bullet.get(i).setInactive();
                        bullet.remove(i);
                    }
                }
            }
        }

       /* for(int i = 0; i < gameObjects.size(); i++) {
            GameObject g = gameObjects.get(i);
            if( g instanceof EnemySpaceship && (((EnemySpaceship) g).geteType()) != EnemySpaceship.enemyType.EASY && shootingCounter == ((EnemySpaceship)g).getShootingDelay() &&
                    g.getVisible() && g.getY() > 0 ){
                shootingCounter = 0;
                Bullet b = ((EnemySpaceship) g).shoot();
                bullet.add(b);
                b.shoot(g.getX() + ((EnemySpaceship) g).getWidth() / 2, g.getY());

            }

            if (g instanceof EnemySpaceship && (((EnemySpaceship) g).geteType()) != EnemySpaceship.enemyType.EASY ){
                if ( g instanceof EnemySpaceship && (((EnemySpaceship) g).geteType()) != EnemySpaceship.enemyType.EASY){// && bullet[ilo].shoot(userSpaceship.getX() + userSpaceship.getWidth() / 2, userSpaceship.getY(), Bullet.UP)) {
                    Bullet b = ((EnemySpaceship)g).shoot();
                    bullet.add(b);
                    b.shoot(g.getX() + ((EnemySpaceship) g).getWidth() / 2, g.getY());
                }
            }
        }*/
        //collision detection
        for (int i = 0; i < gameObjects.size(); i++) {
            if(isEnemyLeft() && userSpaceship.getRect().intersect(gameObjects.get(i).getRect())){
                if(gameObjects.get(i) instanceof EnemySpaceship && !userSpaceship.isInvincible()) userSpaceship.getHit(1); // if shield powerup is not on//constant value for now
                else if( gameObjects.get(i) instanceof PowerUp)((PowerUp) gameObjects.get(i)).powerUp(userSpaceship);
                else if( gameObjects.get(i) instanceof Coin)((Coin) gameObjects.get(i)).activateCoin();
                gameObjects.remove(i);
                i--;
            }
        }

        if(!isEnemyLeft()) {
            Random random = new Random();
            pause();
            gameActivity.gameOver(true, userSpaceship.getScore(), killedEnemyCount, userSpaceship.getCoin());
       }

        if( userSpaceship.getHealth() <= 0){
            gameActivity.gameOver(false, userSpaceship.getScore(), killedEnemyCount, userSpaceship.getCoin());
            System.out.println("After gameover");
            pause();
        }


    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        //when the game is resumed
        //starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void start(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public boolean isPlaying(){
        return playing;
    }

    public void pause() {
        //when the game is paused
        playing = false;

    }

    public UserSpaceship getUserSpaceship() {
        return userSpaceship;
    }

}
