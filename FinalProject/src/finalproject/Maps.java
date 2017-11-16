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
public class Maps {
    
    private int[][] map;
    //private int[][] tileCheckPoints;
    
    private GameBoard gameBoard;
    
    private int GRASS = 0, PATH = 1;
    
    private float startingLocX,startingLocY;
            private int startDir;
    
    public Maps(int mapSelection){
        switch(mapSelection){
            case 0:
                mapOne();
                break;
            case 1:
                mapTwo();
                break;
            case 2:
                mapThree();
                break;
            default:
                break;
        }
        
    }
    
    private void mapOne(){
        map = new int[][]{
            {GRASS,GRASS,PATH,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS, GRASS,GRASS,GRASS},
            {GRASS,GRASS,PATH,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS, GRASS,GRASS,GRASS},
            {GRASS,GRASS,PATH,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS, GRASS,GRASS,GRASS},
            {GRASS,GRASS,PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,PATH, GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,PATH, GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,PATH, GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,PATH, GRASS,GRASS},
            {GRASS,GRASS,PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, GRASS,GRASS},
            {GRASS,GRASS,PATH, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,PATH, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,PATH, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,PATH, GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,PATH, GRASS,GRASS},
        };      
        
//        tileCheckPoints = new int[][]{
//            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS, GRASS,GRASS,GRASS},
//            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS, GRASS,GRASS,GRASS},
//            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS, GRASS,GRASS,GRASS},
//            {GRASS,GRASS,PATH, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,PATH, GRASS,GRASS},
//            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS, GRASS,GRASS},
//            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS, GRASS,GRASS},
//            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS, GRASS,GRASS},
//            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS, GRASS,GRASS},
//            {GRASS,GRASS,PATH, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS, PATH, GRASS,GRASS},
//            {GRASS,GRASS,GRASS, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
//            {GRASS,GRASS,GRASS, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
//            {GRASS,GRASS,GRASS, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
//            {GRASS,GRASS,PATH, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS, PATH, GRASS,GRASS},
//            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS, GRASS,GRASS},
//            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS, PATH, GRASS,GRASS},
//        };  
        
        
        gameBoard = new GameBoard(15, 15, map);
        startingLocX = gameBoard.getTile(2,0).getMiddlePointX();
        startingLocY = gameBoard.getTile(2,0).getMiddlePointY();
        startDir = 2;
    }
    
    private void mapTwo(){
        map = new int[][]{
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,PATH, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,PATH, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,PATH, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,PATH, PATH, PATH ,PATH, PATH, PATH, PATH, GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,PATH, GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,PATH, GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,PATH, GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,PATH, GRASS,GRASS},
            {GRASS,GRASS,PATH,PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH, PATH,  GRASS,GRASS},
            {GRASS,GRASS,PATH, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,PATH, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,PATH, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,PATH, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,PATH, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,PATH, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,PATH, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,PATH, GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
        };      
        
        gameBoard = new GameBoard(20, 20, map);
        startingLocX = gameBoard.getTile(0,2).getMiddlePointX();
        startingLocY = gameBoard.getTile(0,2).getMiddlePointY();
        startDir = 1;
    }
    
    private void mapThree(){
        map = new int[][]{
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,PATH, PATH, PATH,GRASS,PATH,PATH, PATH,GRASS,PATH,PATH, PATH,GRASS,PATH,PATH, PATH,GRASS,GRASS,GRASS,GRASS},
            {GRASS,PATH,GRASS, PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,GRASS,GRASS,GRASS},
            {GRASS,PATH,GRASS, PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,GRASS,GRASS,GRASS},
            {PATH, PATH,GRASS, PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,PATH,PATH},
            {GRASS,GRASS,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,GRASS},
            {GRASS,GRASS,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,PATH,GRASS,GRASS},
            {GRASS,GRASS,GRASS,PATH, PATH,PATH,GRASS,PATH,PATH, PATH,GRASS,PATH,PATH, PATH,GRASS,PATH,PATH, PATH,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
        }; 
        
        gameBoard = new GameBoard(20, 20, map);
        startingLocX = gameBoard.getTile(0,10).getMiddlePointX();
        startingLocY = gameBoard.getTile(0,10).getMiddlePointY();
        startDir=1;
    }

    public float getStartingLocX() {
        return startingLocX;
    }

    public float getStartingLocY() {
        return startingLocY;
    }
    
    public GameBoard getGameBoard(){
        return gameBoard;
    }

    public int getStartDir() {
        return startDir;
    }
    
    
    
    
}
