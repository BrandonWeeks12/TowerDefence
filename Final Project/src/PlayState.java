/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import org.lwjgl.BufferUtils;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
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
public class PlayState {
    
    private Maps map;
    
    private boolean selected;
    
    private ArrayList<Enemy> enemies;
    
    private int towerMenuTexId, towerMenuVertId, towerMenuDrawCount;
    
    private int lives = 10;
    
    private final int TIME_BETWEEN_ROUNDS = 300;
    
    private int numEnemies,timeBetween;
    
    private Texture towerMenuTex;
    
    private int currency;
    
    private static int score = 1000;
    
    private ArrayList<Tower> towerList;
    
    public PlayState(int mapSelected){
        map = new Maps(mapSelected);
        enemies = new ArrayList<Enemy>();
        numEnemies = 15;
        timeBetween = 60;
        currency = 500;
        towerList = new ArrayList();
        
        towerMenuTex = new Texture("./assets/TowerMenu.png");
        initiateTowerMenu(new float[]{0, 0, 0, 1, 1, 1, 1, 0}, new float[]{560, 26, 
                                                                           560, 500, 
                                                                           685, 500, 
                                                                           685, 26});
        
    }
    
    
    private void createEnemy(){
        enemies.add(new Enemy(100, 1f, map.getStartingLocX(),map.getStartingLocY(), new float[]{0, 0, 0, 1, 1, 1, 1, 0}, map.getStartDir()));
        
    }
    private void startWave(){
        frameCount =0;
        createdEnemies = 0;
        numEnemies++;
        wave = true;
    }
    
    private int frameCount=0;
    private int createdEnemies=0;
    private boolean wave= true;
    public void render(){
        
        frameCount++;
        
        if (lives == 0){
            //glfwSetWindowShouldClose(FinalProject.win,true);
            FinalProject.setState(FinalProject.State.HIGHSCORE);
        }
        map.getGameBoard().renderTiles();
        
        if (createdEnemies == numEnemies && enemies.size() == 0 && wave){
            frameCount = 0;
            wave = false;
        }
        if (frameCount >= timeBetween && createdEnemies < numEnemies){
            createEnemy();
            frameCount = 0;
            createdEnemies++;
        }
        if (!wave && frameCount> TIME_BETWEEN_ROUNDS)
            startWave();
        
        renderEnemies(); 
        
        renderTowerMenu();
        renderTowerBullets();
        renderText();
        renderSellUpgradeText();
    }
    private void renderText(){
        //System.out.println("Rendering Text");
        float y = getGameBoard().getHeight() + getGameBoard().getMinY() + 25;
        Text.renderString("Lives: "+lives, 50, y);
        Text.renderString("Score: "+score, 250, y);
        Text.renderString("Currency: " + currency, 450, y);
    }
    
    private void renderSellUpgradeText(){
        Text.renderString("Sell", 560, 335);
        Text.renderString("Upgrade", 560, 380);
    }
    private boolean enemiesAreInView(int i, Tower tower){
        if(enemies.get(i).getMiddlePointX() < tower.getOriginalCenterX()+tower.getRadiusView() && enemies.get(i).getMiddlePointX() > tower.getOriginalCenterX()-tower.getRadiusView()){
            if(enemies.get(i).getMiddlePointY() < tower.getOriginalCenterY()+tower.getRadiusView() && enemies.get(i).getMiddlePointY() > tower.getOriginalCenterY()-tower.getRadiusView()){
                return true;
            }
        }
        return false;
    }
    
    private boolean targetIsInView(Tower tower){
        if(tower.getSelectedEnemy().getMiddlePointX() < tower.getOriginalCenterX()+tower.getRadiusView() && tower.getSelectedEnemy().getMiddlePointX() > tower.getOriginalCenterX()-tower.getRadiusView()){
            if(tower.getSelectedEnemy().getMiddlePointY() < tower.getOriginalCenterY()+tower.getRadiusView() && tower.getSelectedEnemy().getMiddlePointY() > tower.getOriginalCenterY()-tower.getRadiusView()){
                return true;
            }
        }
        if(!tower.hasTargetSelected()){
            tower.setSelectedTarget();
        }
        
        return false;
    }
    
    private void renderTowerBullets(){
        if(enemies.size() >= 1){
            for(Tower tower:towerList){
                for(int i=0; i<enemies.size(); i++){
                    if(enemiesAreInView(i, tower)){
                        if(!tower.getViewableEnemies().contains(enemies.get(i))){
                            tower.addViewableEnemy(enemies.get(i));
                        }
                        if(!tower.hasTargetSelected()){
                            tower.setSelectedTarget();
                        }
                    }else if(tower.getViewableEnemies().contains(enemies.get(i))){
                        tower.getViewableEnemies().remove(enemies.get(i));
                        
                        if(tower.getSelectedEnemy() == enemies.get(i)){
                            tower.setTargetSelected(false);
                        }
                    }
                }
                
                //Checks bullet location if target is selected
                if(tower.getSelectedEnemy() != null){
                    if(targetIsInView(tower) && tower.getSelectedEnemy().isVisible()){
                        tower.renderTowerBullets(tower.getSelectedEnemy().getMiddlePointX(),tower.getSelectedEnemy().getMiddlePointY()); 
                        //-10 and +10 to give padding because tower bullet will not land directly in the center
                        if(tower.getVertices()[0] >= tower.getSelectedEnemy().getVertices()[0]-10 && tower.getVertices()[6] <= tower.getSelectedEnemy().getVertices()[6]+10){
                            if(tower.getVertices()[1] >= tower.getSelectedEnemy().getVertices()[1]-10 && tower.getVertices()[7] <= tower.getSelectedEnemy().getVertices()[7]+10){
                                tower.getSelectedEnemy().setHealth(tower.getSelectedEnemy().getHealth() - tower.getTowerDamage());
                                if(tower.getReloadTimer() % tower.getReloadTime() != 0){
                                    tower.resetBullet(false);
                                }
                            }
                        }
                        if(tower.getReloadTimer() % tower.getReloadTime() == 0){
                            tower.resetBullet(true);
                        }
                    }  
                } 
            }
        }
    }
    
    
    private void renderEnemies(){
        ArrayList<Enemy> toRemove = new ArrayList<Enemy>();
        for (Enemy enemy:enemies){
            if (enemy.getHealth() > 0)
                enemy.render();
            else 
                toRemove.add(enemy);
            
        }
        
        
        for (Enemy enemy:enemies)
            if (checkEnemyPosition(enemy))
                toRemove.add(enemy);
        
        for (Enemy enemy:toRemove)
            destroyEnemy(enemy);
    }
    
    private boolean checkEnemyPosition(Enemy enemy){
        int rowNum = getGameBoard().getRowNum(enemy.getMiddlePointY());
        int colNum = getGameBoard().getColNum(enemy.getMiddlePointX());
        if (rowNum < getGameBoard().getRows() && colNum < getGameBoard().getCols()){
        Tile tile = getGameBoard().getTile(colNum,rowNum);
        if (enemy.getMiddlePointX()==tile.getMiddlePointX() && enemy.getMiddlePointY()==tile.getMiddlePointY()){
            if (rowNum != enemy.getCurRowNum() || colNum != enemy.getCurColNum()){
                enemy.setCurColNum(colNum);
                enemy.setCurRowNum(rowNum);
                checkEnemyMovement(enemy);
            }
        }
        return false;
        }
        else {
            lives--;
            return true;

        }
    }
    
    private void checkEnemyMovement(Enemy enemy){
        int curRowNum = enemy.getCurRowNum();
        int curColNum = enemy.getCurColNum();
        switch (enemy.getCurrentDirection()){
            case 0:
            case 2:
                if (curColNum != getGameBoard().getCols() && getGameBoard().checkTile(curColNum+1,curRowNum))
                    enemy.setCurrentDirection(1);
                else if (curColNum != 0 && getGameBoard().checkTile(curColNum-1,curRowNum))
                    enemy.setCurrentDirection(3);
                break;
            case 1:
            case 3:
                if (curRowNum != getGameBoard().getRows() && getGameBoard().checkTile(curColNum,curRowNum-1))
                    enemy.setCurrentDirection(0);
                else if (curRowNum != 0 && getGameBoard().checkTile(curColNum,curRowNum+1))
                    enemy.setCurrentDirection(2);
                break;
        }
    }
    
    private void destroyEnemy(Enemy enemy){
        for(int i=0; i<enemies.size(); i++){
            if(enemies.get(i).equals(enemy)){
                enemy.setVisible(false);
                currency += 50;
                score += 100;
            }
        }
        
        for(Tower tower:towerList){
            if(tower.getViewableEnemies().contains(enemy)){
                if(tower.getSelectedEnemy() == enemy){
                    tower.setTargetSelected(false);
                }
                tower.getViewableEnemies().remove(enemy);
            }
        }
        
        enemies.remove(enemy);
        
    }
    
    
    public GameBoard getGameBoard(){
        return map.getGameBoard();
    }
    
    
    public boolean alreadySelectedTile(){
        return selected;
    }
    
    public void setSelected(boolean selected){
        this.selected = selected;
    }
    
    public int getCurrency(){
        return currency;
    }
    
    public void setCurrency(int newCurrency){
        currency = newCurrency;
    }
    
    
    
    
    
    private void initiateTowerMenu(float[] texCoords, float[] verts){
        towerMenuDrawCount = verts.length;
        towerMenuVertId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, towerMenuVertId);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(verts), GL_STATIC_DRAW);
        
        towerMenuTexId = glGenBuffers();
        
        glBindBuffer(GL_ARRAY_BUFFER, towerMenuTexId);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(texCoords), GL_STATIC_DRAW);
        
        
        
    }
    
    private void renderTowerMenu(){
        towerMenuTex.bind();
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        
        glBindBuffer(GL_ARRAY_BUFFER, towerMenuVertId);
        glVertexPointer(2, GL_FLOAT, 0, 0);
        
        glBindBuffer(GL_ARRAY_BUFFER, towerMenuTexId);
        glTexCoordPointer(2, GL_FLOAT, 0, 0);
        
        glDrawArrays(GL_QUADS, 0, towerMenuDrawCount);
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
    
    public void createNewTower(int centerX, int centerY, int towerType){
        Tower tower = new Tower(centerX, centerY, towerType);
        towerList.add(tower);
    }
    
    public void sellTower(int centerX, int centerY){
        towerList.remove(getSelectedTower(centerX, centerY));
    }
    
    public Tower getSelectedTower(int centerX, int centerY){
        int towerSelected = 0;
        for(int i=0; i<towerList.size(); i++){
            if(towerList.get(i).getCenterX() == centerX && towerList.get(i).getCenterY() == centerY){
                towerSelected = i;
            }
        }
        return towerList.get(towerSelected);
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        score = score;
    }
    
    
    
}
