package ba.spaceimpact;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import ba.spaceimpact.GameObject.UserSpaceship;

public class MainMenu extends Activity {

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);
    }

    public void startGame(View v){
        v.startAnimation(buttonClick);
        Intent intent = new Intent(this, LevelActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //Do nothing
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
