package ba.spaceimpact;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import ba.spaceimpact.GameObject.Coin;
import ba.spaceimpact.GameObject.EnemySpaceship;
import ba.spaceimpact.GameObject.GameObject;
import ba.spaceimpact.GameObject.PowerUp;
import ba.spaceimpact.GameObject.UserSpaceship;


public class LevelCreator {

    private static final int POSITION_Y_BOUND = 15000;
    //percentages of enemy occurence during the game
    private static int[][] ENEMY_BEHAVIOUR_RATIO = {{75, 25, 0}, {50, 25, 25}, {25, 25, 50}, {0, 50, 50}};

    public static ArrayList<GameObject> setGameObjects(Context context, UserSpaceship userSpaceship, int pixelX, int pixelY, int level){
        ArrayList<GameObject> gameObjects = new ArrayList<>();
        Random random = new Random();

        Log.d("LEVEL", level + "");
        int easyRatio = ENEMY_BEHAVIOUR_RATIO[level - 1][0], mediumRatio = easyRatio + ENEMY_BEHAVIOUR_RATIO[level-1][1];
        int enemyCount = level * 30;
        int coinNumber = random.nextInt(11) + 10;
        int powerupCount = level * 6;

        float posX;
        float posY2;
        //Enemies should appear out of screen and come to view
        float posY;
        float posX2;
        int speedY;

        int typeRand;

        for( int i = 0; i < enemyCount; i++) {
            posX = (float) (Math.random() * (pixelX + 1));

            //Enemies should appear out of screen and come to view
            posY = -random.nextInt(POSITION_Y_BOUND) - 3000;

            posX2 = (float) (Math.random() * (pixelX + 1));
            //Enemies should appear out of screen and come to view
            speedY = random.nextInt(21) + 30;

            typeRand = random.nextInt(101);
            EnemySpaceship e = null;

            if( typeRand < easyRatio) e = new EnemySpaceship(1, 1, posX, posY, 0, speedY, context, EnemySpaceship.enemyType.EASY);
            else if( typeRand < mediumRatio) e = new EnemySpaceship(2, 2, posX, posY, 0, speedY, context, EnemySpaceship.enemyType.MEDIUM);
            else e = new EnemySpaceship(5, 5, posX, posY, 0, speedY, context, EnemySpaceship.enemyType.HARD);

            //Checking if new spaceship intersects with existing ones
            gameObjects = positionCheck(e, gameObjects);
        }

        for(int i = 0; i < powerupCount; i++){
            speedY = random.nextInt(31) + 10;
            posX = (float) (Math.random() * (pixelX + 1));
            posY = -random.nextInt(POSITION_Y_BOUND) - 1000;
            posX2 = (float) (Math.random() * (pixelX + 1));
            posY2 = -700;

            PowerUp e = null;
            int powerUpType = random.nextInt(5);
            switch (powerUpType){
                case 0: e = new PowerUp(context, PowerUp.SHIELD, userSpaceship, posX, posY, 0, speedY); break;
                case 1: e = new PowerUp(context, PowerUp.HEALTH_REGEN, userSpaceship, posX, posY, 0, speedY); break;
                case 2: e = new PowerUp(context, PowerUp.EXTRA_POINT, userSpaceship, posX, posY, 0, speedY); break;
                case 3: e = new PowerUp(context, PowerUp.INF_BULLET, userSpaceship, posX, posY, 0, speedY); break;
                case 4: e = new PowerUp(context, PowerUp.EXTRA_BULLET, userSpaceship, posX, posY, 0, speedY); break;
            }

        }

        for(int i = 0; i < coinNumber; i++){
            speedY = random.nextInt(31) + 10;
            posX = (float) (Math.random() * (pixelX + 1));
            posY = -random.nextInt(POSITION_Y_BOUND) - 1000;
            posX2 = (float) (Math.random() * (pixelX + 1));
            posY2 = -700;

            Coin coin = new Coin( context,random.nextInt(6) + 5, userSpaceship, posX, posY, 0, speedY);

            //Checking if new object intersects with existing ones
            gameObjects = positionCheck(coin, gameObjects);
        }

        return gameObjects;
    }

    private static ArrayList<GameObject> positionCheck(GameObject gameObject, ArrayList<GameObject> gameObjects){
        if (gameObjects.size() > 0) {
            boolean cond = true;
            for (int j = 0; j < gameObjects.size(); j++) {
                if (gameObject.getRect().intersect(gameObjects.get(j).getRect())) {
                    cond = false;
                }
            }
            if (cond)
                gameObjects.add(gameObject);
        }
        else{
            gameObjects.add(gameObject);
        }

        return gameObjects;
    }



}
