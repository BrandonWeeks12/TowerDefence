/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.nio.DoubleBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.glfw.GLFW.*;

/**
 *
 * @author Brandon
 */
public class InputHandler {
    
    private DoubleBuffer xBuffer;
    private DoubleBuffer yBuffer;
    
    private double mouseX, mouseY;
    
    private MainMenuState mainMenu;
    private SelectMapState mapSelection;
    private PlayState playState;
    
    
    private long window;
    
    private boolean mouseIsClicked;
    
    private int rowCounter, colCounter;
    
    public InputHandler(long window, MainMenuState mainMenu, SelectMapState mapSelection, PlayState playState){
        //For mouse location
        xBuffer = BufferUtils.createDoubleBuffer(1);
        yBuffer = BufferUtils.createDoubleBuffer(1);
        
        this.mainMenu = mainMenu;
        this.mapSelection = mapSelection;
        this.playState = playState;
        
        this.window = window;
        
        mouseIsClicked = false;
    }
    
    public void updateMouseClicks(){
        if(glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == 1 && !mouseIsClicked){
            mouseIsClicked = true;
            glfwGetCursorPos(window, xBuffer, yBuffer);
            mouseX = xBuffer.get(0);
            mouseY = yBuffer.get(0);
            
            switch(FinalProject.currentState){
                case MAINMENU:
                    checkMainMenuClick(mouseX, mouseY);
                    break;
                case SELECTMAP:
                    checkSelectMapClick(mouseX, mouseY);
                    break;
                case PLAYSTATE:
                    checkPlayStateClick(mouseX, mouseY);
                    
                    break;
                default:
                    break;
            }
        }else if(glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == 0){
            glfwGetCursorPos(window, xBuffer, yBuffer);
            mouseX = xBuffer.get(0);
            mouseY = yBuffer.get(0);
            if(FinalProject.currentState == FinalProject.State.PLAYSTATE){
                if(!playState.alreadySelectedTile()){
                    isHoveredOverTile(mouseX, mouseY);
                }
            }
            mouseIsClicked = false;
        }
        
        
        
    }
    
    private void checkMainMenuClick(double mouseX, double mouseY){
        if((mouseX >= mainMenu.playMinX && mouseX <= mainMenu.playMaxX) && (mouseY >= mainMenu.playMinY && mouseY <= mainMenu.playMaxY)){
            FinalProject.setState(FinalProject.State.SELECTMAP);
        }else if((mouseX >= mainMenu.exitMinX && mouseX <= mainMenu.exitMaxX) && (mouseY >= mainMenu.exitMinY && mouseY <= mainMenu.exitMaxY)){
            glfwDestroyWindow(window);
            glfwTerminate();
        }
    }
    
    private void checkSelectMapClick(double mouseX, double mouseY){
        if((mouseX >= mapSelection.oneMinX && mouseX <= mapSelection.oneMaxX) && mouseY >= mapSelection.oneMinY && mouseY <= mapSelection.oneMaxY){
            FinalProject.initializePlayState(0);
            playState = FinalProject.getPlayState();
            FinalProject.setState(FinalProject.State.PLAYSTATE);
        }else if((mouseX >= mapSelection.twoMinX && mouseX <= mapSelection.twoMaxX) && mouseY >= mapSelection.twoMinY && mouseY <= mapSelection.twoMaxY){
            FinalProject.initializePlayState(1);
            playState = FinalProject.getPlayState();
            FinalProject.setState(FinalProject.State.PLAYSTATE);
        }else if((mouseX >= mapSelection.threeMinX && mouseX <= mapSelection.threeMaxX) && mouseY >= mapSelection.threeMinY && mouseY <= mapSelection.threeMaxY){
            FinalProject.initializePlayState(2);
            playState = FinalProject.getPlayState();
            FinalProject.setState(FinalProject.State.PLAYSTATE);
        }
    }
    
    //Selects/Deselects tile
    private void checkPlayStateClick(double mouseX, double mouseY){
        for(rowCounter=0; rowCounter<playState.getGameBoard().getRows(); rowCounter++){
            for(colCounter=0; colCounter<playState.getGameBoard().getCols(); colCounter++){
                if((mouseX >= playState.getGameBoard().getTiles()[rowCounter][colCounter].getMinX() && mouseX <= playState.getGameBoard().getTiles()[rowCounter][colCounter].getMaxX()) 
                            && (mouseY >= playState.getGameBoard().getTiles()[rowCounter][colCounter].getMinY() && mouseY <= playState.getGameBoard().getTiles()[rowCounter][colCounter].getMaxY())){
                    
                    if(playState.getGameBoard().getTiles()[rowCounter][colCounter].getTileType() != 1){
                        
                        
                        if(playState.alreadySelectedTile()){
                            playState.setSelected(false);
                        }else{
                            playState.getGameBoard().getTiles()[rowCounter][colCounter].setTileType(2);
                            playState.getGameBoard().setTouchedTile(playState.getGameBoard().getTiles()[rowCounter][colCounter]);
                            playState.setSelected(true);
                        }
                    }
                }
            }
        }
    }
    
    //Turns tiles red when mouse is hovered over tile
    private void isHoveredOverTile(double mouseX, double mouseY){
        for(rowCounter=0; rowCounter<playState.getGameBoard().getRows(); rowCounter++){
            for(colCounter=0; colCounter<playState.getGameBoard().getCols(); colCounter++){
                if((mouseX >= playState.getGameBoard().getTiles()[rowCounter][colCounter].getMinX() && mouseX <= playState.getGameBoard().getTiles()[rowCounter][colCounter].getMaxX()) 
                            && (mouseY >= playState.getGameBoard().getTiles()[rowCounter][colCounter].getMinY() && mouseY <= playState.getGameBoard().getTiles()[rowCounter][colCounter].getMaxY())){
                    //playState.getGameBoard().setTouchedTile(playState.getGameBoard().getTiles()[rowCounter][colCounter]);
                    if(playState.getGameBoard().getTiles()[rowCounter][colCounter].getTileType() == 0){
                        playState.getGameBoard().getTiles()[rowCounter][colCounter].setTileType(2);
                    }
                }else{
                    if(playState.getGameBoard().getTiles()[rowCounter][colCounter].getTileType() == 2){
                        playState.getGameBoard().getTiles()[rowCounter][colCounter].setTileType(0);
                    }  
                }
            }
        }
    }
}
