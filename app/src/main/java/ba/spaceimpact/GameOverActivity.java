package ba.spaceimpact;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends Activity {

    Button menuButton;
    TextView result, score, killCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_over);

        /*
        *         intent.putExtra("Result", win);
        intent.putExtra("Score", score);
        intent.putExtra("Killed enemy", killedEnemyCount);*/

        boolean win = getIntent().getBooleanExtra("Result", true);
        int s = getIntent().getIntExtra("Score", 0);
        int k = getIntent().getIntExtra("Killed enemy", 0);

        result = findViewById(R.id.textView);
        if( win )result.setText("YOU WON");
        else result.setText("YOU LOST");

        score = findViewById(R.id.scoreText);
        score.setText("Score : " + s);

        killCount = findViewById(R.id.killCountText);
        killCount.setText("Killed enemy : " + k);

        menuButton = (Button)findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameOverActivity.this, MainMenu.class);
                startActivity( intent);
                GameOverActivity.this.overridePendingTransition(0, 0);
            }
        });

    }
}
