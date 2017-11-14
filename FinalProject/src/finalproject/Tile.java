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
public class Tile {
    
    private int tileType;
    
    private int vertId, texId, drawCount, size;
    
    private int minX, minY, maxX, maxY, middlePointX, middlePointY;
    
    private float[] vertices, texCoordinates, nextTileDestination;
    
    
    public Tile(float[] vertices, float[] texCoords, int tileSize, int typeId){
        tileType = typeId;
        
        this.vertices = vertices;
        texCoordinates = texCoords;
        
        drawCount = vertices.length;
        
        size = tileSize;
        
        //For mouse clicks
        setMinMaxes();
        
        updateTexCoordsBuffer(texCoordinates);
        updateVertBuffer(vertices);
        
    }
    
    public void render(){
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
    
    private void setMinMaxes(){
        minX = (int)vertices[0];
        minY = (int)vertices[1];
        
        maxX = (int)vertices[4];
        maxY = (int)vertices[5];
        
        middlePointX = (minX+maxX)/2;
        System.out.println("middlePointX: " + middlePointX);
        
        middlePointY = (minY + maxY)/2;
        System.out.println("middlePointY: " + middlePointY);
        
    }
    
    public int getTileType(){
        return tileType;
    }
    
    public void setTileType(int type){
        tileType = type;
    }
    
    private FloatBuffer createBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
    
    public void setVerts(float[] verts){
        updateVertBuffer(verts);
    }
    public void setTexCoords(float[] texCoords){
        updateTexCoordsBuffer(texCoords);
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
    
    public void setNextDestination(float[] destination){
        nextTileDestination = destination;
    }
    
    public int getMaxX(){
        return maxX;
    }
    
    public int getMinX(){
        return minX;
    }
    
    public int getMaxY(){
        return maxY;
    }
    
    public int getMinY(){
        return minY;
    }
    
    public int getMiddlePointX(){
        return middlePointX;
    }
    
    public int getMiddlePointY(){
        return middlePointY;
    }
    
    
}
