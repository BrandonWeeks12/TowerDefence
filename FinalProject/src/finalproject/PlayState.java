/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

/**
 *
 * @author Brandon
 */
public class PlayState {
    
    private Maps map;
    
    private boolean selected;
    
    private Enemy enemy;
    
    private int rowCounter, colCounter;
    
    
    public PlayState(int mapSelected){
        map = new Maps(mapSelected);
        
        enemy = new Enemy(5, .007f, new float[]{83, 16, 
                                                83, 31, 
                                                98, 31, 
                                                98, 16}, new float[]{0, 0, 0, 1, 1, 1, 1, 0}, 2);
    }
    
    public void render(){
        map.getGameBoard().renderTiles();
        enemy.render();
    }
    
    private void checkEnemyPosition(){
        
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
