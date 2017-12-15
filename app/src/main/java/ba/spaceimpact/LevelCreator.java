package ba.spaceimpact;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

import ba.spaceimpact.GameObject.EnemySpaceship;
import ba.spaceimpact.GameObject.GameObject;
import ba.spaceimpact.GameObject.PowerUp;
import ba.spaceimpact.GameObject.UserSpaceship;

/**
 * Created by pc on 15.12.2017.
 */

public class LevelCreator {

    public static ArrayList<GameObject> setGameObjects(Context context, UserSpaceship userSpaceship, int pixelX, int pixelY, int enemyCount, int powerupCount, int level){
        ArrayList<GameObject> gameObjects = new ArrayList<>();
        Random random = new Random();

        for( int i = 0; i < enemyCount; i++) {
            float posX = (float) (Math.random() * (pixelX + 1));
            //float posY = (float)(-(Math.random() * (pixelY + 1)));
            //Enemies should appear out of screen and come to view
            float posY = -700;

            float posX2 = (float) (Math.random() * (pixelX + 1));
            //float posY = (float)(-(Math.random() * (pixelY + 1)));
            //Enemies should appear out of screen and come to view
            float posY2 = -700;

            int speedY = random.nextInt(31) + 10;

            EnemySpaceship e = new EnemySpaceship(1, 2, posX, posY, 0, speedY, context);

            //Checking if new spaceship intersects with existing ones
            if (gameObjects.size() > 0) {
                boolean cond = true;
                for (int j = 0; j < gameObjects.size(); j++) {
                    if (e.getRect().intersect(gameObjects.get(j).getRect())) {
                        cond = false;
                    }
                }
                if (cond)
                    gameObjects.add(e);
            }
            else{
                gameObjects.add(e);
            }
        }

        for(int i = 0; i < powerupCount; i++){
            int speedY = random.nextInt(31) + 10;
            float posX = (float) (Math.random() * (pixelX + 1));
            float posY = -700;
            float posX2 = (float) (Math.random() * (pixelX + 1));
            float posY2 = -700;

            PowerUp e = null;
            int powerUpType = random.nextInt(5);
            switch (powerUpType){
                case 0: e = new PowerUp(PowerUp.SHIELD, userSpaceship, posX, posY, 0, speedY); break;
                case 1: e = new PowerUp(PowerUp.HEALTH_REGEN, userSpaceship, posX, posY, 0, speedY); break;
                case 2: e = new PowerUp(PowerUp.EXTRA_POINT, userSpaceship, posX, posY, 0, speedY); break;
                case 3: e = new PowerUp(PowerUp.INF_BULLET, userSpaceship, posX, posY, 0, speedY); break;
                case 4: e = new PowerUp(PowerUp.EXTRA_BULLET, userSpaceship, posX, posY, 0, speedY); break;
            }

            //Checking if new spaceship intersects with existing ones
            if (gameObjects.size() > 0) {
                boolean cond = true;
                for (int j = 0; j < gameObjects.size(); j++) {
                    if (e.getRect().intersect(gameObjects.get(j).getRect())) {
                        cond = false;
                    }
                }
                if (cond)
                    gameObjects.add(e);
            }
            else{
                gameObjects.add(e);
            }

        }

        return gameObjects;
    }

}
