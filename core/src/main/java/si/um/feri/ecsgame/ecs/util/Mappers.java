package si.um.feri.ecsgame.ecs.util;

import com.badlogic.ashley.core.ComponentMapper;
import si.um.feri.ecsgame.ecs.components.*;

public final class Mappers {
    private Mappers() {}
    public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<VelocityComponent> velocity = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<TextureComponent> texture = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper<BoundsComponent> bounds = ComponentMapper.getFor(BoundsComponent.class);
    public static final ComponentMapper<PlayerComponent> player = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<EnemyComponent> enemy = ComponentMapper.getFor(EnemyComponent.class);
    public static final ComponentMapper<ProjectileComponent> projectile = ComponentMapper.getFor(ProjectileComponent.class);
    public static final ComponentMapper<HealthComponent> health = ComponentMapper.getFor(HealthComponent.class);
    public static final ComponentMapper<ManaComponent> mana = ComponentMapper.getFor(ManaComponent.class);
    public static final ComponentMapper<ParticleComponent> particle = ComponentMapper.getFor(ParticleComponent.class);
    public static final ComponentMapper<BackgroundComponent> background = ComponentMapper.getFor(BackgroundComponent.class);
    public static final ComponentMapper<ZOrderComponent> z = ComponentMapper.getFor(ZOrderComponent.class);
    public static final ComponentMapper<TTLComponent> ttl = ComponentMapper.getFor(TTLComponent.class);
    public static final ComponentMapper<ShieldComponent> shield = ComponentMapper.getFor(ShieldComponent.class);
    public static final ComponentMapper<ScoreComponent> score = ComponentMapper.getFor(ScoreComponent.class);
    public static final ComponentMapper<ItemComponent> item = ComponentMapper.getFor(ItemComponent.class);
    public static final ComponentMapper<SpawnComponent> spawn = ComponentMapper.getFor(SpawnComponent.class);
}
