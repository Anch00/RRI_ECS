package si.um.feri.ecsgame.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import si.um.feri.ecsgame.AncevskiGameECS;

public class DesktopLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("AncevskiGameECS");
        config.setWindowedMode(800, 480);
        config.useVsync(true);
        new Lwjgl3Application(new AncevskiGameECS(), config);
    }
}
