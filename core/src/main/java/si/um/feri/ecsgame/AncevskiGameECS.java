package si.um.feri.ecsgame;

import com.badlogic.gdx.Game;
import si.um.feri.ecsgame.assets.GameAssets;
import si.um.feri.ecsgame.screens.LoadingScreen;

public class AncevskiGameECS extends Game {
    public GameAssets assets;

    @Override
    public void create() {
        assets = new GameAssets();
        setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        if (assets != null) assets.dispose();
    }
}
