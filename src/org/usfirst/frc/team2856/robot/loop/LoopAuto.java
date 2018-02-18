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
		stateMachine = new StateIterator(robot.driveTrain,this);

		// Then adding options for Autonomous mode
		chooser = new SendableChooser<String>();
		chooser.addDefault(chooserTest, chooserTest);
		chooser.addObject(chooserSwitch, chooserSwitch);
		chooser.addObject(chooserScale, chooserScale);
		chooser.addObject(chooserForward, chooserForward);
		
		waitTimer = new SendableChooser<Double>();
		waitTimer.addObject(chooserTime, waitTime);
		
		startDistChooser = new SendableChooser<String>();
		startDistChooser.addDefault("0", "0");
		for(int i = 3;i < 13;i+= 3) {
			startDistChooser.addObject(String.valueOf(i), String.valueOf(i));
			startDistChooser.addObject(String.valueOf(-i), String.valueOf(-i));
		}
		
		SmartDashboard.putData("Auto modes", chooser);

		/*
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.charAt(0) == 'L')
		{
			//Put left auto code here
			System.out.println("Left");
		} else {
			//Put right auto code here
			System.out.println("Right");
		}
		*/
	}
	
	@Override
	public void init() {
		System.out.println("got to init");
		gameSides = DriverStation.getInstance().getGameSpecificMessage();
		// it cant be null
		if(gameSides == null){
			gameSides ="LL";
		}
		
		shuffleAuto = chooser.getSelected();
		System.out.println("shuffleboard: " + autoSelected);
		
		defaultAuto = SmartDashboard.getString("Auto Selector", "Select Autonomous ...");
		System.out.println("default board: " + defaultAuto);
		
		if(defaultAuto.equals("s")) {
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
		else {
			System.out.println("using default dash");
			autoSelected = defaultAuto;
			choosenCommand = autoSelected;
		}
		
		state = 0;

		// startPos = Double.parseDouble(SmartDashboard.getString("Starting
		// Position", "0"));
		/*if (gameSides.charAt(0) == 'L') {
			gameSideSwitch = true;
		}
		if (gameSides.charAt(1) == 'L') {
			gameSideScale = true;
		}*/
		
		drive = robot.driveTrain;
		drive.initAuto();

		// Gyro for tracking direction of the robot
		//robot.gyro.reset();
		//robot.gyro.calibrate();
	}
	
	private void shuffleBoardChoose() {
		
	}
	private void defaultBoardChoose() {
		
	}
	@Override
	public void loop() {
		this.switchAuto(choosenCommand, startPos);
		drive.update(false);
	}

	public static void addModes() {
		for (int i = 0; i < modes.length; i++) {
			SmartDashboard.putString("Auto Selector", modes[i]);
		}
	}
	
	//Controls the switching of the functions in Auto
	//E.g. putting power boxes on the switch
	public void switchAuto(String mode, double start) {
		if (state == 0)
		{
			//System.out.println("switchAuto: " + mode);
		}
		switch (mode) {
			case "Test":
				this.testingAuto(start, false);
				break;
			case "Switch":
				this.depositAtSwitch(start, gameSideSwitch);
				break;
			case "Scale":
				this.depositAtScale(start, gameSideScale);
				break;
			case "Forward":
				 this.crossLine(start, gameSideCross);
				break;
			default:
				break;
		}
	}	

	public void adjust() {
		// Adjust the robot back on track
	}
	
	public void testingAuto(double start, boolean side){
		if (state == 0) {
			if (!robot.driveTrain.moveGetActive()) {
				System.out.println(state);
				System.out.println("driving forward");
				//previous parameter value: 5
				robot.driveTrain.moveTurn(360*1.25, 0);
				state++;
			}
			return;
		}
		if (state == 1) {
			if (!robot.driveTrain.moveGetActive()) {
				System.out.println(state);
				System.out.println(state);
				//previous first parameter value: 90*1.25
				robot.driveTrain.moveStraight(2);
				state++;
			}
			return;
		}
		if(state == 2) {
			state++;
			System.out.println(state);
		}
		
	}
	
	public void depositAtSwitch(double start, boolean side) { // left = true,
																// right = false
		if (state == 1) {
			if (!robot.driveTrain.moveGetActive()) {
				robot.driveTrain.moveStraight(5); // clear any obstacles
				state++;
			}
			return;
		}
		// align bot with switch
		if (side) { 
			// do we have the left switch . . .
			if (start > -(6/*+manipulator length*/)) { 
				// if we start to the right of the switch

				if (state == 2) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(-Constants.MOVE_RIGHT_TURN_ANGLE, 0);
						state++;
					}
					return;
				}
				if (state == 3) {
					if (!robot.driveTrain.moveGetActive()) {

						robot.driveTrain.moveStraight(start + (6/*+manipulator length*/));
						state++;
					}
					return;
				}
				if (state == 4) {
					if (!robot.driveTrain.moveGetActive()) {

						robot.driveTrain.moveTurn(Constants.MOVE_RIGHT_TURN_ANGLE, 0);
						state++;
					}
					return;
				}

			} else if (start < (-6/*-manipulator length*/)) { // if we start to the left of the switch
				if (state == 2) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(Constants.MOVE_RIGHT_TURN_ANGLE, 0);
						state++;
					}
					return;
				}
				if (state == 3) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveStraight(start-6/*-manipulator length*/);//klklklklklk
						state++;
					}
					return;
				}
				if (state == 4) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(-Constants.MOVE_RIGHT_TURN_ANGLE, 0);
						state++;
					}
					return;
				}
			} // do nothing if we start directly adjacent to the switch

		} else { // . . . or the right switch
			if (start > 6/*+manipulator length*/) { // if we start to the right of the switch
				if (state == 2) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(-Constants.MOVE_RIGHT_TURN_ANGLE, 0);
						state++;
					}
					return;
				}
				if (state == 3) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveStraight(start - 6/*-manipulator length*/);
						state++;
					}
					return;
				}
				if (state == 4) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(Constants.MOVE_RIGHT_TURN_ANGLE, 0);
						state++;
					}
					return;
				}
				// if we start to the left of the switch
			} else if (start < 6/*+manipulator length*/) {
				if (state == 2) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(Constants.MOVE_RIGHT_TURN_ANGLE, 1);
						state++;
					}
					return;
				}
				if (state == 3) {
					if (!robot.driveTrain.moveGetActive()) {
						System.out.println("moved to ");
						robot.driveTrain.moveStraight(-start + 6);
						state++;
					}
					return;
				}
				if (state == 4) {
					if (!robot.driveTrain.moveGetActive()) {
						robot.driveTrain.moveTurn(-Constants.MOVE_RIGHT_TURN_ANGLE, 0);
						state++;
					}
					return;
				}

			}
			// do nothing if we start directly adjacent to the switch

		}

		if (state == 5) {
			if (!robot.driveTrain.moveGetActive()) {
				robot.driveTrain.moveStraight(6.66 - Constants.DRIVE_BASE_LENGTH);
				state++;
			}
			return;
		}
		//turn to switch
		if(state == 6) {
			if (!robot.driveTrain.moveGetActive()) {
				if(side) {
					robot.driveTrain.moveTurn(Constants.MOVE_RIGHT_TURN_ANGLE, 0);
				}
				else {
					robot.driveTrain.moveTurn(-Constants.MOVE_RIGHT_TURN_ANGLE, 0);
				}
			}
			return;
		}
		// deposit the cube
		if (state == 7) {
			//store time
			startTime = System.currentTimeMillis();
			// update on time required
			robot.lift.liftUp(1);
			state++;
			return;
		}
		if (state == 8) {
			//is time up
			if (System.currentTimeMillis() - startTime > 3000) {
				robot.lift.liftStop();
				state++;
			}
			return;
		}
		if (state == 9) {
			robot.manipulator.pullOut(1);
			state++;
			return;
		}

	}
	
	
	
	public void depositAtSwitchCommands(double start, boolean side) { // left = true,
		// right = false

		robot.driveTrain.moveStraight(5); // clear any obstacles

		// align bot with switch
		if (side) {
			// do we have the left switch . . .
			if (start > -(6/* +manipulator length */)) {
				// if we start to the right of the switch

				robot.driveTrain.moveTurn(-Constants.MOVE_RIGHT_TURN_ANGLE, 0);
				robot.driveTrain.moveStraight(start + (6/* +manipulator length */));
				robot.driveTrain.moveTurn(Constants.MOVE_RIGHT_TURN_ANGLE, 0);
				
			} else if (start < (-6/*-manipulator length*/)) { // if we start to the left of the switch

				robot.driveTrain.moveTurn(Constants.MOVE_RIGHT_TURN_ANGLE, 0);
				robot.driveTrain.moveStraight(start - 6/*-manipulator length*/);
				robot.driveTrain.moveTurn(-Constants.MOVE_RIGHT_TURN_ANGLE, 0);

			} // do nothing if we start directly adjacent to the switch

		} else { // . . . or the right switch
			if (start > 6/* +manipulator length */) { // if we start to the right of the switch

				robot.driveTrain.moveTurn(-Constants.MOVE_RIGHT_TURN_ANGLE, 0);
				robot.driveTrain.moveStraight(start - 6/*-manipulator length*/);
				robot.driveTrain.moveTurn(Constants.MOVE_RIGHT_TURN_ANGLE, 0);

				// if we start to the left of the switch
			} else if (start < 6/* +manipulator length */) {

				robot.driveTrain.moveTurn(Constants.MOVE_RIGHT_TURN_ANGLE, 1);
				System.out.println("moved to ");
				robot.driveTrain.moveStraight(-start + 6);
				robot.driveTrain.moveTurn(-Constants.MOVE_RIGHT_TURN_ANGLE, 0);

			}

		}
		// do nothing if we start directly adjacent to the switch

		robot.driveTrain.moveStraight(6.66 - Constants.DRIVE_BASE_LENGTH);

		// turn to switch
		if (side) {
			robot.driveTrain.moveTurn(Constants.MOVE_RIGHT_TURN_ANGLE, 0);
		} else {
			robot.driveTrain.moveTurn(-Constants.MOVE_RIGHT_TURN_ANGLE, 0);
		}

		// deposit the cube
		// store time
		startTime = System.currentTimeMillis();
		// update on time required
		robot.lift.liftUp(1);

		// is time up
		if (System.currentTimeMillis() - startTime > 3000) {
			robot.lift.liftStop();
		}

		robot.manipulator.pullOut(1);

	}
	
	public void depositAtScale(double start, boolean side) {

	} ; 
	
	// stateMachine.add("", new double[]{});

	
	public void depositAtScaleCommands(double start, boolean side) { // left = true,
		// right = false

		// clear any obstacles
		stateMachine.add("forward", new double[]{5});

		// Align robot with the scale
		if(side){ // do we have the left scale . . .
			
			stateMachine.add("turn", new double[]{-Constants.MOVE_RIGHT_TURN_ANGLE, 0});
			stateMachine.add("forward", new double[]{start + 11});
			stateMachine.add("turn", new double[]{Constants.MOVE_RIGHT_TURN_ANGLE, 0});


		} 
		else{ // . . . or the right scale

			stateMachine.add("turn", new double[]{Constants.MOVE_RIGHT_TURN_ANGLE, 0});
			stateMachine.add("moveStraight", new double[]{start + 11});
			stateMachine.add("turn", new double[]{-Constants.MOVE_RIGHT_TURN_ANGLE, 0});

		}

		// Move to the center of the arena

		stateMachine.add("forward", new double[]{22 - Constants.DRIVE_BASE_LENGTH});
		
		// turn to face the scale
		if (side) {
			stateMachine.add("turn", new double[]{Constants.MOVE_RIGHT_TURN_ANGLE, 0});
			
		} 
		else {
			stateMachine.add("turn", new double[]{-Constants.MOVE_RIGHT_TURN_ANGLE, 0});
		}

		
		stateMachine.add("forward", new double[]{5 - Constants.DRIVE_BASE_LENGTH});

		/*
		 * /deposit the cube /long startTime = System.currentTimeMillis();
		 * while(System.currentTimeMillis()-startTime< 3000){ //update on time
		 * required robot.lift.liftUp(1); } robot.lift.liftStop();
		 * robot.manipulator.pullOut(1);
		 */
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
	
	public void crossLine(double start, boolean side) {
		if(!side)
			start *= -1;
		
		if (start < 13 && start < 6 || start < -13 && start > -6) {
			if (state == 1) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveStraight(13);
					state++;
				}
				return;
			}
		}
		if (start >= 0 && start <= 4.5) { //----------
			if (state == 1) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveStraight(1);
					state++;
				}
				return;
			}
			if (state == 2) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveTurn(90, 0);
					state++;
				}
				return;
			}
			if (state == 3) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveStraight(9 - start);
					state++;
				}
				return;
			}
			if (state == 4) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveTurn(-90, 0);
					state++;
				}
				return;
			}
			if (state == 5) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveStraight(12);
					state++;
				}
				return;
			}
		}

		if (start < 0 && start >= -6) { //-------
			if (state == 1) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveStraight(1);
					state++;
				}
				return;
			}
			if (state == 2) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveTurn(-90, 0);
					state++;
				}
				return;
			}
			if (state == 3) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveStraight(9 - start);
					state++;
				}
				return;
			}
			if (state == 4) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveTurn(90, 0);
					state++;
				}
				return;
			}
			if (state == 5) {
				if (!robot.driveTrain.moveGetActive()) {
					robot.driveTrain.moveStraight(12);
					state++;
				}
				return;
			}
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