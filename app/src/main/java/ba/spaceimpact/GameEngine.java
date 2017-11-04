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

        import ba.spaceimpact.GameObject.EnemySpaceship;
        import ba.spaceimpact.GameObject.GameObject;
        import ba.spaceimpact.GameObject.SpaceShip;
        import ba.spaceimpact.GameObject.UserSpaceship;

        import java.util.ArrayList;
        import java.util.LinkedList;
        import java.util.Random;

/**
 * Created by pc on 31.10.2017.
 */

public class GameEngine implements Runnable {

    private boolean playing;
    private Thread gameThread;
    private Canvas canvas;
    public static LinkedList<GameObject> gameObjects;
    private Context context;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    public static UserSpaceship userSpaceship;
    private Bitmap background;
    private int pixelX, pixelY;
    private int score = 1000;
    private float dockHeight;
    public static final double DOCK_RATIO = 0.2;
    private Rect shootButtonRect;
    private GameActivity gameActivity;

    public GameEngine(Context context, SurfaceView surfaceView, UserSpaceship userSpaceship, GameActivity gameActivity){
        this.context = context;
        this.surfaceView = surfaceView;
        surfaceHolder = surfaceView.getHolder();

        gameObjects = new LinkedList<>();
        this.userSpaceship = userSpaceship;

        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        pixelX = display.getWidth();
        pixelY = display.getHeight();
        this.setEnemies( 5);

        dockHeight = (float)(pixelY * DOCK_RATIO);
        shootButtonRect = new Rect(pixelX / 2, (int)(pixelY), (int)(pixelX ), (int)(pixelY - (0.5 * dockHeight)));
        this.gameActivity = gameActivity;
    }

    public void setBackgroundImage(Bitmap backgroundImage){
        background = backgroundImage;
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
            int speedY = random.nextInt(31) + 10;

            EnemySpaceship e = new EnemySpaceship(5, 2, posX, posY, 0, speedY, context);

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
            //to draw the frame
            draw();

            //to update the frame
            update();

            //check collisions


            //to control
            control();
        }
    }

    public UserSpaceship getUserSpaceship() {
        return userSpaceship;
    }

    public void draw(){
        if(surfaceHolder.getSurface().isValid()){
            canvas = surfaceHolder.lockCanvas();

            if(background != null){
                canvas.drawBitmap(background, 0, 0, null);
            }

            String scoreStr = "" + score;
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
            for(int i = 0; i < GameView.bullet.length; i++){
                if(GameView.bullet[i].getStatus()) {
                    canvas.drawRect(GameView.bullet[i].getRect(), paint);
                }
            }

            drawBottomDock( canvas, dockHeight, 40, 50);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawBottomDock( Canvas canvas, float dockH, int bulletCount, int health){
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
                i--;

            }
        }



        // Update the players bullet
        for(int i = 0; i < GameView.bullet.length; i++){
            if(GameView.bullet[i].getStatus()) {
                GameView.bullet[i].update(60);
            }
        }


        // Has the player's bullet hit the top of the screen
        for(int i = 0; i < GameView.bullet.length; i++){
            if(GameView.bullet[i].getImpactPointY() < 0){
                GameView.bullet[i].setInactive();
            }
        }

        for (int i = 0; i < gameObjects.size(); i++) {

            if(isEnemyLeft() && userSpaceship.getRect().intersect(gameObjects.get(i).getRect())){
                Log.d("Collision", "Collision between enemy and user");
                userSpaceship.getHit(1); //constant value for now
                gameObjects.remove(i);
                i--;
                score--;
            }


        }
        if(!isEnemyLeft()) {
            Random random = new Random();
            int enemyNum = random.nextInt(10) + 1;
            setEnemies(enemyNum);
        }

   /*     if( userSpaceship.getHealth() <= 0){

            gameActivity.gameOver();
            pause();
        }
    */
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
        //setting the variable to false
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

}
