package ba.spaceimpact;

/**
 * Created by busraarabaci on 02/11/2017.
 */
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.graphics.Rect;
        import android.graphics.RectF;
        import android.graphics.drawable.Drawable;
        import android.util.Log;
        import android.view.Display;
        import android.view.SurfaceHolder;
        import android.view.SurfaceView;
        import android.view.WindowManager;

        import ba.spaceimpact.GameObject.Bullet;
        import ba.spaceimpact.GameObject.Collectable;
        import ba.spaceimpact.GameObject.EnemySpaceship;
        import ba.spaceimpact.GameObject.GameObject;
        import ba.spaceimpact.GameObject.PowerUp;
        import ba.spaceimpact.GameObject.SpaceShip;
        import ba.spaceimpact.GameObject.UserSpaceship;

        import java.io.Serializable;
        import java.util.ArrayList;
        import java.util.LinkedList;
        import java.util.Random;

        import static ba.spaceimpact.GameView.screenY;

/**
 * Created by pc on 31.10.2017.
 */

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
    private Bullet[] bullet = new Bullet[150];
    private int ilo = 0;//?

    public GameEngine(Context context, SurfaceView surfaceView, UserSpaceship userSpaceship, GameActivity gameActivity){
        this.context = context;
        this.surfaceView = surfaceView;
        surfaceHolder = surfaceView.getHolder();

        this.userSpaceship = userSpaceship;


        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        pixelX = display.getWidth();
        pixelY = display.getHeight();
      //  this.setEnemies( 5);
    //    this.setCollectables();

        gameObjects = LevelCreator.setGameObjects(context, userSpaceship, pixelX, pixelY, 35, 5, 1); //TODO level will be changed

        this.userSpaceship.move((this.userSpaceship.getWidth() + pixelX) / 2, (float)0.6 * pixelY);

        for(int i = 0; i < bullet.length; i++){
            bullet[i] = new Bullet(screenY, true, 1); //TODO 1 will be changed
        }

        System.out.println("X: " + (this.userSpaceship.getWidth() + pixelX) / 2);
        System.out.println("Y: " + (float)0.6 * pixelY);

        dockHeight = (float)(pixelY * DOCK_RATIO);
        shootButtonRect = new Rect(pixelX / 2, (int)(pixelY), (int)(pixelX ), (int)(pixelY - (0.5 * dockHeight)));
        this.gameActivity = gameActivity;
    }

    public void setBackgroundImage(Bitmap backgroundImage){
        background = backgroundImage;
    }

    public void shoot( boolean infiniteShoot){
        if (userSpaceship.isInfiniteShoot() == infiniteShoot && bullet[ilo].shoot(userSpaceship.getX() + userSpaceship.getWidth() / 2, userSpaceship.getY(), bullet[ilo].UP)) {
            ilo++;
            userSpaceship.shoot(!userSpaceship.isInfiniteShoot());
            if (ilo == bullet.length) {
                ilo = 0;
            }
        }

    }


    private void setEnemies( int enemyCount){
        for( int i = 0; i < enemyCount; i++) {
            float posX = (float) (Math.random() * (pixelX + 1));
            //float posY = (float)(-(Math.random() * (pixelY + 1)));
            //Enemies should appear out of screen and come to view
            float posY = -700;

            float posX2 = (float) (Math.random() * (pixelX + 1));
            //float posY = (float)(-(Math.random() * (pixelY + 1)));
            //Enemies should appear out of screen and come to view
            float posY2 = -700;

            Random random = new Random();

            if(random.nextInt(5) > 0){
                int speedY = random.nextInt(31) + 10;

                EnemySpaceship e = new EnemySpaceship(1, 2, posX, posY, 0, speedY, context);

                //Checking if new spaceship intersects with existing ones
                if (gameObjects.size() > 0) {
                    boolean cond = true;
                    for (int j = 0; j < gameObjects.size(); j++) {
                        if (e.getRect().intersect(gameObjects.get(j).getRect())) {
                            cond = false;
                        }
                    }
                    if (cond)
                        gameObjects.add(e);
                }
                else{
                    gameObjects.add(e);
                }
            }
            else{
                int speedY = random.nextInt(31) + 10;

                PowerUp e = null;
                int powerUpType = random.nextInt(4);
                switch (powerUpType){
                    case 0: e = new PowerUp(PowerUp.SHIELD, userSpaceship, posX, posY, 0, speedY); break;
                    case 1: e = new PowerUp(PowerUp.HEALTH_REGEN, userSpaceship, posX, posY, 0, speedY); break;
                    case 2: e = new PowerUp(PowerUp.EXTRA_POINT, userSpaceship, posX, posY, 0, speedY); break;
                    case 3: e = new PowerUp(PowerUp.INF_BULLET, userSpaceship, posX, posY, 0, speedY); break;
                }

                //Checking if new spaceship intersects with existing ones
                if (gameObjects.size() > 0) {
                    boolean cond = true;
                    for (int j = 0; j < gameObjects.size(); j++) {
                        if (e.getRect().intersect(gameObjects.get(j).getRect())) {
                            cond = false;
                        }
                    }
                    if (cond)
                        gameObjects.add(e);
                }
                else{
                    gameObjects.add(e);
                }

            }

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


    @Override
    public void run() {
        while (playing) {


            //to update the frame
            update();

            System.out.println("Insıde run");

            //to draw the frame
            draw();

            //to control
            control();
        }
    }


    public void draw(){
        if(surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();

            if(background != null){
                try{
                    canvas.drawBitmap(background, 0, 0, null);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

            String scoreStr = "" + userSpaceship.getScore();
            Paint p = new Paint();
            p.setTextSize(48f);
            p.setColor(Color.WHITE);
            canvas.drawText(scoreStr,50,50,p);

            userSpaceship.draw(canvas);

            for (int i = 0; i < gameObjects.size(); i++){
                gameObjects.get(i).draw(canvas);
            }

            Paint paint = new Paint();
            paint.setColor(Color.YELLOW);
            for(int i = 0; i < bullet.length; i++){
                if(bullet[i].getStatus()) {
                    canvas.drawRect(bullet[i].getRect(), paint);
                }
            }

            drawBottomDock( canvas);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
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

        canvas.drawText("Health: " + userSpaceship.getHealth(), 10, pixelY - dockHeight + 50, textColor);
        canvas.drawText("Ammo: " + userSpaceship.getBulletCount(), 10, pixelY - 10, textColor);

    }

    public Rect getShootButtonRect() {
        return shootButtonRect;
    }

    public double getDockHeight() {
        return dockHeight;
    }

    public void update(){

        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).update();
            //if the object gets out of the frame dispose it
            if (!gameObjects.get(i).getVisible()) {
                gameObjects.remove(i);
                userSpaceship.increaseScore(ENEMY_KILL_SCORE);
                i--;
            }

        }

        //if the infinite shoot powerup is active, then the spacship will shoot automatically
        //and it won't use user's bullets
        shoot(true);

        // Update the players bullet
        for(int i = 0; i < bullet.length; i++){
            if(bullet[i].getStatus()) {
                bullet[i].update(60);
            }
        }

        // Has the player's bullet hit the top of the screen
        for(int i = 0; i < bullet.length; i++){
            if(bullet[i].getImpactPointY() < 0){
                bullet[i].setInactive();
            }
        }

        //collision between bullets and spaceships
       for( int j = 0; j < gameObjects.size(); j++){
            if( gameObjects.get(j) instanceof EnemySpaceship){
                for( int i = 0; i < bullet.length; i++){
                    if(bullet[i].getStatus() && gameObjects.get(j).getRect().intersect(bullet[i].getRect())){
                        Log.d("Collision","Between Enemy and Bullet");
                        ((EnemySpaceship) gameObjects.get(j)).getHit(bullet[i].getDamage());
                        bullet[i].setInactive();
                    }
                }
            }
        }


        //collision detection
        for (int i = 0; i < gameObjects.size(); i++) {

            if(isEnemyLeft() && userSpaceship.getRect().intersect(gameObjects.get(i).getRect())){
                if(gameObjects.get(i) instanceof EnemySpaceship && !userSpaceship.isInvincible()) userSpaceship.getHit(1); // if shield powerup is not on//constant value for now
                else if( gameObjects.get(i) instanceof PowerUp)((PowerUp) gameObjects.get(i)).powerUp(userSpaceship);
                gameObjects.remove(i);
                i--;
            }
        }
        if(!isEnemyLeft()) {
            Random random = new Random();
            int enemyNum = random.nextInt(10) + 1;
            setEnemies(enemyNum);
        }

        if( userSpaceship.getHealth() <= 0){
            gameActivity.gameOver();
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
