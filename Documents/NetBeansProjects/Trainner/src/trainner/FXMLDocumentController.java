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
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.equalizeHist;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import static org.opencv.objdetect.Objdetect.CASCADE_SCALE_IMAGE;

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
    private CascadeClassifier faceCascade;
	// minimum face size
	private int absoluteFaceSize;
	
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
    int amount = 4;
    private AnchorPane tab;
    @FXML
    private Button rec;

    @FXML
    private AnchorPane tab1;
    private Mat frame;
    @FXML
    private TextField mat_nu;
    @FXML
    private TextField t_imageSetC;
    @FXML
    private Button capture;
    @FXML
    private Button save_rec;
    private trainee user;
    @FXML
    private CheckBox haarClassifier;
    @FXML
    private CheckBox lbpClassifier;
  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = new trainee();
        // mat_nu.setText("Default");
      
        this.faceCascade = new CascadeClassifier();
	this.absoluteFaceSize = 0;
        amount = user.getTainingCount();
        
    }

    @FXML
    private void takeCap(ActionEvent event) {
        //this.capture.setText("Next Capture");
        this.t_redoC.setDisable(false);
        Register(frame);
        
          

        
    }

    protected void startCamera() {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (!this.cameraActive) {
			// disable setting checkboxes
            this.haarClassifier.setDisable(true);
			this.lbpClassifier.setDisable(true);
            // start the video capture
            this.capture_obj.open(0);

            // is the video stream available?
            if (this.capture_obj.isOpened()) {
                this.cameraActive = true;
                System.out.println("hello camara up");
                // grab a frame every 33 ms (30 frames/sec)
                frameGrabber = new TimerTask() {
                    @Override
                    public void run() {
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

            } else {
                System.err.println("Failed to open the camera connection...");
            }
        } else {
            // the camera is not active at this point
            this.cameraActive = false;
			// update again the button content
            //this.capture.setText("Capture Next");
            // enable setting checkboxes

            // stop the timer
            if (this.timer != null) {
                this.timer.cancel();
                this.timer = null;
            }
            // release the camera
            this.capture_obj.release();
            // clean the image area
            trainer_im.setImage(null);
        }

    }

    private Image grabFrame() {
        // init everything
        Image imageToShow = null;
        frame = new Mat();

        // check if the capture is open
        if (this.capture_obj.isOpened()) {
            try {
                // read the current frame
                this.capture_obj.read(frame);

                // if the frame is not empty, process it
                if (!frame.empty()) {
					
                    
                    MatOfRect faces = new MatOfRect();
                    Mat grayFrame = new Mat();

                    // convert the frame in gray scale
                    Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2YUV_I420);
                    // equalize the frame histogram to improve the result
                    Imgproc.equalizeHist(grayFrame, grayFrame);
                    // convert the Mat object (OpenCV) to Image (JavaFX)
                    this.detectAndDisplay(frame);
                    imageToShow = mat2Image(frame);
                    boolean file = new File(user.getTainingDir()).mkdir();
                    

                }
            } catch (Exception e) {
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
        switch (amount) {
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
                System.out.println("amount " + amount);
        }
    }

    protected void Register(Mat imFrame) {
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
        Mat Test = null;
        equalizeHist(frame,Test);
        System.out.println(frame.toString());
        if(user.getTainingCount()==0)
            Dialogs.create().message("This is the last alocated Slot for a Face").showInformation();
        Highgui.imwrite(user.getTainingDir() + "/" + user.getUserId() + "_" + user.getTainingCount() + ".jpg", imFrame);
        String location =user.getTainingDir() + "/" + user.getUserId() + "_" + user.getTainingCount();
        user.saveToDb(location,user.getUserId());
        amount = user.getTainingCount() - 1;
            user.setTainingCount(amount);
            t_imageSetC.setText(""+amount);
          if(user.getTainingCount()<0)
          {
            capture.setDisable(true);
            stopCamera();
          }
            
    }

    @FXML
    private void save_User_Data(ActionEvent event) {
         capture_obj = new VideoCapture(0);
        //System.out.println("Hello");
         rec.setDisable(true);
         user.setTainingCount(Integer.parseInt(t_imageSetC.getText()));
         user.setUserId(mat_nu.getText());
         startCamera();
         capture.setDisable(false);
       
    }

 private void detectAndDisplay(Mat frames)
	{
		// init
		MatOfRect faces = new MatOfRect();
		Mat grayFrame = new Mat();
		
		// convert the frame in gray scale
		Imgproc.cvtColor(frames, grayFrame, Imgproc.COLOR_BGR2YUV_I420);
		// equalize the frame histogram to improve the result
		Imgproc.equalizeHist(grayFrame, grayFrame);
		int flags = CASCADE_SCALE_IMAGE;
		// compute minimum face size (20% of the frame height)
		if (this.absoluteFaceSize == 0)
		{
			int height = grayFrame.rows();
			if (Math.round(height * 0.2f) > 0)
			{
				this.absoluteFaceSize = Math.round(height * 0.2f);
			}
		}
		
		// detect faces
		this.faceCascade.detectMultiScale(grayFrame, faces, 1.1f, 4, 0 | Objdetect.CASCADE_SCALE_IMAGE, new Size(
				20, 20), new Size());
		
		// each rectangle in faces is a face
		Rect[] facesArray = faces.toArray();
		for (int i = 0; i < facesArray.length; i++){
			Core.rectangle(frames, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0, 255), 3);
                           //Imgproc.equalizeHist(frames, frames);
                }
//Mat faceImg = cameraImg(facesArray[0]);
              
	}       
	
    @FXML
    private void RedoCapture(ActionEvent event) 
    {
    }

    @FXML
    private void haarSelected(ActionEvent event) {
    	// check whether the lpb checkbox is selected and deselect it
		if (this.lbpClassifier.isSelected())
			this.lbpClassifier.setSelected(false);
		
		this.checkboxSelection("resources/haarcascades/haarcascade_frontalface_default.xml");
	
    
    }

    @FXML
    private void lbpSelected(ActionEvent event) {
 	if (this.haarClassifier.isSelected())
			this.haarClassifier.setSelected(false);
		
		this.checkboxSelection("resources/lbpcascades/lbpcascade_frontalface.xml");
	
    }

    @FXML
    private void discard(ActionEvent event) {
    }

  private void checkboxSelection(String... classifierPath)
	{
		// load the classifier(s)
		for (String xmlClassifier : classifierPath)
		{
			this.faceCascade.load(xmlClassifier);
		}
		
		// now the capture can start
	//	this.cameraButton.setDisable(false);
	}

    private void stopCamera() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     trainer_im.setImage(null);
     timer=null;
     
    
    }
	
}
