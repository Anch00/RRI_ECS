package si.um.feri.ecsgame.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import si.um.feri.ecsgame.ecs.components.GlitchComponent;
import si.um.feri.ecsgame.ecs.components.PlayerComponent;
import si.um.feri.ecsgame.ecs.components.TextureComponent;

public class GlitchSystem extends IteratingSystem {

    private final ComponentMapper<GlitchComponent> glitchMapper = ComponentMapper.getFor(GlitchComponent.class);

    public GlitchSystem() {
        super(Family.all(PlayerComponent.class, GlitchComponent.class, TextureComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        GlitchComponent glitch = glitchMapper.get(entity);

        glitch.intervalTimer -= deltaTime;

        if (glitch.intervalTimer <= 0) {
            glitch.timeRemaining = GlitchComponent.GLITCH_DURATION;
            glitch.intervalTimer += GlitchComponent.GLITCH_INTERVAL;

            if (glitch.timeRemaining < 0) {
                glitch.timeRemaining = 0.001f;
            }
        }

        if (glitch.timeRemaining > 0) {
            glitch.timeRemaining -= deltaTime;

            // Logika nevidnosti (pusti to RenderingSystemu, da preveri glitch.timeRemaining)
        }
    }
}
