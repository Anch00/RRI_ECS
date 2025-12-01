package si.um.feri.ecsgame.ecs.components;

import com.badlogic.ashley.core.Component;

public class GlitchComponent implements Component {
    public float timeRemaining = 0f;

    public static final float GLITCH_DURATION = 0.1f; // Kratka nevidnost, npr. 0.1s

    public static final float GLITCH_INTERVAL = 1.0f; // Vsako sekundo

    public float intervalTimer = GLITCH_INTERVAL;
}
