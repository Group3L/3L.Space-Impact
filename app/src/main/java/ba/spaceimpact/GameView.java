package ba.spaceimpact;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
        import android.view.Display;
        import android.view.MotionEvent;
        import android.view.SurfaceHolder;
        import android.view.SurfaceView;
        import android.view.WindowManager;

import ba.spaceimpact.GameObject.Bullet;
import ba.spaceimpact.GameObject.UserSpaceship;

/**
 * Created by pc on 31.10.2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    private GameEngine gameEngine;
    private Context context;
    private SurfaceHolder holder;
    private int touchCount;
    private boolean isUserSpaceshipMovable;
    public static int screenX,screenY;
    private GameActivity gameActivity;
    // The player's bullet
    // public static Bullet[] bullet = new Bullet[20];
    private int ilo = 0;

    public GameView(Context context, GameActivity gameActivity){
        super(context);
        this.context = context;
        holder = this.getHolder();
        holder.addCallback(this);
        touchCount = 0;
        isUserSpaceshipMovable = false;
        this.gameActivity = gameActivity;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        screenX = display.getWidth();
        screenY = display.getHeight();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        GameData.load(context);
       // gameEngine = new GameEngine(context, this, new UserSpaceship(context, 10, 4), gameActivity);
        if(GameData.getUserSpaceship(context) == null) System.out.println("Userspaceship is null");
        else System.out.println("User spaceship is not null");
        System.out.println(GameData.getUserSpaceship(context));
        gameEngine = new GameEngine(context, this, GameData.getUserSpaceship(context), gameActivity);
        /*for(int i = 0; i < bullet.length; i++){
            bullet[i] = new Bullet(screenY, true, 1);
        }*/
        gameEngine.setBackgroundImage(BitmapFactory.decodeResource(getResources(), R.drawable.levelback));
        gameActivity.setGameEngine( gameEngine);
        gameEngine.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        gameEngine.pause();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();
        int pointedId = event.getPointerId(pointerIndex);
        int maskedAction = event.getActionMasked();

        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN:
                if( InputManager.contains(event.getX(), event.getY(), gameEngine.getUserSpaceship()))
                    isUserSpaceshipMovable = true;
                touchCount++;

                break;
            case MotionEvent.ACTION_MOVE:  // a pointer was moved
                if( isUserSpaceshipMovable && gameEngine.isPlaying() && event.getY(0) <= screenY - gameEngine.getDockHeight()  ){
                    float X = event.getX(0);
                    float Y = event.getY(0);
                    gameEngine.getUserSpaceship().move( X - (gameEngine.getUserSpaceship().getWidth()/2),
                            Y - (gameEngine.getUserSpaceship().getWidth() /2));
                }else if(!gameEngine.isPlaying())gameEngine.resume();

                break;
            case MotionEvent.ACTION_POINTER_DOWN: {
                 if (event.getX(1) > gameEngine.getShootButtonRect().left && event.getY(1) < gameEngine.getShootButtonRect().top &&
                        event.getX(1) < gameEngine.getShootButtonRect().right && event.getY(1) > gameEngine.getShootButtonRect().bottom) {

                  /*  if (gameEngine.getUserSpaceship().getBulletCount() > 0 && bullet[ilo].shoot(gameEngine.getUserSpaceship().getX() + gameEngine.getUserSpaceship().getWidth() / 2, gameEngine.getUserSpaceship().getY(), bullet[ilo].UP)) {
                        ilo++;
                        gameEngine.getUserSpaceship().shoot();
                        if (ilo == bullet.length) {
                            ilo = 0;
                        }
                    }*/
                  gameEngine.shoot(false);
                }

                break;
            }
            case MotionEvent.ACTION_UP:
                Log.d("Motion Event", "Released");
                isUserSpaceshipMovable = false;

                gameEngine.pause();

                if(gameEngine.isPlaying())
                    gameActivity.pauseGame();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;

        }
        invalidate();

        return true;
    }


}
