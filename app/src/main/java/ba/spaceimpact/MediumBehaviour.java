package ba.spaceimpact;

/**
 * Created by busraarabaci on 15.12.2017.
 */

public class MediumBehaviour implements EnemyBehaviour {
    private static int count=0;
    private int maxNum = 50;

    @Override
    public float[] move(float x, float y, float speed) {

        if(count <= maxNum){
            float[] result = {x+10,y+speed};
            count++;
            return result;
        }
        else if(count >maxNum && count <= 2*maxNum){
            float[] result = {x-10,y+speed};
            count++;
            return result;
        }
        else{
            count=0;
            float[] result = {x,y+speed};
            return result;
        }

    }

    @Override
    public void shoot() {

    }
}
