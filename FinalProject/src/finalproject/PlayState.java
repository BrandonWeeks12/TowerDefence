/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.util.ArrayList;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

/**
 *
 * @author Brandon
 */
public class PlayState {
    
    private Maps map;
    
    private boolean selected;
    
    private ArrayList<Enemy> enemies;
    
    private int rowCounter, colCounter;
    
    private int lives = 10;
    
    private final int TIME_BETWEEN_ROUNDS = 300;
    
    private int numEnemies,timeBetween;
    
    public PlayState(int mapSelected){
        map = new Maps(mapSelected);
        enemies = new ArrayList<Enemy>();
        numEnemies = 4;
        timeBetween = 60;
        /*
        enemy = new Enemy(5, 1f, new float[]{83, 16, 
                                                83, 31, 
                                                98, 31, 
                                                98, 16}, new float[]{0, 0, 0, 1, 1, 1, 1, 0}, 2);
*/
    }
    
    
    private void createEnemy(){
        enemies.add(new Enemy(5, 1f, map.getStartingLocX(),map.getStartingLocY(), new float[]{0, 0, 0, 1, 1, 1, 1, 0}, map.getStartDir()));
        
    }
    public void startWave(){
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
            glfwSetWindowShouldClose(FinalProject.win,true);
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
        renderText();
    }
    private void renderText(){
        //System.out.println("Rendering Text");
        float y = getGameBoard().getHeight() + getGameBoard().getMinY() + 25;
        Text.renderString("Lives: "+lives, 50, y);
        Text.renderString("Score: ", 250, y);
    }
    private void renderEnemies(){
        ArrayList<Enemy> toRemove = new ArrayList<Enemy>();
        for (Enemy enemy:enemies){
            if (enemy.getHealth() >0)
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
        //System.out.println(colNum+ " "+rowNum);
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
    
    public void destroyEnemy(Enemy enemy){
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
    
}
