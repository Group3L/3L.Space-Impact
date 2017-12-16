package ba.spaceimpact;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LevelActivity extends Activity {
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_level);
    }

    public void startLevel(View v){
        v.startAnimation(buttonClick);

        String level="";
        Intent intent = new Intent(this , StartLevelActivity.class);

        int level_id = v.getId();
        switch (level_id){
            case R.id.level1_button:
                level = "LEVEL 1";
                intent.putExtra("LEVEL", level);
                intent.putExtra("LEVELNO", 1);
                startActivity(intent);
                this.overridePendingTransition(0, 0);
                break;
            case R.id.level2_button:
                level = "LEVEL 2";
                intent.putExtra("LEVEL", level);
                intent.putExtra("LEVELNO", 2);
                startActivity(intent);
                this.overridePendingTransition(0, 0);
                break;
            case R.id.level3_button:
                level = "LEVEL 3";
                intent.putExtra("LEVEL", level);
                intent.putExtra("LEVELNO", 3);
                startActivity(intent);
                this.overridePendingTransition(0, 0);
                break;
            case R.id.level4_button:
                level = "LEVEL 4";
                intent.putExtra("LEVEL", level);
                intent.putExtra("LEVELNO", 4);
                startActivity(intent);
                this.overridePendingTransition(0, 0);
                break;
        }



    }

    //disable the animation
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
            this.overridePendingTransition(0, 0);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
