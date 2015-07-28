/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainner;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
/**
 *
 * @author USER
 */
public class FXMLDocumentController implements Initializable {

 // public static camara camaraInit;
    static void StartTrain() {
      //startCamera();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @FXML
    private Label label;
    boolean cameraActive;
    VideoCapture capture_obj;
    
    private Timer timer;
    private Image CamStream;
    @FXML
    static ImageView t_im1;
    @FXML
    static ImageView t_im2;
    @FXML
     static ImageView t_im3;
    @FXML
    private ImageView trainer_im;
    @FXML
    private Button t_redoC;
    private TimerTask frameGrabber;
    int amount=4;
    private AnchorPane tab;
    @FXML
    private Button rec;
    
    @FXML
    private AnchorPane tab1;
    private Mat frame;
    @FXML
    private CheckBox ol_stu;
    @FXML
    private CheckBox new_Stu;
    @FXML
    private TextField mat_nu;
    @FXML
    private TextField t_imageSetC;
    @FXML
    private Button capture;
    @FXML
    private Button save_rec;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // mat_nu.setText("Default");
        capture_obj = new VideoCapture(0);
        amount = Trainner.getTainingCount();
      startCamera();
    }    
    
   

    @FXML
    private void takeCap(ActionEvent event) {
        //this.capture.setText("Next Capture");
        this.t_redoC.setDisable(false);
       if(cameraActive&&amount!=0)
        {
          
      // capture_obj.grab();
           this.timer.cancel();
           this.timer=null;
         //   System.out.println(" final "+CamStream);
           // trainer_im.setImage(CamStream);
            amount=Trainner.getTainingCount()-1;
            Trainner.setTainingCount(amount);
            capture_obj.release();
            this.cameraActive=false;
      
             //Register();
            sendto_preview();
        
        }
        else
        {
      startCamera();
        
        }
    }

 protected void startCamera() {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    if (!this.cameraActive)
		{
			// disable setting checkboxes
			
			
			// start the video capture
			this.capture_obj.open(0);
			
			// is the video stream available?
			if (this.capture_obj.isOpened())
			{
				this.cameraActive = true;
				System.out.println("hello camara up");
                            // grab a frame every 33 ms (30 frames/sec)
                                 frameGrabber = new TimerTask() {
                                @Override
                                public void run()
                                {
                                    CamStream = grabFrame();
//                                    System.out.println(CamStream.toString());
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            trainer_im.setImage(CamStream);
                                            trainer_im.setSmooth(true);
                                            trainer_im.setMouseTransparent(true);
                                            trainer_im.setFitWidth(600);
                                            trainer_im.setPreserveRatio(true);
                                        }
                                    });
                                }
                            };
				this.timer = new Timer();
				this.timer.schedule(frameGrabber, 0, 33);
				
			}
			else
			{
                            System.err.println("Failed to open the camera connection...");
			}
		}
		else
		{
			// the camera is not active at this point
			this.cameraActive = false;
			// update again the button content
			//this.capture.setText("Capture Next");
			// enable setting checkboxes
			
			
			// stop the timer
			if (this.timer != null)
			{
				this.timer.cancel();
				this.timer = null;
			}
			// release the camera
			this.capture_obj.release();
			// clean the image area
			trainer_im.setImage(null);
		}
    
    }
    private Image grabFrame()
	{
		// init everything
		Image imageToShow = null;
		 frame = new Mat();
		
		// check if the capture is open
		if (this.capture_obj.isOpened())
		{
			try
			{
				// read the current frame
				this.capture_obj.read(frame);
				
				// if the frame is not empty, process it
				if (!frame.empty())
				{
					// face detection
					//this.detectAndDisplay(frame);
					MatOfRect faces = new MatOfRect();
                                        Mat grayFrame = new Mat();
		
                                         // convert the frame in gray scale
                                         Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2YUV_I420);
                                         // equalize the frame histogram to improve the result
                                         Imgproc.equalizeHist(grayFrame, grayFrame);
                                            // convert the Mat object (OpenCV) to Image (JavaFX)
					imageToShow = mat2Image(frame);
                                          boolean file = new File(Trainner.getTainingDir()).mkdir();
                                          Register(frame);
                                          amount=Trainner.getTainingCount()-1;
                                          Trainner.setTainingCount(amount);
                                
                                            
                                            
				
			}
                        }
			catch (Exception e)
			{
				// log the (full) error
				System.err.print("ERROR");
				e.printStackTrace();
			}
		
                }
		return imageToShow;
	}
       
        
    private Image mat2Image(Mat frame) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  MatOfByte buffer = new MatOfByte();
		// encode the frame in the buffer, according to the PNG format
		Highgui.imencode(".jpg", frame, buffer);
		// build and return an Image created from the image encoded in the
		// buffer
		return new Image(new ByteArrayInputStream(buffer.toArray()));
    }


    private void sendto_preview() {
     //  trainer_im.setImage(CamStream); //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    switch(amount)
    {
        case 1:
            FXMLDocumentController.t_im3.setImage(CamStream);
          
            break;
        case 2:
            FXMLDocumentController.t_im2.setImage(CamStream);
            break;
        case 3:
            FXMLDocumentController.t_im1.setImage(CamStream);
            break;
                 
           default:
               System.out.println("amount "+amount);
    }
    }
     protected  void Register(Mat imFrame) 
    {
        //Image needs to be proceesed before storing
        //Storng include
        /*
        1) Tacking the face 
        2) Perform Geomatrical transformation and cropping to remove the various lightening effects
        3)Perform Histogram Equalization fo the left and right sides of the face 
        to standardise the brightness and contrast on both sides
        4) Image smoothnening to remove pixel noisegi
        5) Perform Elliptical mask to remove remainning hair and background from the face
        
        
        */
        System.out.println(frame.toString());
           Highgui.imwrite(Trainner.getTainingDir()+"/"+Trainner.getUserId()+"_"+Trainner.getTainingCount()+".jpg", imFrame);
    }

    @FXML
    private void save_User_Data(ActionEvent event) {
        
       //System.out.println("Hello");
         if((mat_nu.getText()!=null&&mat_nu.getText()!="")&&(t_imageSetC.getText()!=null&&t_imageSetC.getText()!=""))
         {
        Trainner.setUserId(mat_nu.getText().toString());
       try{ 
           Trainner.setTainingCount(Integer.parseInt(t_imageSetC.getText()));
       } 
       catch(Exception vv)
       {
       
       }
       capture.setDisable(false);
    }
         else
         {
             Dialogs.create()
                     .masthead("Data Error")
                     .message("User Matricule and image count cant be null")
                     .showError();
                     
         }
    } 

    @FXML
    private void SetO(ActionEvent event) {
        new_Stu.setSelected(false);
        Trainner.setStudent_status(0);
    }

    @FXML
    private void SetN(ActionEvent event) {
        ol_stu.setSelected(false);
        Trainner.setStudent_status(1);
    
    }

    @FXML
    private void RedoCapture(ActionEvent event) {
    }

   
   }
    
