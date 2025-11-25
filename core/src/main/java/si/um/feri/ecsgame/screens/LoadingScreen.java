package si.um.feri.ecsgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import si.um.feri.ecsgame.AncevskiGameECS;

public class LoadingScreen extends ScreenAdapter {
    private final AncevskiGameECS game;

    public LoadingScreen(AncevskiGameECS game) {
        this.game = game;
        game.assets.queueLoading();
    }

    @Override
    public void render(float delta) {
        if (game.assets.update()) {
            game.setScreen(new GameScreen(game));
            return;
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
