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
public class GameBoard {
    
    private Tile tiles[][];
    private Tile tileCheckPoints[];
    private Tile tile;
    
    private float[] verts;
    private float[] texCoords;
    
    
    private int rows, cols, checkPointCounter = 0;
    
    private int width,height,minX,minY;
    
    private Texture highLightTex, grassTex, pathTex;
    
   
    
    public GameBoard(int rows, int cols, int[][] mapLayout){
        
        this.rows = rows;
        this.cols = cols;
        
        this.width = cols*26;
        this.height = rows*26;
        
        minX = 26;
        minY = 26;
        
        tiles = new Tile[rows][cols];
       
        //this.tileCheckPoints = new Tile[checkPoints];
        
        //System.out.println("checkPointNumber " + checkPoints);
        
        //Tile Size
        verts = new float[] {26, 26, 
                             26, 51, 
                             51, 51, 
                             51, 26};
        
        //Texture coords for quads
        texCoords = new float[] {0, 0, 0, 1, 1, 1, 1, 0};
        
        grassTex = new Texture("./assets/Square.png");
        
        highLightTex = new Texture("./assets/HighLightedSquare.png");
        
        pathTex = new Texture("./assets/Path.png");
        
        
        createGameBoard(mapLayout);
        
    }
    
    //Creates gameboard and stores check points
    private void createGameBoard(int[][] mapLayout){
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                updateVerts(j+1, i+1);
                tile = new Tile(verts, texCoords, 25, mapLayout[i][j]);
                tiles[i][j] = tile;
//                if(tileCheckPoints[i][j] == 1){
//                    this.tileCheckPoints[checkPointCounter] = tile;
//                    checkPointCounter++;
//                }
            }
        }
     
    }
    
    private void updateVerts(int i, int j){
        
        //Bottom left
        verts[0] = (26 * i);
        verts[1] = (26 * j);
        
        //Top left
        verts[2] = (26 * i);
        verts[3] = 25 + (26 * j);
        
        //Bottom right
        verts[4] = 25 + (26 * i);
        verts[5] = 25 + (26 * j);
        
        //Top right
        verts[6] = 25 + (26 * i);
        verts[7] = (26 * j);
    }
    
    public void renderTiles(){
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                switch(tiles[i][j].getTileType()){
                    case 0:
                        grassTex.bind();
                        break;
                    case 1:
                        pathTex.bind();
                        break;
                    case 2:
                        highLightTex.bind();
                    default:
                        break;
                }
                tiles[i][j].render();
            }
        }
    }
    
    public Tile[][] getTiles(){
        return tiles;
    }
    
    public void setTouchedTile(Tile selected){
        tile = selected;
    }
    
    public Tile getTouchedTile(){
        return tile;
    }
    
    public int getRows(){
        return rows;
    }
    
    public int getCols(){
        return rows;
    }
    
    public Tile[] getCheckPoints(){
        return tileCheckPoints;
    }
    
    //Returns the tile based on the x and y pixel value
    public Tile getTile(int colNum, int rowNum){
        return tiles[rowNum][colNum];
    }
    
    //Gets the column number based on the x pixel value
    public int getColNum(int x){
        double tmpX = (double)(x-minX);
        int colNum = (int)Math.floor((tmpX/width)*rows);
        return (colNum >= 0 ? colNum:0);
    }
    
    //Gets the row number based on the y pixel value
    public int getRowNum(int y){
        double tmpY = (double)(y-minY);
        int rowNum = (int)Math.floor((tmpY/height)*cols);
        return (rowNum >= 0 ? rowNum:0);
    }
    
    //Returns true if tile is path
    public boolean checkTile(int x,int y){
        return tiles[y][x].getTileType() == 1;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    
    
    
}
