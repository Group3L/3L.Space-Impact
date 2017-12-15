package ba.spaceimpact;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.Serializable;

import ba.spaceimpact.GameObject.UserSpaceship;

public class GameActivity extends AppCompatActivity implements Serializable{

    GameEngine gameEngine;
    Button shoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      //  setContentView(R.layout.activity_game);
         setContentView( new GameView(this, this));
    }

    public void setGameEngine( GameEngine ge){
        this.gameEngine = ge;
    }

    public void gameOver( boolean win, int score, int killedEnemyCount){
        Log.d("GameOver", "game over");
        Intent intent = new Intent(this, GameOverActivity.class);
        intent.putExtra("Result", win);
        intent.putExtra("Score", score);
        intent.putExtra("Killed enemy", killedEnemyCount);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
    }

    public void pauseGame(){
        Log.d("Pause Game", "Game Paused");
        Intent intent = new Intent(this, PauseActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);

    }

    //disable the animation
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent(this, MainMenu.class);
            GameData.setUserSpaceship( new UserSpaceship(this,3, 1, 40));
            GameData.save(this);
            startActivity(intent);
            this.overridePendingTransition(0, 0);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
