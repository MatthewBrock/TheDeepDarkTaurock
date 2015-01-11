package taurockdeepdark;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;


/**
 * Created by Matthew Brock on 07/01/2015.
 */
public class Main extends Game {//http://stackoverflow.com/questions/24551605/libgdx-input-processor-not-working-with-group
    GameScreen gameScreen;
    MainMenu mainMenu;
    ScreenControl screenControl;
    CharacterSelect characterSelect;
    int nScreen;

    @Override
    public void create() {
        Gdx.input.setCatchBackKey(true);// lets you use the back button without it just taking you out of the game
        screenControl = new ScreenControl();
        gameScreen = new GameScreen();
        mainMenu = new MainMenu();
        characterSelect = new CharacterSelect();
        screenControl.create();
        gameScreen.create();
        mainMenu.create();
        characterSelect.create();
        characterSelect.setScreenControl(screenControl);// screen control is just used to set the screen that is being rendered
        mainMenu.setScreenControl(screenControl);
        gameScreen.controls.setScreenControl(screenControl);
        characterSelect.setGameScreen(gameScreen);
    }

    @Override
    public void render() {
        nScreen = screenControl.nScreen;
        if (nScreen == 1) {
            mainMenu.render();
        } else if (nScreen == 2) {
            characterSelect.render();
        } else if (nScreen == 3) {
            gameScreen.render();
        }
    }
}