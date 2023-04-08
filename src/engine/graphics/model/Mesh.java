package engine.graphics.model;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class Mesh
{
    private int numVertices;
    private int vaoId;
    private List<Integer> vboIdList;

    public Mesh(float[] positions, int numVertices){
        try (MemoryStack stack = MemoryStack.stackPush()) {
            this.numVertices = numVertices;
            this.vboIdList = new ArrayList<>();

            //create the VAO, we store a pointer to it in vaoId
            vaoId = glGenVertexArrays();

            //bind the VAO so it is current
            glBindVertexArray(vaoId);

            //create the VBO, we store a pointer to it in vboId and add it to the list of VBOs
            int vboId = glGenBuffers();
            vboIdList.add(vboId);

            //we need the memory stored off-heap so that it can be accessed by the native code
            //first allocate the memory
            FloatBuffer posBuffer = stack.callocFloat(positions.length);

            //populate it with vertex data
            posBuffer.put(0,positions);

            //bind the buffer so it is current
            glBindBuffer(GL_ARRAY_BUFFER, vboId);

            //upload the data to the current buffer
            glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);

            //enable the vertex attribute array
            glEnableVertexAttribArray(0);

            //specify the layout of the vertex data, we have 3 floats per vertex, only one attribute which means the stride is 0
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        }
    }

    public void cleanup(){
        vboIdList.stream().forEach(GL30::glDeleteBuffers);
        glDeleteVertexArrays(vaoId);
    }

    public final int getVaoId() {
        return vaoId;
    }

    public final int getNumVertices() {
        return numVertices;
    }


}
