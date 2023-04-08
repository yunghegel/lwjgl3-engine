package engine.graphics.rendering;

import engine.graphics.shaders.ShaderProgram;
import engine.graphics.shaders.ShaderProgram.*;


import java.util.ArrayList;
import java.util.List;
import static org.lwjgl.opengl.GL30.*;

public class SceneRenderer
{

    private ShaderProgram shaderProgram;

    public SceneRenderer(){
        List<ShaderProgram.ShaderModuleData> shaderModuleDataList = new ArrayList<>();
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("resources/shaders/default.fs.glsl", GL_VERTEX_SHADER));
        shaderModuleDataList.add(new ShaderProgram.ShaderModuleData("resources/shaders/default.vs.glsl", GL_FRAGMENT_SHADER));
        shaderProgram = new ShaderProgram(shaderModuleDataList);

    }

    public void cleanup(){
        shaderProgram.cleanup();
    }

    public void render(Scene scene){
        shaderProgram.bind();
        scene.getMeshMap().values().stream().forEach(m -> {
           glBindVertexArray(m.getVaoId());
           glDrawArrays(GL_TRIANGLES,0,m.getNumVertices());
        });

        glBindVertexArray(0);
        shaderProgram.unbind();
    }
}
