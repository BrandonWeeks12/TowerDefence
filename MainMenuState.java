/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

/**
 *
 * @author Brandon
 */
public class MainMenuState {
    
    private Texture mainMenuTex;
    
    private int vertId, texId, drawCount;
    
    public int playMinX=270, playMaxX=430, playMinY=325, playMaxY=380;
    
    public int exitMinX=270, exitMaxX=430, exitMinY=430, exitMaxY=480;
    
    public MainMenuState(float[] vertices, float[] texCoordinates){
        mainMenuTex = new Texture("./assets/MainMenu.png");
        
        drawCount = vertices.length;
        
        updateTexCoordsBuffer(texCoordinates);
        updateVertBuffer(vertices);
        
    }
    
    
    public void renderMainMenu(){
        mainMenuTex.bind();
        
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        
        glBindBuffer(GL_ARRAY_BUFFER, vertId);
        glVertexPointer(2, GL_FLOAT, 0, 0);
        
        glBindBuffer(GL_ARRAY_BUFFER, texId);
        glTexCoordPointer(2, GL_FLOAT, 0, 0);
        
        glDrawArrays(GL_QUADS, 0, drawCount);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        
        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
    }
    
    private FloatBuffer createBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
    
    //Needs this to update texCoords for image
     private void updateTexCoordsBuffer(float[] texCoords){
        texId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, texId);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(texCoords), GL_STATIC_DRAW);
    }
    
    //Needs this to update verticies for image
    private void updateVertBuffer(float[] verts){
        vertId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertId);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(verts), GL_STATIC_DRAW);
    }
    
}
