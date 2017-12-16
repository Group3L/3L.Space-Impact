package ba.spaceimpact;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class GameOverActivity extends Activity {

    ImageButton menuButton, nextButton;
    ImageView result;
    TextView score, killCount, coinText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_over);

        boolean win = getIntent().getBooleanExtra("Result", true);
        int s = getIntent().getIntExtra("Score", 0);
        int k = getIntent().getIntExtra("Killed enemy", 0);
        final int c = getIntent().getIntExtra("Coin", 0);

        result = findViewById(R.id.result);
        if( !win )result.setImageResource(R.drawable.you_lose_img);


        score = findViewById(R.id.scoreText);
        score.setText("Score : " + s);

        killCount = findViewById(R.id.killCountText);
        killCount.setText("Killed enemy : " + k);

        coinText = findViewById(R.id.coinText);
        coinText.setText("Coin : " + c);

        menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameData.setCoin(c);
                GameData.save(GameOverActivity.this);
                Intent intent = new Intent(GameOverActivity.this, MainMenu.class);
                startActivity( intent);
                GameOverActivity.this.overridePendingTransition(0, 0);
            }
        });

        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GameData.setCoin(c);
                GameData.save(GameOverActivity.this);
                Intent intent = new Intent(GameOverActivity.this, GameActivity.class);
                startActivity( intent);
                GameOverActivity.this.overridePendingTransition(0, 0);
            }
        });

    }
}
