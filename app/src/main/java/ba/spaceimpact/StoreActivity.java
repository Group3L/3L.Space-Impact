package ba.spaceimpact;

import android.app.Activity;
import android.app.Dialog;
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
import android.widget.Toast;

import ba.spaceimpact.GameObject.UserSpaceship;

public class StoreActivity extends Activity {

    GridLayout storeGrid;
    Dialog mydialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_store);

        storeGrid = (GridLayout)findViewById(R.id.storeid);
        mydialog = new Dialog(this);

    }


    public void setSingleEvent(View view){

        switch (view.getId()) {
            case R.id.bulleticon:
                mydialog.setContentView(R.layout.bullet_popup);
                mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mydialog.show();
                break;
            case R.id.lifeicon:
                mydialog.setContentView(R.layout.life_popup);
                mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mydialog.show();
                break;
            case R.id.spaceshipicon:
                mydialog.setContentView(R.layout.spaceship_popup);
                mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mydialog.show();
                break;
        }


    }

    public void lifeUpgrade(View view){

    }


}
