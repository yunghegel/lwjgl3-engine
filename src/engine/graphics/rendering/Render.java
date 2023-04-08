package engine.graphics.rendering;

import engine.input.Window;
import engine.graphics.rendering.Scene;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Render
{

    private SceneRenderer sceneRenderer;

    public Render(){
        GL.createCapabilities();
        sceneRenderer = new SceneRenderer();
    }

    public void cleanup(){
        sceneRenderer.cleanup();
    }

    public void render(Window window, Scene scene){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        sceneRenderer.render(scene);
    }



}
