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
public class Enemy {
    
    private float movementSpeed;
    
    private int health, texId, vertId, drawCount;
    
    private int middlePointX, middlePointY;
    
    private int curRowNum,curColNum;
    //For location
    private int NORTH=0, EAST=1, SOUTH=2, WEST=3, currentDirection;
    
    private float[] verts, texCoords;
    
    private float radius= 15f/2f; 
    
    private Texture enemyTex;
    
    public Enemy(int totalHealth, float speed, float startingLocX,float startingLocY, float[] texCoordinates, int startingDirection){
        
        movementSpeed = speed;
        health = totalHealth;
        
        this.currentDirection = startingDirection;
        
        verts = new float[]{
            startingLocX - radius,startingLocY - radius,
            startingLocX -radius,startingLocY +radius,
            startingLocX + radius,startingLocY +radius,
            startingLocX + radius, startingLocY - radius
        };
        
        
        drawCount = verts.length;
        
        
        texCoords = texCoordinates;
        
        //Center Point X
        middlePointX = (int)(verts[0]+verts[4])/2;
        
        //Center Point Y
        middlePointY = (int)(verts[1]+verts[5])/2;
        
        enemyTex = new Texture("./assets/Enemy.png");
        
        updateVertBuffer(verts);
        updateTexCoordsBuffer(texCoords);
        
        curRowNum =-1;
        curColNum =-1;
    }
    
    private void setCircleVerts(){
        
    }
    
    
    private void updateEnemyMovement(){
        switch(currentDirection){
            case 0:
                updateYVerts(-movementSpeed);
                break;
            case 1:
                updateXVerts(movementSpeed);
                break;
            case 2:
                updateYVerts(movementSpeed);
                break;
            case 3:
                updateXVerts(-movementSpeed);
                break;
        }
        
    }
    
    public void render(){
        enemyTex.bind();

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
        
        updateEnemyMovement();
        
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
    
    private void updateYVerts(float y){
        verts[1] = verts[1] + y;
        
        //Top left
        verts[3] = verts[3] + y;
        
        //Bottom right
        verts[5] = verts[5] + y;
        
        //Top right
        verts[7] = verts[7] + y;
        
        setVerts(verts);
        
        //Center Point Y
        middlePointY = (int)(verts[1]+verts[3])/2;
        //System.out.println("middlePointY - Enemy: " + middlePointY);
    }
    
    private void updateXVerts(float x){
        //Top left
        verts[0] = verts[0] + x;
        
        //Bottom left
        verts[2] = verts[2] + x;
        
        //Bottom right
        verts[4] = verts[4] + x;
        
        //Top right
        verts[6] = verts[6] + x;
        
        setVerts(verts);
        //Center Point X
        middlePointX = (int)(verts[0]+verts[4])/2;
        //System.out.println("middlePointX - Enemy: " + middlePointX);
    }
    
    
    public void setVerts(float[] verts){
        updateVertBuffer(verts);
    }
    public void setTexCoords(float[] texCoords){
        updateTexCoordsBuffer(texCoords);
    }
    
    public int getMiddlePointX(){
        return middlePointX;
    }
    
    public int getMiddlePointY(){
        return middlePointY;
    }
    
    public int getCurrentDirection(){
        return currentDirection;
    }
    
    public void setCurrentDirection(int x){
        currentDirection = x;
    }
    
    public int getHealth(){
        return health;
    }
    
    public void setHealth(int newHealth){
        health = newHealth;
    }
    
    public int getCurColNum(){
        return curColNum;
    }

    public int getCurRowNum() {
        return curRowNum;
    }

    public void setCurRowNum(int curRowNum) {
        this.curRowNum = curRowNum;
    }

    public void setCurColNum(int curColNum) {
        this.curColNum = curColNum;
    }
    
    
    
}
