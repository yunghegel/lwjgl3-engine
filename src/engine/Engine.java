package engine;

import engine.input.Window;
import engine.graphics.rendering.Render;
import engine.graphics.rendering.Scene;

public class Engine
{
    public static final int TARGET_UPS = 60;
    private final IAppLogic appLogic;
    private final Window window;
    private Render render;
    private boolean running;
    private Scene scene;
    private int targetFps;
    private int targetUps;

    public Engine(String windowTitle, Window.WindowOptions opts, IAppLogic appLogic){
        this.appLogic = appLogic;
        window = new Window(windowTitle, opts, () ->
        {
            resize();
            return null;
        });
        targetFps = 0;
        targetUps = 0;
        render = new Render();
        scene = new Scene();
        appLogic.init(window, scene, render);
        running = true;
    }

    private void cleanup() {
        appLogic.cleanup();
        render.cleanup();
        scene.cleanup();
        window.cleanup();
    }

    private void resize() {
        // Nothing to be done yet
    }

    private void run(){
        long initialTime = System.currentTimeMillis();
        float timeU = 1000.0f / targetUps;
        float timeR = targetFps > 0 ? 1000.0f / targetFps : 0;
        float deltaUpdate = 0;
        float deltaFps = 0;

        long updateTime = initialTime;

        while (running && !window.windowShouldClose()) {
            window.pollEvents();

            long now = System.currentTimeMillis();

            deltaUpdate += (now - initialTime) / timeU;
            deltaFps += (now - initialTime) / timeR;

            if (targetFps <= 0 || deltaFps >= 1) {
                appLogic.input(window, scene, now - initialTime);
            }

            if (deltaUpdate >= 1) {
                appLogic.update(window, scene, now - initialTime);
                updateTime = now;
                deltaUpdate--;
            }
            if (targetFps <= 0 || deltaFps >= 1) {
                render.render(window, scene);
                deltaFps--;
                window.update();
            }
            initialTime = now;

        }
        cleanup();
    }
    public void start() {
        running = true;
        run();
    }

    public void stop() {
        running = false;
    }
}
