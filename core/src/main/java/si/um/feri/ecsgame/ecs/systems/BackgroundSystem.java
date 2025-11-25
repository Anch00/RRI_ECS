package si.um.feri.ecsgame.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import si.um.feri.ecsgame.ecs.components.BackgroundComponent;
import si.um.feri.ecsgame.ecs.components.TextureComponent;
import si.um.feri.ecsgame.ecs.util.Constants;
import si.um.feri.ecsgame.ecs.util.Mappers;

public class BackgroundSystem extends IteratingSystem {
    private final SpriteBatch batch;

    public BackgroundSystem(SpriteBatch batch) {
        super(Family.all(BackgroundComponent.class, TextureComponent.class).get());
        this.batch = batch;
    }

    @Override
    protected void processEntity(Entity e, float dt) {
        TextureComponent t = Mappers.texture.get(e);
        // static full-screen draw
        batch.begin();
        batch.draw(t.region, 0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        batch.end();
    }
}
