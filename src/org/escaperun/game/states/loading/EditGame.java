package org.escaperun.game.states.loading;

import org.escaperun.game.Keyboard;
import org.escaperun.game.model.Stage;
import org.escaperun.game.serialization.SaveManager;
import org.escaperun.game.states.GameState;
import org.escaperun.game.states.Option;
import org.escaperun.game.states.mapcreation.CreateMap;
import org.escaperun.game.view.Decal;
import org.escaperun.game.view.GameWindow;
import org.escaperun.game.states.mainmenu.MainMenu;

import java.io.File;

public class EditGame extends GameState {
    private static final int TICKS_PER_MOVEMENT = 10;
    private static final String MAP_FOLDER = "/maps/";
    private static final File MAP_DIRECTORY = new File(System.getProperty("user.dir") + MAP_FOLDER);
    private Option[] options;
    private int selected;
    private int ticksSince;
    private String[] filesnames;
    private MainMenu from;

    public EditGame(MainMenu from) {
        selected = 0;
        ticksSince = 0;
        this.from = from;

        //Get filenames
        try {
            filesnames = MAP_DIRECTORY.list();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //Create filename options.
        options = new Option[filesnames.length + 1];
        for (int i = 1; i < options.length; i++){
            options[i] = new Option(filesnames[i-1],null);    //using for decals, not to go to next state.
        }
        options[0] = new Option("Back to Main Menu", from);
    }

    @Override
    public GameState update(boolean[] pressed) {
        boolean up = pressed[Keyboard.UP];
        boolean down = pressed[Keyboard.DOWN];

        int nextIdx = selected;

        if (up) nextIdx--;
        if (down) nextIdx++;
        if ( nextIdx >= 0 && (nextIdx < options.length) && ticksSince >= TICKS_PER_MOVEMENT && (up || down)) {
            selected = nextIdx;
            ticksSince = 0;
        }
        ++ticksSince;

        boolean enter = pressed[Keyboard.ENTER];
        if (enter) {
            GameState next;
            if (selected != 0) {
                String path = System.getProperty("user.dir") + "\\maps\\" + filesnames[selected -1];
                Stage stage = SaveManager.loadStage(path);
                if (stage != null)
                    next = new CreateMap(stage);
                else {
                    options[selected] = new Option("ERROR LOADING THIS FILE", null);
                    next = null;
                }
            }
            else
                next = from;
            pressed[Keyboard.ENTER] = false;
            return next;
        }
        return null;
    }

    @Override
    /**
     * Currently Assumes number of files do not exceed size of window.
     */
    public Decal[][] getRenderable() {
        Decal[][] ret = new Decal[GameWindow.ROWS][GameWindow.COLUMNS];
        Decal[] button;

        //Display back option
        if (selected == 0)
            button = options[0].selected[0];
        else
            button = options[0].unselected[0];

        int y = 3;
        int x = GameWindow.COLUMNS/2 - options[0].text.length()/2;
        for (int i = 0; i < button.length; i++)
            ret[y][x + i] = button[i];

        //Display files.
        y += 2;
        for (int i = 1; i < options.length; i++){
            x = GameWindow.COLUMNS/2 - options[i].text.length()/2;
            if (i == selected)
                button = options[i].selected[0];
            else
                button = options[i].unselected[0];

            for (int j = 0; j < button.length; j++)
                ret[y][x + j] = button[j];
            y++;
        }
        return ret;
    }
}
