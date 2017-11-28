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
 * @author zamsl
 */
public class HealthBar {
    
    private float[] vertices;
    private float[] barVertices;
    private float width = 25f;
    private float height = 5f;
    private float distAbove = - 20;
    private int startingHealth;
    private int vertId;
    private int barId;
    public HealthBar(float enemyMiddleX,float enemyMiddleY, int enemyStartingHealth){
        vertices = new float[]{
            enemyMiddleX - width/2,enemyMiddleY - height/2 + distAbove,
            enemyMiddleX - width/2,enemyMiddleY  + height/2 + distAbove,
            enemyMiddleX + width/2,enemyMiddleY +height/2 + distAbove,
            enemyMiddleX + width/2, enemyMiddleY - height/2 + distAbove
    };
        barVertices = vertices;
        startingHealth = enemyStartingHealth;
    }
    
    public void render(int newHealth){
        changeHealth(newHealth);
        updateVertBuffer();
        updateBarBuffer();
        
        glEnableClientState(GL_VERTEX_ARRAY);
        
        glColor3f(0,0,0);
        glBindBuffer(GL_ARRAY_BUFFER, vertId);
        glVertexPointer(2, GL_FLOAT, 0, 0);
        
        glDrawArrays(GL_QUADS, 0, 4);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        
        glColor3f(1,0,0);
        glBindBuffer(GL_ARRAY_BUFFER,barId);
        glVertexPointer(2, GL_FLOAT, 0, 0);
        
        glDrawArrays(GL_QUADS, 0, 4);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        
        glColor3f(1,1,1);
     
        glDisableClientState(GL_VERTEX_ARRAY);
        
    }
    
    private void changeHealth(int newHealth){
        barVertices = vertices.clone();
        float percent = (float)newHealth / (float)startingHealth;
        barVertices[4] = vertices[2] + width * percent;
        barVertices[6] = vertices[2] + width * percent;
    }
    
    public void moveX(float x){
        for (int i=0;i<8;i+=2){
            vertices[i] += x;
            //barVertices[i] += x;
        }
    }
    
    public void moveY(float y){
        for (int i=1;i<8;i+=2){
            vertices[i] += y;
            //barVertices[i] += y;
        }
    }
    
    private void updateVertBuffer(){
        vertId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertId);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);
    }
    
    private void updateBarBuffer(){
        barId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, barId);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(barVertices), GL_STATIC_DRAW);
    }
    
    private FloatBuffer createBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
