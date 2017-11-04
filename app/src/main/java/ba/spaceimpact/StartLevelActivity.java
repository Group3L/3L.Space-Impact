package ba.spaceimpact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

public class StartLevelActivity extends Activity {

    TextView levelText;
    Button levelStartButton;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_level);

        Bundle extras = getIntent().getExtras();
        String level = extras.getString("LEVEL");


        levelStartButton = (Button) findViewById(R.id.startlevel_button);
        levelText = (TextView) findViewById(R.id.level_tv);

        levelText.setText(level);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.4));
    }

    public void startLevel(View v){
        v.startAnimation(buttonClick);

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);

    }
}
