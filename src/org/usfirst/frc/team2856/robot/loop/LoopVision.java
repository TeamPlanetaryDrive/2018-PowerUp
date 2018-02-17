package org.usfirst.frc.team2856.robot.loop;

import org.usfirst.frc.team2856.robot.Robot;

//import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class LoopVision extends Loop{
	
	public LoopVision(Robot rob){super(rob);}
	
	public void init() {
		
		/*
		Thread camThread;
		camThread = new Thread(() -> {
			CameraServer.getInstance().addAxisCamera("10.28.56.11");
			CvSink cvSink = CameraServer.getInstance().getVideo();
			CvSource oStream = CameraServer.getInstance().putVideo("Video Stream", 640, 480);
			
			Mat frame = new Mat();
//			Mat out = new Mat();
			
			
			while(true){
				cvSink.grabFrame(frame);
				
				Mat blurredImage = new Mat();
				Mat hsvImage = new Mat();
				Mat mask = new Mat();
				Mat morphOutput = new Mat();
				
				
				//remove noise
				Imgproc.blur(frame, blurredImage, new Size(7, 7));
				
				
				//HSV
				Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_BGR2HSV);
				
				Core.inRange(hsvImage, new Scalar(171 / 2, 255*86/100, 255*30/100), new Scalar(112, 255, 255*50/100), mask);
				
				
				//erode & dilate
				Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 24));
				Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(12, 12));
				
				Imgproc.erode(mask, morphOutput, erodeElement);
				Imgproc.erode(mask, morphOutput, erodeElement);
				
				Imgproc.dilate(mask, morphOutput, dilateElement);
				Imgproc.dilate(mask, morphOutput, dilateElement);
				
				//Contours
				List<MatOfPoint> contours = new ArrayList<>();
				Mat hierarchy = new Mat();
				
				Imgproc.findContours(morphOutput, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
		
				// if any contour exist...
				if (hierarchy.size().height > 0 && hierarchy.size().width > 0) {
					// for each contour, display it in blue
					for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0]) {
						Imgproc.drawContours(frame, contours, idx, new Scalar(250, 250, 250));
					}
				}
				
				
				
				oStream.putFrame(frame);
			}
		});
		
		camThread.start();
		*/
		//AxisCamera a = 
		CameraServer.getInstance().addAxisCamera("axis-camera.local");
		CameraServer.getInstance().startAutomaticCapture();
	}

	public void loop() {
		
	}

}
