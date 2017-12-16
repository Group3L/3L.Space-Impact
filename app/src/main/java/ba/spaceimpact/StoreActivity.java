package ba.spaceimpact;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import ba.spaceimpact.GameObject.UserSpaceship;

public class StoreActivity extends Activity {

    GridLayout storeGrid;
    Dialog mydialog;
    TextView coinText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_store);
        coinText = findViewById(R.id.coinText);
        GameData.load(this);
        coinText.setText("Coin : " + GameData.getCoin());
        storeGrid = (GridLayout)findViewById(R.id.storeid);
        mydialog = new Dialog(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        GameData.load(this);
        coinText.setText("Coin : " + GameData.getCoin());
    }

    public void setSingleEvent(View view){

        switch (view.getId()) {
            case R.id.bulleticon:
                mydialog.setContentView(R.layout.bullet_popup);
                mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button b = mydialog.findViewById(R.id.bulletbuttonupgrade);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GameData.load(StoreActivity.this);
                        int coin = GameData.getCoin();
                        if(coin >= Store.BULLET_UPGRADE_PRICE)
                            Store.addBulletUpgrade(StoreActivity.this);
                        else{
                            Toast.makeText(StoreActivity.this, "You don't have enough coin.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mydialog.show();
                break;
            case R.id.lifeicon:
                mydialog.setContentView(R.layout.life_popup);
                mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button b2 = mydialog.findViewById(R.id.lifebuttonupgrade);
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GameData.load(StoreActivity.this);
                        int coin = GameData.getCoin();
                        if(coin >= Store.HEALTH_UPGRADE_PRICE)
                            Store.healthUpgrade(StoreActivity.this);
                        else{
                            Toast.makeText(StoreActivity.this, "You don't have enough coin.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mydialog.show();
                break;
            case R.id.spaceshipicon:
                mydialog.setContentView(R.layout.spaceship_popup);
                mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button b3 = mydialog.findViewById(R.id.spaceshipbuttonupgrade);
                b3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Store.shootingStyleUpgrade(StoreActivity.this);
                    }
                });
                mydialog.show();
                break;
        }


    }

    public void lifeUpgrade(View view){
        Store.healthUpgrade(this);
    }

    public void addBulletUpgrade(View view){
        Store.addBulletUpgrade(this);
    }

    public void shootingStyleUpgrade(View view){
        Store.shootingStyleUpgrade(this);
    }


}
