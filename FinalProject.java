/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Brandon
 */
public class FinalProject {

    

    public static enum State {
        MAINMENU, SELECTMAP, PLAYSTATE,HIGHSCORE
    }
    
    public static State currentState;
    
    private static MainMenuState mainMenuState;
    private static InputHandler inputHandler;
    private static SelectMapState mapSelection;
    private static HighScoreState highScoreState = new HighScoreState();
    private static PlayState playState;
    public static long win;
    public static void main(String[] args) {
        
        //Returns 1 if successfull, 0 if not.
        glfwInit();
        
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        
        //Creates window
        win = glfwCreateWindow(700, 700, "Title", 0, 0);
        
        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
                
        glfwSetWindowPos(win, (videoMode.width() - 700)/2, (videoMode.height() - 700) / 2);
        
        glfwShowWindow(win);
        
        //Uses current context as context
        glfwMakeContextCurrent(win);
        GL.createCapabilities();
        
        glEnable(GL_TEXTURE_2D);
        
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        
        glOrtho(0, 700, 700, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        
        currentState = State.MAINMENU;
        mainMenuState = new MainMenuState(new float[]{0, 0, 0, 700, 700, 700, 700, 0}, new float[]{0, 0, 0, 1, 1, 1, 1, 0});
        mapSelection = new SelectMapState(new float[]{0, 0, 0, 700, 700, 700, 700, 0}, new float[]{0, 0, 0, 1, 1, 1, 1, 0});
        
        inputHandler = new InputHandler(win, mainMenuState, mapSelection, playState);
        //glEnable(GL_BLEND);
        //glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        //PlayState playState = new PlayState(win);
        double frameCap = 1.0/60.0;        
        double unprocessed = 0;
        double frameTime = 0;
        int frames =0;
        double time = (double)System.nanoTime() / (double)1000000000L;
        //Keeps window open(updates)
        while(!glfwWindowShouldClose(win)){
            boolean canRender = false;
            double startTime = (double)System.nanoTime() / (double)1000000000L;
            double passed = startTime - time;
            unprocessed += passed;
            time = startTime;         
            frameTime += passed; 
            
            
            
            while (unprocessed >= frameCap) {
                glfwPollEvents();
                glClear(GL_COLOR_BUFFER_BIT);
                inputHandler.updateMouseClicks();
                canRender = true;
                unprocessed -= frameCap;
                if (frameTime>=1){
                    //System.out.println(frames);
                    frames=0;
                    frameTime=0;
                }
            }
            
            if (canRender){
            switch(currentState){
                case MAINMENU:
                    mainMenuState.renderMainMenu();
                    break;
                case SELECTMAP:
                    mapSelection.renderMapSelectionMenu();
                    break;
                case PLAYSTATE:
                    playState.render();
                    break;
                case HIGHSCORE:
                    highScoreState.render();
                    break;
                default:
                    break;
            }
            
            
            glfwSwapBuffers(win);
            frames++;
            }
            
            //To close window
            if(glfwGetKey(win, GLFW_KEY_ESCAPE) == GL_TRUE){
                if (currentState==State.HIGHSCORE && HighScoreState.curStatus == HighScoreState.status.addScore)
                    HighScoreState.addScore();
                glfwDestroyWindow(win);
                break;
            }
            
            
        }
        
        //Closes GLFW
        glfwTerminate();
    }
    
    public static void setState(State state){
        currentState = state;
        if (state.equals(State.HIGHSCORE))
            HighScoreState.loadFile();
    }
    
    public State getCurrentState(){
        return currentState;
    }
    
    public static PlayState getPlayState(){
        return playState;
    }
    
    static void initializePlayState(int mapSelected){
        playState = new PlayState(mapSelected);
    }
    
}
