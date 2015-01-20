package taurockdeepdark;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;


/**
 * Created by Matthew Brock on 19/01/2015.
 */
//https://github.com/libgdx/libgdx/wiki/Reading-&-writing-JSON
//http://stackoverflow.com/questions/22802572/write-to-json-using-libgdx
public class Loader extends Game {
    MainCharacter mainCharacter;
    FileHandle file;
    Character character;
    Json json;
    String sCharacter;

    @Override
    public void create() {
        file = Gdx.files.local("save.json");
        character = new Character();
        json = new Json();
    }

    public void setMainCharacter(MainCharacter mainCharacter_) {
        mainCharacter = mainCharacter_;
    }

    public void save() {
        character.set(mainCharacter.fCharacterX, mainCharacter.fCharacterY, mainCharacter.nCurrentMap, mainCharacter.sName);
        sCharacter = json.toJson(character);
        file.writeString(sCharacter, false);
    }

    public void load() {
        sCharacter = file.readString();
        character = json.fromJson(Character.class, sCharacter);
        mainCharacter.fCharacterX = character.fX;
        mainCharacter.fCharacterY = character.fY;
        mainCharacter.nCurrentMap = character.nMap;
        mainCharacter.setCharacter(character.sName);
    }
}






