package engine.graphics.rendering;

import engine.graphics.model.Mesh;

import java.util.HashMap;
import java.util.Map;

public class Scene
{
    private Map<String, Mesh> meshMap;

    public Scene(){
        meshMap = new HashMap<>();
    }

    public void cleanup(){
        meshMap.values().stream().forEach(Mesh::cleanup);
    }

    public void addMesh(String meshId, Mesh mesh){
        meshMap.put(meshId, mesh);
    }

    public Mesh getMesh(String meshId){
        return meshMap.get(meshId);
    }

    public Map<String, Mesh> getMeshMap() {
        return meshMap;
    }

}
