package ba.spaceimpact;

import android.util.Log;

import ba.spaceimpact.GameObject.UserSpaceship;

/**
 * Created by busraarabaci on 15.12.2017.
 */

public class HardBehaviour implements EnemyBehaviour{
    @Override
    public float[] move(float x, float y, float speed) {
        float aimX = GameEngine.userSpaceship.getX();
        if(aimX > x) {
            float[] result = {x + 5, y + speed};
            Log.d("HARD","HARD");
            return result;

        }
        else if(aimX < x) {
            float[] result = {x - 5, y + speed};
            Log.d("HARD","HARD");
            return result;
        }
        else{
            float[] result = {x,y+speed};
            Log.d("HARD","HARD");
            return result;
        }

    }

    @Override
    public void shoot() {

    }
}
