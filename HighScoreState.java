/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwSetCharCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

/**
 *
 * @author zamsl
 */
public class HighScoreState {
    public static enum status{
        addScore,fail,showScores
    }
    public static status curStatus;
    private static int[] scores = new int[10];
    private static String[] names = new String[10];
    private static StringBuilder sb = new StringBuilder();
    private static URL url;
    public static void loadFile(){
        
        try{
            added = false;
            
            
            curStatus = status.showScores;
            url = new URL("http://zamsler.000webhostapp.com/highScores.txt");
            
            Scanner s = new Scanner(url.openStream());
            int i=0;
            while (s.hasNextLine()){
                String[] curLine = s.nextLine().split(" ");
                scores[i] = Integer.parseInt(curLine[curLine.length-1]);
                if (PlayState.getScore()>scores[i]){
                    sb=new StringBuilder();
                    curStatus = status.addScore;
                    glfwSetCharCallback(FinalProject.win,new GLFWCharCallback(){
                        @Override
                        public void invoke(long window,int codePoint){
                            if (sb.length()<=10 && !added){
                                sb.append(Character.toChars(codePoint));
                                System.out.println(sb.toString()); 
                            }
                   }
                });
                    glfwSetKeyCallback(FinalProject.win,new GLFWKeyCallback(){
                       @Override
                       public void invoke(long window,int key,int scancode,int action,int mods){
                           if (key == GLFW_KEY_ENTER && action == GLFW_TRUE && !added)
                               addScore();
                           if (key == GLFW_KEY_BACKSPACE && action == GLFW_TRUE){
                               if (sb.length()>0 && !added)
                                sb.deleteCharAt(sb.length()-1);
                           }
                       }
                    });
                }
                names[i] = combineStrings(curLine);
                //System.err.println(names[i] +" "+scores[i]);
                i++;
            }
            
            
        } catch (IOException e){
            e.printStackTrace();
            curStatus = status.fail;
            System.err.println("Can't find file");
        }
    }
    private static boolean added = false;
    public static void addScore(){
        if (!added){
        String s = sb.toString();
        try {
            if (s.length()==0){
            s = "blank";
        
        }
        s = s +"+"+ PlayState.getScore();
        s = "newScore="+s.replaceAll(" ","+");
        s = "http://zamsler.000webhostapp.com/setScore.php"+"?"+s;
        System.out.println(s);
        
        URL output = new URL(s);
        HttpURLConnection urlConnection = (HttpURLConnection) output.openConnection();
        urlConnection.setRequestMethod("GET");
       
        urlConnection.connect();
        System.out.println(urlConnection.getResponseCode());
        
        } catch (IOException e){
            
        }
        added=true;
        }
        
    }
    public static void render(){
        float f;
        switch (curStatus){
            case showScores:
                Text.renderString("High Scores", 125, 50, 120, 90);
                f =200;
                for (int i = 0;i<10;i++){
                    Text.renderString(names[i],150,f);
                    Text.renderString(""+scores[i],450,f);
                    f+= 40;
                }
                Text.renderString("Main Menu",250,625,60,45);
                break;
            case fail:
                Text.renderString("Can't load high scores",70,200,80,60);
                Text.renderString("Retry",300,300,60,45);
                Text.renderString("Main Menu",260,375,60,45);
                break;
            case addScore:
                Text.renderString("New High Score", 75, 50, 120, 90);
                int i=0;
                int count =0;
                f = 200;
                while (scores[i]>=PlayState.getScore()){
                    Text.renderString(names[i],150,f);
                    Text.renderString(""+scores[i],450,f);
                    f+= 40; 
                    i++;
                    count++;
                }
                Text.renderString(sb.toString(),150,f);
                Text.renderString(""+PlayState.getScore(),450,f);
                f+=40;
                count++;
                while (count<10){
                  Text.renderString(names[i],150,f);
                  Text.renderString(""+scores[i],450,f);
                    f+= 40;
                    i++;
                    count++;
                }  
                Text.renderString("Main Menu",250,625,60,45);
                break;
        }

    }
    
    private static String combineStrings(String[] line){
        StringBuilder b = new StringBuilder();
        for (int i =0;i<line.length-2;i++){
            b.append(line[i]+" ");
        }
        b.append(line[line.length-2]);
        return b.toString();
    }
    
    public static void handleMouseClicks(double x,double y){
        switch (curStatus){
            case fail:
                if (x >= 260 && x <= 440 && y>= 375 && y <= 375 + 45){
                    FinalProject.setState(FinalProject.State.MAINMENU);
                } else if (x>= 300 && x<= 400 && y >= 300 && y <= 345)
                    loadFile();
                break;
            case addScore:
            case showScores:
                if (x>= 250 && x<=430 && y >= 625 && y <=670){
                    
                    if (curStatus == status.addScore && !added)
                        addScore();
                    FinalProject.setState(FinalProject.State.MAINMENU);
                    curStatus=status.showScores;
                }
                break;
        }
    }
}
