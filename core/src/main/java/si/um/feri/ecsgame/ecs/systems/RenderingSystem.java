package si.um.feri.ecsgame.ecs.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import si.um.feri.ecsgame.ecs.components.PositionComponent;
import si.um.feri.ecsgame.ecs.components.TextureComponent;
import si.um.feri.ecsgame.ecs.components.ZOrderComponent;
import si.um.feri.ecsgame.ecs.util.Mappers;
import si.um.feri.ecsgame.ecs.components.GlitchComponent;
import java.util.Comparator;

public class RenderingSystem extends SortedIteratingSystem {

    private final SpriteBatch batch;

    private final ComponentMapper<GlitchComponent> glitchMapper = ComponentMapper.getFor(GlitchComponent.class);


    public RenderingSystem(SpriteBatch batch) {
        super(Family.all(PositionComponent.class, TextureComponent.class, ZOrderComponent.class).get(),
            Comparator.comparingInt(e -> {
                ZOrderComponent z = Mappers.z.get(e);
                return z.z;
            }));
        this.batch = batch;
    }


    @Override
    public void update(float dt) {
        batch.begin();
        super.update(dt);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float dt) {
        GlitchComponent glitch = glitchMapper.get(entity);

        if (glitch != null && glitch.timeRemaining > 0) {
            return;
        }

        PositionComponent p = Mappers.position.get(entity);
        TextureComponent t = Mappers.texture.get(entity);

        batch.draw(t.region, p.x, p.y, t.width, t.height);
    }
}
