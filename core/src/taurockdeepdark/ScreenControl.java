package taurockdeepdark;

import com.badlogic.gdx.Game;

/**
 * Created by Matthew Brock on 07/01/2015.
 */
public class ScreenControl extends Game {// basically holds an int
    int nScreen;
    @Override
    public void create() {
        nScreen = 1;
    }
    public void setnScreen(int nScreen_){
        nScreen = nScreen_;
    }
}
