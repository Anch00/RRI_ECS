package si.um.feri.ecsgame.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class GameAssets implements AutoCloseable {
    public final AssetManager manager = new AssetManager();

    public void queueLoading() {
        manager.load(AssetPaths.IMG_BACKGROUND, Texture.class);
        manager.load(AssetPaths.IMG_WIZARD, Texture.class);
        manager.load(AssetPaths.IMG_CRYSTAL, Texture.class);
        manager.load(AssetPaths.IMG_POTION, Texture.class);
        manager.load(AssetPaths.IMG_DARK_ORB, Texture.class);
        manager.load(AssetPaths.IMG_FIREBALL, Texture.class);
        manager.load(AssetPaths.IMG_SHIELD, Texture.class);
        manager.load(AssetPaths.IMG_PIXEL, Texture.class);

        manager.load(AssetPaths.SND_COLLECT, Sound.class);
        manager.load(AssetPaths.SND_POTION, Sound.class);
        manager.load(AssetPaths.SND_SHOOT, Sound.class);
        manager.load(AssetPaths.SND_HIT, Sound.class);
        manager.load(AssetPaths.SND_GAMEOVER, Sound.class);
        manager.load(AssetPaths.SND_POWERUP, Sound.class);

        manager.load(AssetPaths.FONT_ARIAL_32, BitmapFont.class);

        manager.load(AssetPaths.PFX_FIREBALL_TRAIL, ParticleEffect.class);
    }

    public <T> T get(String path, Class<T> type) {
        return manager.get(path, type);
    }

    public boolean update() { return manager.update(); }
    public float getProgress() { return manager.getProgress(); }

    public void dispose() { manager.dispose(); }

    @Override public void close() { dispose(); }
}
