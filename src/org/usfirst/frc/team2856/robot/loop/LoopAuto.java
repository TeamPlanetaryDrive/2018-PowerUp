package org.usfirst.frc.team2856.robot.loop;

import org.usfirst.frc.team2856.robot.Constants;
import org.usfirst.frc.team2856.robot.loop.StateIterator;
import org.usfirst.frc.team2856.robot.Robot;
import org.usfirst.frc.team2856.robot.drivetrain.DriveTrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LoopAuto extends Loop{	

	static String[] modes = {"l", "m", "r", "s"};
	
	//IterativeRobot robot;
	private String autoSelected;
	private Integer state;
	private DriveTrain drive;
	private StateIterator stateMachine;
	private double startPos;
	private SendableChooser<String> chooser, startDistChooser;
	private double startTime;
	//shuffleboard timer chooser
	private SendableChooser<Double> waitTimer;
	//time variables and such
	private Double waitTime = new Double(0);
	//private double prevTime = System.currentTimeMillis();
	
	// Getting Game-Specific data
	private String gameSides = "LL";
	private boolean gameSideSwitch;
	private boolean gameSideScale;
	private boolean gameSideCross;
	
	private String shuffleAuto;
	private String defaultAuto;
	
	//Names of our options for Autonomous
	private final String 
		chooserTest = "Test",
		chooserSwitch = "Switch",
		chooserScale = "Scale",
		chooserForward = "Cross the Line",
		chooserTime = "Wait Time";
	
	private String choosenCommand = "";
	
	public LoopAuto(Robot rob){
		//First instantiating through the parent class
		super(rob);
		

		// Then adding options for Autonomous mode
		chooser = new SendableChooser<String>();
		chooser.addDefault(chooserTest, chooserTest);
		chooser.addObject(chooserSwitch, chooserSwitch);
		chooser.addObject(chooserScale, chooserScale);
		chooser.addObject(chooserForward, chooserForward);
		
		waitTimer = new SendableChooser<Double>();
		waitTimer.addObject(chooserTime, waitTime);
		
		/*startDistChooser = new SendableChooser<String>();
		startDistChooser.addDefault("0", "0");
		for(int i = 3;i < 13;i+= 3) {
			startDistChooser.addObject(String.valueOf(i), String.valueOf(i));
			startDistChooser.addObject(String.valueOf(-i), String.valueOf(-i));
		}*/
		
		SmartDashboard.putData("Auto modes", chooser);
	}
	
	@Override
	public void init() {
		state = 0;
		drive = robot.driveTrain;
		stateMachine = new StateIterator(robot.driveTrain,this);
		
		System.out.println("got to init");
		gameSides = DriverStation.getInstance().getGameSpecificMessage();
		if(gameSides == null) {
			gameSides = "";
		}
		if(gameSides == ""){
			gameSides ="LLL";
		}
		
		//defaultBoardChoose();
		autoSelected = "Test";
		switch (autoSelected) {
			case "Test":
				testingAutoCommands(startPos, false);
				break;
			case "Switch":
				depositAtSwitchCommands(startPos, gameSideSwitch);
				break;
			case "Scale":
				depositAtScaleCommands(startPos, gameSideScale);
				break;
			case "Forward":
				 crossLineCommands(startPos);
				break;
			default:
				break;
		}
		
		// startPos = Double.parseDouble(SmartDashboard.getString("Starting Position", "0"));
		/*if (gameSides.charAt(0) == 'L') {
			gameSideSwitch = true;
		}
		if (gameSides.charAt(1) == 'L') {
			gameSideScale = true;
		}*/
		
		
		drive.initAuto();

		// Gyro for tracking direction of the robot
		//robot.gyro.reset();
		//robot.gyro.calibrate();
	}
	
	private void shuffleBoardChoose() {
		
		shuffleAuto = chooser.getSelected();
		System.out.println("shuffleboard: " + autoSelected);
		
		System.out.println("using shuffle dash " + autoSelected);
		autoSelected = shuffleAuto;
		
		switch(autoSelected) {
			case chooserForward:
				choosenCommand = "Forward";
				break;
			case chooserSwitch:
				choosenCommand = "Switch";
				break;
			case chooserScale:
				choosenCommand = "Scale";
				break;/*
			case chooserNumber:
				choosenCommand = "Start Distance";
				break;*/
			default:
				choosenCommand = "Test";
				break;	
		}
	}
	private void defaultBoardChoose() {
		
		defaultAuto = SmartDashboard.getString("Auto Selector", "Select Autonomous ...");
		System.out.println("default board: " + defaultAuto);
	
		SmartDashboard.updateValues();
		
		System.out.println("using default dash");
		choosenCommand = defaultAuto;
		
		switch(choosenCommand) {
			case chooserForward:
				choosenCommand = "Forward";
				break;
			case chooserSwitch:
				choosenCommand = "Switch";
				break;
			case chooserScale:
				choosenCommand = "Scale";
				break;/*
			case chooserNumber:
				choosenCommand = "Start Distance";
				break;*/
			case chooserTest:
				choosenCommand = "Test";
				break;
			default:
				choosenCommand = "";
				break;	
		}
	}
	
	public static void addModes() {
		for (int i = 0; i < modes.length; i++) {
			SmartDashboard.putString("Auto Selector", modes[i]);
		}
	}
	
	@Override
	public void loop() {
		stateMachine.update();
		drive.update(false);
	}
	
	public void adjust() {
		// Adjust the robot back on track
	}
	
	public void testingAutoCommands(double start, boolean side){

				stateMachine.add("turn", new double[]{4*Constants.MOVE_RIGHT_TURN_ANGLE});
				stateMachine.add("forward", new double[]{2});
		
	}
	public void depositAtSwitchCommands(double start, boolean side) { // left = true,
		// right = false

		stateMachine.add("forward", new double[] {5}); // clear any obstacles

		// align bot with switch
		if (side) {
			// do we have the left switch . . .
			if (start > -(6/* +manipulator length */)) {
				// if we start to the right of the switch

				stateMachine.add("turn", new double[] {-Constants.MOVE_RIGHT_TURN_ANGLE, 0});
				stateMachine.add("forward", new double[] {start + (6/* +manipulator length */)});
				stateMachine.add("turn", new double[] {Constants.MOVE_RIGHT_TURN_ANGLE, 0});
				
			} else if (start < (-6/*-manipulator length*/)) { // if we start to the left of the switch

				stateMachine.add("turn", new double[] {Constants.MOVE_RIGHT_TURN_ANGLE, 0});
				stateMachine.add("forward", new double[] {start - (6/* +manipulator length */)});
				stateMachine.add("turn", new double[] {-Constants.MOVE_RIGHT_TURN_ANGLE, 0});

			} // do nothing if we start directly adjacent to the switch

		} else { // . . . or the right switch
			if (start > 6/* +manipulator length */) { // if we start to the right of the switch

				stateMachine.add("turn", new double[] {-Constants.MOVE_RIGHT_TURN_ANGLE, 0});
				stateMachine.add("forward", new double[] {start - (6/* +manipulator length */)});
				stateMachine.add("turn", new double[] {Constants.MOVE_RIGHT_TURN_ANGLE, 0});

				// if we start to the left of the switch
			} else if (start < 6/* +manipulator length */) {

				stateMachine.add("turn", new double[] {Constants.MOVE_RIGHT_TURN_ANGLE, 0});
				stateMachine.add("forward", new double[] {-start + 6});
				stateMachine.add("turn", new double[] {-Constants.MOVE_RIGHT_TURN_ANGLE, 0});

			}

		}
		// do nothing if we start directly adjacent to the switch

		stateMachine.add("forward", new double[] {6.66 - Constants.DRIVE_BASE_LENGTH});

		// turn to switch
		if (side) {
			stateMachine.add("turn", new double[] {Constants.MOVE_RIGHT_TURN_ANGLE, 0});
		} else {
			stateMachine.add("turn", new double[] {-Constants.MOVE_RIGHT_TURN_ANGLE, 0});
		}

		// deposit the cube
		// store time
		stateMachine.add("lift", new double[] {3000, 1});

		stateMachine.add("manipulate", new double[] {1000, 1});

	}
	public void depositAtScaleCommands(double start, boolean side) { // left = true,
		// right = false

		// clear any obstacles
		stateMachine.add("forward", new double[]{5});

		// Align robot with the scale
		if(side){ // do we have the left scale . . .
			
			stateMachine.add("turn", new double[]{-Constants.MOVE_RIGHT_TURN_ANGLE, 0});
			stateMachine.add("forward", new double[]{start + 11});
			stateMachine.add("turn", new double[]{Constants.MOVE_RIGHT_TURN_ANGLE, 0});


		} else { // . . . or the right scale

			stateMachine.add("turn", new double[]{Constants.MOVE_RIGHT_TURN_ANGLE, 0});
			stateMachine.add("moveStraight", new double[]{start + 11});
			stateMachine.add("turn", new double[]{-Constants.MOVE_RIGHT_TURN_ANGLE, 0});

		}

		// Move to the center of the arena

		stateMachine.add("forward", new double[]{22 - Constants.DRIVE_BASE_LENGTH});
		
		// turn to face the scale
		if (side) {
			stateMachine.add("turn", new double[]{Constants.MOVE_RIGHT_TURN_ANGLE, 0});
			
		} else {
			stateMachine.add("turn", new double[]{-Constants.MOVE_RIGHT_TURN_ANGLE, 0});
		}
		
		stateMachine.add("forward", new double[]{5 - Constants.DRIVE_BASE_LENGTH});

		stateMachine.add("lift", new double[] {3000, 1});

		stateMachine.add("manipulate", new double[] {1000, 1});
		
	}
	public void crossLineCommands(double start) {

		if (start < 13 && start < 6 || start < -13 && start > -6) {
			stateMachine.add("forward",new double[] {13});
		}
		if (start >= 0 && start <= 4.5) {
			stateMachine.add("forward",new double[] {1});
			stateMachine.add("turn",new double[] {Constants.MOVE_RIGHT_TURN_ANGLE, 0});
			stateMachine.add("forward",new double[] {9 - start});
			stateMachine.add("turn",new double[] {-Constants.MOVE_RIGHT_TURN_ANGLE, 0});
			stateMachine.add("forward",new double[] {12});
		}

		if (start < 0 && start >= -6) {
			stateMachine.add("forward",new double[] {1});
			stateMachine.add("turn",new double[] {-Constants.MOVE_RIGHT_TURN_ANGLE, 0});
			stateMachine.add("forward",new double[] {9 - start});
			stateMachine.add("turn",new double[] {Constants.MOVE_RIGHT_TURN_ANGLE, 0});
			stateMachine.add("forward",new double[] {12});
		}
	}
	
	public void waitTimer (double prevTime) {
		if (waitTime != 0){
			if (state == 0) {
				waitTime = waitTimer.getSelected();
				if ((System.currentTimeMillis() - prevTime) >= waitTime) {
					state ++ ; 
				}
				else {
					return;
				}
			}
		}
		state++;
	}
}

//Commented out code because it won't do anything
/*public void parseCommand(String command){
* String part1 = command.substring(0, command.indexOf(","));
* command = command.substring(command.indexOf(",")+1)
* String part2 = command.substring(0, command.indexOf(","));
* String part3 = command.substring(command.indexOf(",")+1);
* boolean side;
* 
* part1 = part1.toLowerCase;
* 
* if(part1 = "left")
* 		side = true;
* else if(part1 = "right")
* 		side = false;
* 
* part3 = part3.toLowerCase();
* 
* switch(part3){
* 	case "test":
* 		startPos = Double.parseDouble(part2);
* 		choosenCommand = "Test";
* 		break;
* 	case "cross":
* 		gameSideCross = side;
* 		startPos = Double.parseDouble(part2);
* 		choosenCommand = "Forward";
*  	break;
* 	case "switch":
* 		gameSideSwitch = side;
* 		startPos = Double.parseDouble(part2);
* 		choosenCommand = "Switch";
* 		break;
* 	case "scale":
* 		gameSideScale = side;
* 		startPos = Double.parseDouble(part2);
* 		choosenCommand = "Scale";
* 		break;
* 	default:
* 		break;
* 	}
*}
*/