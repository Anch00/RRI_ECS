package si.um.feri.ecsgame.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;

public class ParticleComponent implements Component {
    public ParticleEffectPool pool;
    public ParticleEffectPool.PooledEffect effect;
    public boolean attachedToEntity = false;
    public float offsetX = 0, offsetY = 0;
}
