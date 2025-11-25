package si.um.feri.ecsgame.ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import si.um.feri.ecsgame.assets.AssetPaths;
import si.um.feri.ecsgame.assets.GameAssets;
import si.um.feri.ecsgame.ecs.components.*;
import si.um.feri.ecsgame.ecs.util.Constants;
import si.um.feri.ecsgame.ecs.util.Mappers;

import java.util.Comparator;

public class HudSystem extends SortedIteratingSystem {
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final com.badlogic.gdx.graphics.Texture pixel;
    private final com.badlogic.gdx.graphics.Texture shieldTex;

    public HudSystem(SpriteBatch batch, GameAssets assets) {
        super(Family.all(PlayerComponent.class, HealthComponent.class, ManaComponent.class).get(),
            Comparator.comparingInt(e -> 0));
        this.batch = batch;
        this.font = assets.get(AssetPaths.FONT_ARIAL_32, BitmapFont.class);
        this.pixel = assets.get(AssetPaths.IMG_PIXEL, com.badlogic.gdx.graphics.Texture.class);
        this.shieldTex = assets.get(AssetPaths.IMG_SHIELD, com.badlogic.gdx.graphics.Texture.class);
    }

    @Override
    public void update(float deltaTime) {
        batch.begin();
        super.update(deltaTime);

        GameStateComponent gs = null;
        Engine eng = getEngine();
        for (Entity e : eng.getEntitiesFor(Family.all(GameStateComponent.class).get())) {
            gs = e.getComponent(GameStateComponent.class);
            break;
        }
        if (gs != null) {
            if (gs.paused) {
                font.setColor(Color.WHITE);
                font.draw(batch, "PAUSED", Constants.WORLD_WIDTH / 2f - 60, Constants.WORLD_HEIGHT / 2f);
            } else if (gs.gameOver) {
                font.setColor(Color.RED);
                font.draw(batch, "GAME OVER (press R to reset)", Constants.WORLD_WIDTH / 2f - 160, Constants.WORLD_HEIGHT / 2f);
            }
        }

        batch.end();
    }

    @Override
    protected void processEntity(Entity e, float dt) {
        HealthComponent h = Mappers.health.get(e);
        ManaComponent m = Mappers.mana.get(e);
        ShieldComponent s = Mappers.shield.get(e);
        ScoreComponent sc = Mappers.score.get(e);

        drawBar(20, Constants.WORLD_HEIGHT - 30, 200, 20, h.hp / 100f, Color.RED);
        drawBar(20, Constants.WORLD_HEIGHT - 60, 200, 15, m.mana / (float)m.max, Color.BLUE);

        font.setColor(Color.YELLOW);
        font.draw(batch, "Score: " + (sc != null ? sc.score : 0), 250, Constants.WORLD_HEIGHT - 15);
        font.draw(batch, "Hits: " + (sc != null ? sc.hits : 0), 250, Constants.WORLD_HEIGHT - 45);

        if (s != null && s.active) {
            float iconSize = 40f;
            float shieldX = 20f;
            float shieldY = Constants.WORLD_HEIGHT - 60f - 12f - iconSize;
            batch.setColor(1f, 1f, 1f, 1f);
            batch.draw(shieldTex, shieldX, shieldY, iconSize, iconSize);
        }
    }

    private void drawBar(float x, float y, float width, float height, float fill, Color color) {
        fill = Math.min(1f, Math.max(0f, fill));
        batch.setColor(Color.DARK_GRAY);
        batch.draw(pixel, x, y, width, height);
        batch.setColor(color);
        batch.draw(pixel, x, y, width * fill, height);
        batch.setColor(Color.WHITE);
    }
}
