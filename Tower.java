/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.nio.FloatBuffer;
import java.util.ArrayList;
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
 * @author Brandon
 */
public class Tower {
    
    private int towerType, centerX, centerY;
    
    private float[] vertices, texCoords;
    
    private int damage, attackSpeed, radiusView, originalCenterX, originalCenterY;
    
    private Texture towerOneBullet, towerTwoBullet, towerThreeBullet;
    
    private int texId, vertId, drawCount, reloadTime, reloadTimer;
    
    private boolean canShoot = true, targetSelected = false;
    
    private ArrayList<Enemy> viewableEnemies;
    
    private Enemy targetEnemy;
    
    public Tower(int centerX, int centerY, int towerType){
        originalCenterX = centerX;
        originalCenterY = centerY;
        
        viewableEnemies = new ArrayList<Enemy>();
        
        this.centerX = centerX;
        this.centerY = centerY;
        this.towerType = towerType;
        
        texCoords = new float[]{0,0,0,1,1,1,1,0};
        
        //-5 because the bullet textures are 10x10
        vertices = new float[]{centerX-5, centerY-5, centerX-5, centerY+5, centerX+5, centerY+5, centerX+5, centerY-5};
        
        drawCount = vertices.length;
        
        switch(towerType){
            case 1:
                towerOneBullet = new Texture("./assets/Tower1Bullet.png");
                setTowerOneVariables();
                break;
            case 2:
                towerTwoBullet = new Texture("./assets/Tower2Bullet.png");
                setTowerTwoVariables();
                break;
            case 3:
                towerThreeBullet = new Texture("./assets/Tower3Bullet.png");
                setTowerThreeVariables();
                break;
            default:
                break;
        }
        
        updateTexCoordsBuffer(texCoords);
        updateVertBuffer(vertices);
        
    }
    
    public void renderTowerBullets(int enemyCenterX, int enemyCenterY){
        
        switch(towerType){
            case 1:
                towerOneBullet.bind();
                break;
            case 2:
                towerTwoBullet.bind();
                break;
            case 3:
                towerThreeBullet.bind();
                break;
            default:
                break;
        }
        
        reloadTimer++;
        if(canShoot){
            updateBullet(enemyCenterX, enemyCenterY);
        }else if(reloadTimer % reloadTime == 0){
            resetBullet(true);
        }
        
        
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
    
    private void updateBullet(int enemyCenterX, int enemyCenterY){
        
        if(centerX > enemyCenterX){
            updateXVerts(-attackSpeed);
        }else if(centerX < enemyCenterX){
            updateXVerts(attackSpeed);
        }
        
        if(centerY > enemyCenterY){
            updateYVerts(-attackSpeed);
        }else if(centerY < enemyCenterY){
            updateYVerts(attackSpeed);
        }
        
        updateVertBuffer(vertices); 
        
        
        //Update center points
        centerX = (int)(vertices[0]+vertices[6])/2;
        centerY = (int)(vertices[1]+vertices[7])/2;
        
    }
    
    private void updateYVerts(float speed){
        vertices[1] = vertices[1] + speed;
        vertices[3] = vertices[3] + speed;
        vertices[5] = vertices[5] + speed;
        vertices[7] = vertices[7] + speed;
    }
    
    private void updateXVerts(float speed){
        vertices[0] = vertices[0] + speed;
        vertices[2] = vertices[2] + speed;
        vertices[4] = vertices[4] + speed;
        vertices[6] = vertices[6] + speed;
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
    
    //Radius view is in pixels
    private void setTowerOneVariables(){
        damage = 2;
        reloadTime = 50;
        attackSpeed = 5;
        radiusView = 150;
    }
    
    private void setTowerTwoVariables(){
        damage = 1;
        reloadTime = 20;
        attackSpeed = 5;
        radiusView = 150;
    }
    
    private void setTowerThreeVariables(){
        damage = 10;
        reloadTime = 100;
        attackSpeed = 3;
        radiusView = 100;
    }
    
    public int getCenterX(){
        return centerX;
    }
    
    public int getCenterY(){
        return centerY;
    }
    
    public int getOriginalCenterX(){
        return originalCenterX;
    }
    
    public int getOriginalCenterY(){
        return originalCenterY;
    }
    
    public int getRadiusView(){
        return radiusView;
    }
    
    
    public float[] getVertices(){
        return vertices;
    }
    
    private void updateVertices(float[] verts){
        vertices = verts;
    }
    
    
    public int getTowerDamage(){
        return damage;
    }
    
    public int getAttackSpeed(){
        return attackSpeed;
    }
    
    public int getReloadTime(){
        return reloadTime;
    }
    
    public int getReloadTimer(){
        return reloadTimer;
    }
    
    public void addViewableEnemy(Enemy enemy){
        viewableEnemies.add(enemy);
    }
    
    public void removeViewableEnemy(Enemy enemy){
        viewableEnemies.remove(enemy);
    }
    
    public ArrayList getViewableEnemies(){
        return viewableEnemies;
    }
    
    public Enemy getViewableEnemies(int location){
        return viewableEnemies.get(location);
    }
    
    public boolean hasTargetSelected(){
        return targetSelected;
    }
    
    public void setTargetSelected(boolean selected){
        targetSelected = selected;
    }
    
    public Enemy getSelectedEnemy(){
        return targetEnemy;
    }
    
    public void setSelectedTarget(){
        for(int i=0; i<viewableEnemies.size(); i++){
            if(viewableEnemies.get(i).isVisible()){
                targetEnemy = viewableEnemies.get(i);
                targetSelected = true;
                System.out.println("target selected = true" + targetEnemy);
                return;
            }
        }
        targetSelected = false;
    }
    
    public void resetBullet(boolean resetTimer){
        
        if(resetTimer){
            reloadTimer = 0;
            canShoot=true;
        }else{
            canShoot=false;
        }
        
        centerX = originalCenterX;
        centerY = originalCenterY;
        
        vertices[0] = centerX-5;
        
        vertices[1] = centerY-5;
        
        vertices[2] = centerX-5;
        
        vertices[3] = centerY+5;
        
        vertices[4] = centerX+5;
        
        vertices[5] = centerY+5;
        
        vertices[6] = centerX+5;
        
        vertices[7] = centerY-5;
        
        updateVertBuffer(vertices);
        
        
        
        
    }
}
