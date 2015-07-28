/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainner;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opencv.core.Core;

/**
 *
 * @author USER
 */
public class Trainner extends Application {
    private static int TainingCount=0;
    private static String TainingDir ="Trainning_Set/";
    private static String UserId="Default";
    private static Integer camraState;
    
    private static int student_status=0;

    public static int getStudent_status() {
        return student_status;
    }

    public static Integer getCamraState() {
        return camraState;
    }

    public static void setCamraState(Integer camraState) {
        Trainner.camraState = camraState;
    }
    
    static void setCamara(Integer valueOf) {
       camraState = valueOf;
    }
    static Integer getCamara() {
      return camraState;
    }
    private int CurrentCount=0;
   
    public int getCurrentCount() {
        return CurrentCount;
    }

    public void setCurrentCount(int CurrentCount) {
        this.CurrentCount = CurrentCount;
    }
    public static void setTainingCount(int TainingCount) {
        Trainner.TainingCount = TainingCount;
    }

    public static void setTainingDir(String TainingDir) {
        Trainner.TainingDir = TainingDir;
    }

    public static void setUserId(String UserId) {
        Trainner.UserId = UserId;
    }

    public static void setStudent_status(int student_status) {
        student_status = student_status;
    }

    public static int getTainingCount() {
        return TainingCount;
    }

    public static String getTainingDir() {
        return TainingDir;
    }

    public static String getUserId() {
        return UserId;
    }

  
    
   
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
       launch(args);
       
       
    }
    
}
