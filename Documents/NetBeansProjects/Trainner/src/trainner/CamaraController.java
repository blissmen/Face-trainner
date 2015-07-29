/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainner;

import General.ControlledScreen;
import General.ScreensController;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
//import static trainner.FXMLDocumentController.camaraInit;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class CamaraController implements Initializable, ControlledScreen{
    boolean cameraActive;
    int amount=4;
    VideoCapture capture_obj;
    
    @FXML
    private AnchorPane tab;
    private Timer timer;
    private Image CamStream;
    private TimerTask frameGrabber;
    @FXML
    private ImageView trainer_im;

    ScreensController sc;
    private static Mat frame;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        capture_obj = new VideoCapture(0);
      
    }    

    @Override
    public void setScreenParent(ScreensController pane) {
        sc=pane;
// throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
