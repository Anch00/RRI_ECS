package si.um.feri.ecsgame.ecs.components;

import com.badlogic.ashley.core.Component;

public class SpawnComponent implements Component {
    public float timer;
    public float interval;
    // 0=crystal,1=potion,2=darkOrb,3=shield
    public int type;
}
