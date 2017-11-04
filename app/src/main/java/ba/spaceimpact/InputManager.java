package ba.spaceimpact;

/**
 * Created by busraarabaci on 02/11/2017.
 */

import ba.spaceimpact.GameObject.GameObject;
import ba.spaceimpact.GameObject.UserSpaceship;

public class InputManager{

    public static boolean contains( float x, float y, UserSpaceship userSpaceship){
        return userSpaceship.contains(x, y);
    }

}