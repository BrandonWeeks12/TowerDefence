/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

/**
 *
 * @author zamsl
 */
public class Text {
    private static Texture tex = new Texture("/assets/font.png");
    public Text(){
        
    }
    private static float desHeight = .2f;
    private static float deslength = .2f;
    private static float[] vertices;
    private static final float defCharacterWidth = 40f;
    private static final float defCharacterHeight = 30f;
    
    
    public static void renderString(String string,float x,float y){
        renderString(string,x,y,defCharacterWidth,defCharacterHeight);
    }
    
    private static float[] texCoor = new float[8];
            public static void renderString(String string, float x, float y,float characterWidth,float characterHeight) {
        for (int i = 0; i < string.length(); i++) {
            
            int ascii = (int) string.charAt(i);
            
            final float cellSize = 1.0f / 16;
           
            float cellX = ((int) ascii % 16) * cellSize;
            
            float cellY = ((int) ascii / 16) * cellSize;
            //System.out.println("Cellx: "+cellX/cellSize+" CellY: "+cellY/cellSize);
            
            texCoor = new float[] {
            cellX,cellY+cellSize,
            cellX+cellSize,cellY+cellSize,
            cellX+cellSize,cellY,
            cellX,cellY
            
        };
            vertices = new float[] {
                x + i * characterWidth / 3,characterHeight + y,      //Top Left
                x+ i * characterWidth / 3 + characterWidth / 2, y +characterHeight,     //Top Right
                x+i * characterWidth / 3 + characterWidth/2,y,      //Bottom Right
                x+ i * characterWidth / 3,y,    //Bottom Left
                
            };
            updateTexCoordsBuffer();
            updateVertBuffer();
            render();
        }
        
    }
    private static void render(){
        tex.bind();
  
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        glEnableClientState(GL_VERTEX_ARRAY);
        
        glBindBuffer(GL_ARRAY_BUFFER,vertId);
        glVertexPointer(2,GL_FLOAT,0,0);
        
        
        glBindBuffer(GL_ARRAY_BUFFER,texId);
        glTexCoordPointer(2,GL_FLOAT,0,0);
        
        glDrawArrays(GL_QUADS,0,4);
        
        glBindBuffer(GL_ARRAY_BUFFER,0);
        
        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        
    }
    private static int texId = glGenBuffers();
     private static void updateTexCoordsBuffer(){
        glBindBuffer(GL_ARRAY_BUFFER, texId);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(texCoor), GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER,0);
    }
    private static int vertId = glGenBuffers();
    //Needs this to update verticies for image
    private static void updateVertBuffer(){
        glBindBuffer(GL_ARRAY_BUFFER, vertId);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER,0);
    }
    private static FloatBuffer createBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
