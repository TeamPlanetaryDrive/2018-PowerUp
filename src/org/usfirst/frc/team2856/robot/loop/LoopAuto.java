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
	private DriveTrain drive;
	private StateIterator stateMachine;
	private double startPos, delay;
	private SendableChooser<String> chooser, startDistChooser;
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
	
	//Names of our options for Autonomous
	private final String 
		chooserTest = "Test",
		chooserSwitch = "Switch",
		chooserScale = "Scale",
		chooserForward = "Forward",
		chooserTime = "Wait Time",
		numberDelay = "Delay",
		numberStart = "Start position";
	
		
	
	public LoopAuto(Robot rob){
		//First instantiating through the parent class
		super(rob);
		
		SmartDashboard.putNumber(numberDelay, 0);
		SmartDashboard.putNumber(numberStart, 0);

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
		String autoSelected = "test";
		
		drive = robot.driveTrain;
		stateMachine = new StateIterator(robot.driveTrain,this);
		gameSides = DriverStation.getInstance().getGameSpecificMessage();
		
		if(gameSides == null) {
			gameSides = "";
		}
		if(gameSides.equals("")){
			gameSides ="LLL";
		}
		
		autoSelected = shuffleBoardChoose();
		//autoSelected = defaultBoardChoose();
		
		System.out.println("default selected printout: " + autoSelected);
		
		
		
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
				testingAutoCommands(startPos, false);
				break;
		}
		System.out.println(gameSides);
		if(gameSides != null && gameSides != "") {
			if (gameSides.charAt(0) == 'L') {
				gameSideSwitch = true;
			}
			if (gameSides.charAt(1) == 'L') {
				gameSideScale = true;
			}
		}
		drive.initAuto();

		// Gyro for tracking direction of the robot
		//robot.gyro.reset();
	}
	
	private String shuffleBoardChoose() {
		String returnVal = "";
		String commandChosen = chooser.getSelected();
		
		System.out.println("shuffleboard command chosen: " + commandChosen);
		
		switch(commandChosen) {
			case chooserForward:
				returnVal = "Forward";
				break;
			case chooserSwitch:
				returnVal = "Switch";
				break;
			case chooserScale:
				returnVal = "Scale";
				break;
			default:
				returnVal = "Test";
				break;	
		}

		startPos = Double.parseDouble(SmartDashboard.getString("Starting Position", "0"));
		
		delay = SmartDashboard.getNumber(numberDelay, 0);
		startPos = SmartDashboard.getNumber(numberStart, 0);
		
		return returnVal;
	}
	
	private String defaultBoardChoose() {
		String returnVal = "", commandChosen = "Forward", startingDelay = "10", startingPosition = "0";
		String commands = SmartDashboard.getString("Auto Selector", "Select Autonomous ...");
		System.out.println("default board command chosen: " + commands);
		
		String commandList[] = commands.split(",");
		for(String s: commandList) {
			String[] sList = s.split(":");
			System.out.println(sList[0]);
			if(sList[0].equals("command")){
				commandChosen = sList[1];
			}
			if(sList[0].equals("position")){
				startingPosition = sList[1];
			}
			if(sList[0].equals("delay")){
				/*System.out.print("delay value: ");
				System.out.println(startingDelay);*/
				startingDelay = sList[1];
			}
		}
		
		delay = Double.parseDouble(startingDelay);
		
		startPos = Double.parseDouble(startingPosition);
		
		//SmartDashboard.updateValues();
		
		switch(commands.trim()) {
			case chooserForward:
				returnVal = "Forward";
				break;
			case chooserSwitch:
				returnVal = "Switch";
				break;
			case chooserScale:
				returnVal = "Scale";
				break;
			case chooserTest:
				returnVal = "Test";
				break;
			default:
				returnVal = "";
				break;	
		}
		return returnVal;
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
		if(true)
		return;
		stateMachine.add("delay", new double[]{delay});
		stateMachine.add("turn", new double[]{4*Constants.MOVE_RIGHT_TURN_ANGLE});
		stateMachine.add("forward", new double[]{2});
	}
	public void depositAtSwitchCommands(double start, boolean side) { // left = true,
		// right = false

		stateMachine.add("delay", new double[]{delay});
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

		stateMachine.add("delay", new double[]{delay});
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

		stateMachine.add("delay", new double[]{delay});
		if (start < -6|| start>6) {
			stateMachine.add("forward",new double[] {13});
		}
		if (start >= 0 && start <= 6.5) {
			stateMachine.add("forward",new double[] {1});
			stateMachine.add("turn",new double[] {Constants.MOVE_RIGHT_TURN_ANGLE, 0});
			stateMachine.add("forward",new double[] {6 - start});
			stateMachine.add("turn",new double[] {-Constants.MOVE_RIGHT_TURN_ANGLE, 0});
			stateMachine.add("forward",new double[] {12});
		}

		if (start < 0 && start >= -6.5) {
			stateMachine.add("forward",new double[] {1});
			stateMachine.add("turn",new double[] {-Constants.MOVE_RIGHT_TURN_ANGLE, 0});
			stateMachine.add("forward",new double[] {6 - start});
			stateMachine.add("turn",new double[] {Constants.MOVE_RIGHT_TURN_ANGLE, 0});
			stateMachine.add("forward",new double[] {12});
		}
	}
}