package ba.spaceimpact;


import ba.spaceimpact.GameObject.GameObject;
import ba.spaceimpact.GameObject.UserSpaceship;

public class InputManager{

    public static boolean contains( float x, float y, UserSpaceship userSpaceship){
        return userSpaceship.contains(x, y);
    }

}