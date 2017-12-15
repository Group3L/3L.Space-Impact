package ba.spaceimpact;

/**
 * Created by busraarabaci on 14.12.2017.
 */

public class EasyBehaviour implements EnemyBehaviour{

    @Override
    public float[] move(float x,float y,float speed){
        float[] result = {x,y+speed};
        return result;
    }

    @Override
    public void shoot(){

    }
}
