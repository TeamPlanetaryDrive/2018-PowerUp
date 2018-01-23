
package org.usfirst.frc.team2856.robot;

//import java.util.*;

//import org.opencv.core.*;
//import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2856.robot.drivetrain.DriveTrain;
import org.usfirst.frc.team2856.robot.loop.*;

//import edu.wpi.cscore.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	//DEFAULT VARS
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
//  SendableChooser chooser;
	
    
    //Loops
    LoopVision camera;
    LoopTele tele;
    LoopAuto auto;
    
    //Joysticks
    public DriveTrain driveTrain;
    //public Shooter shooter;
    //public Climber climber;
    //public Intake intake;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	/*
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);
        */
    	Constants.init();
    	
    	LoopAuto.addModes();
    	
    	camera = new LoopVision(this);
    	tele = new LoopTele(this);
    	auto = new LoopAuto(this);
    	
    	camera.init();
    	
    	driveTrain = new DriveTrain();
    	//shooter = new Shooter();
    	//intake = new Intake();
    	//climber = new Climber();
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
//    	autoSelected = (String) chooser.getSelected();
		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
//		SmartDashboard.putString("Auto Selector", "asasd");
		System.out.println("Auto selected: " + autoSelected);
		
		
//		driveTrain.setSetpoint(10);//XXX
//		driveTrain.enable();
		
		
		auto.init();
		
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	auto.loop();
    }
    
    public void teleopInit() {
//    	driveTrain.disable();
    	
    	tele.init();
    	
    	
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	tele.loop();
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
//This is only here so I can attempt to commit the code again