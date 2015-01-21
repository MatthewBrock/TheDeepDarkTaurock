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
        file = Gdx.files.local("save.json");//lets you reference a file
        character = new Character();
        json = new Json();//used to convert to json and from json
    }

    public void setMainCharacter(MainCharacter mainCharacter_) {
        mainCharacter = mainCharacter_;
    }

    public void save() {
        character.set(mainCharacter.fCharacterX, mainCharacter.fCharacterY, mainCharacter.nCurrentMap, mainCharacter.sName);//fills the character object with the information to be saved
        sCharacter = json.toJson(character);//turns the character object into a json list that holds the variable names and values
        file.writeString(sCharacter, false);//write the string to the save file the false me overwrite it
    }

    public void load() {
        sCharacter = file.readString();//read the text out of the save file
        character = json.fromJson(Character.class, sCharacter);//use the json object to pull the information out of the string and out it into the character object
        mainCharacter.fCharacterX = character.fX;//this is taking from the character object and setting the values in the mainCharacter object
        mainCharacter.fCharacterY = character.fY;
        mainCharacter.nCurrentMap = character.nMap;
        mainCharacter.setCharacter(character.sName);
    }
}






